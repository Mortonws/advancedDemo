package com.advanced.demo.rotationSensor;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

import java.util.HashMap;

/**
 * @author by sangw on 2017/9/13.
 */

public class RotationSensorActivity extends BaseActivity {
    private Button mGetRotationParam;
    private TextView mContentRotationParam;
    private DeviceGyroscopeProperty mDeviceGyroscopeProperty;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case IDeviceProperty.EVENT_COLLECT_GYROSCOPE:
                    String propertyValue = (String) msg.obj;
                    HashMap<String, String> paramMap = new HashMap<>();
                    paramMap.put("jr_app_gyroscope_property", propertyValue);
                    mContentRotationParam.setText(paramMap.toString());
                    if (mDeviceGyroscopeProperty != null) {
                        mDeviceGyroscopeProperty.unregisterListener();
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
    }

    @Override
    protected void initPages() {
        super.initPages();
        mDeviceGyroscopeProperty = new DeviceGyroscopeProperty(mContext);
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
}
