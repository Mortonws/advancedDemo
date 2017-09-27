package com.advanced.demo.rotationSensor;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.advanced.demo.rotationSensor.IDeviceProperty.EVENT_COLLECT_GYROSCOPE;
import static com.advanced.demo.rotationSensor.IDeviceProperty.EVENT_SHOW_CONTENT_GRAVITY;
import static com.advanced.demo.rotationSensor.IDeviceProperty.EVENT_SHOW_CONTENT_GYROS;

/**
 * @author by sangw on 2017/9/13.
 */

public class RotationSensorActivity extends BaseActivity {
    private final static String TAG = "Collect.SensorGyro";
    private Button mGetRotationParam;
    private TextView mContentRotationParam;
    private DeviceGyroscopeProperty mDeviceGyroscopeProperty;

    private TextView mBtnStart;
    private TextView mBtnStop;
    private TextView mContentGyros;
    private TextView mContentGravity;
    private TextView mBtnReset;
    private RecyclerView mDataList;
    private DataAdapter mAdapter;
    private TextView mBtnCopy;

    private SensorManager mSensorManager;
    private Sensor mSensorGravity;
    private Sensor mSensorGyros;
    private SensorPropertyEventListener mSensorEventListener;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case EVENT_COLLECT_GYROSCOPE:
                    String propertyValue = (String) msg.obj;
                    HashMap<String, String> paramMap = new HashMap<>();
                    paramMap.put("jr_app_gyroscope_property", propertyValue);
                    mContentRotationParam.setText(paramMap.toString());
                    if (mDeviceGyroscopeProperty != null) {
                        mDeviceGyroscopeProperty.unregisterListener();
                    }
                    break;
                case EVENT_SHOW_CONTENT_GYROS:
                    String gyros = (String) msg.obj;
                    if (TextUtils.isEmpty(gyros)) {
                        mContentGyros.setText("0,0,0");
                        mAdapter.addData("x:0, y:0, z:0");
                    } else {
                        mContentGyros.setText(gyros);
                        mAdapter.addData(gyros);
                    }
                    mDataList.scrollToPosition(mAdapter.getItemCount() - 1);
                    break;
                case EVENT_SHOW_CONTENT_GRAVITY:
                    String gravity = (String) msg.obj;
                    if (TextUtils.isEmpty(gravity)) {
                        mContentGravity.setText("0,0,0");
                    } else {
                        mContentGravity.setText(gravity);
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected void initView() {
        super.initView();
        mGetRotationParam = (Button) findViewById(R.id.btn_get_rotation_param);
        mContentRotationParam = (TextView) findViewById(R.id.content_rotation_param);

        mBtnStart = (TextView) findViewById(R.id.btn_start);
        mBtnStop = (TextView) findViewById(R.id.btn_stop);
        mContentGyros = (TextView) findViewById(R.id.content_gyros);
        mContentGravity = (TextView) findViewById(R.id.content_gravity);
        mDataList = (RecyclerView) findViewById(R.id.data_list);
        mBtnReset = (TextView) findViewById(R.id.btn_reset);
        mBtnCopy = (TextView) findViewById(R.id.btn_copy);
    }

    @Override
    protected void initPages() {
        super.initPages();
        mDeviceGyroscopeProperty = new DeviceGyroscopeProperty(mContext);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorGravity = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        mSensorGyros = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mSensorEventListener = new SensorPropertyEventListener();

        mAdapter = new DataAdapter();
        mDataList.setAdapter(mAdapter);
        mDataList.setItemAnimator(new DefaultItemAnimator());
        mDataList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initListener() {
        super.initListener();
        mGetRotationParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRotationParam();
            }
        });

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSensorManager.registerListener(mSensorEventListener, mSensorGravity, SensorManager.SENSOR_DELAY_NORMAL);
                mSensorManager.registerListener(mSensorEventListener, mSensorGyros, SensorManager.SENSOR_DELAY_NORMAL);
            }
        });
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSensorManager.unregisterListener(mSensorEventListener);
            }
        });
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.reset();
            }
        });
        mBtnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> dataList = mAdapter.getDataList();
                StringBuilder sb = new StringBuilder();
                for (String data : dataList) {
                    sb.append(data).append("\n");
                }

                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label", sb.toString().trim());
                cm.setPrimaryClip(mClipData);
                Toast.makeText(mContext, "复制成功", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getRotationParam() {
        mDeviceGyroscopeProperty.getDeviceGyroscope(mHandler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRotationParam();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_rotation_sensor;
    }

    private class SensorPropertyEventListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            String content = "";
            float[] values = event.values;
            if (values != null) {
                content = String.format(Locale.getDefault(), "x:%s, y:%s, z:%s", values[0], values[1], values[2]);
            }
            int type = event.sensor.getType();
            Message msg = new Message();
            if (type == Sensor.TYPE_GYROSCOPE) {
                msg.what = EVENT_SHOW_CONTENT_GYROS;
            } else if (type == Sensor.TYPE_GRAVITY) {
                msg.what = EVENT_SHOW_CONTENT_GRAVITY;
            }
            msg.obj = content;
            mHandler.sendMessage(msg);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
        private List<String> dataList = new ArrayList<>();

        @Override
        public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_data, parent, false);
            return new DataViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DataViewHolder holder, int position) {
            String data = dataList.get(position);
            holder.itemName.setText(data);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void addData(String data) {
            dataList.add(data);
            notifyDataSetChanged();
        }

        public void reset() {
            dataList.clear();
            notifyDataSetChanged();
        }

        public List<String> getDataList() {
            return dataList;
        }

        public class DataViewHolder extends RecyclerView.ViewHolder {
            TextView itemName;

            public DataViewHolder(View itemView) {
                super(itemView);
                itemName = (TextView) itemView.findViewById(R.id.item_name);
            }
        }
    }
}
