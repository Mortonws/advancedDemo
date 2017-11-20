package com.advanced.demo.devicePropteryCollect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by morton_ws on 2017/11/16.
 */

public class BatteryCollectActivity extends BaseActivity {
    private Button mBtnStart;
    private Button mBtnStop;
    private TextView mDeviceBattery;

    private Button mBtnGetBattery;
    private TextView mDeviceBattery1;
    private TextView mTvContent;

    private BroadcastReceiver mBatteryReceiver;
    private IntentFilter mIntentFilter;

    private String content = "Today is Friday\nTomorrow is Saturday";

    @Override
    protected void initPages() {
        super.initPages();
        mBatteryReceiver = new BatteryReceiver();
        mIntentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        StringBuilder sb = new StringBuilder();
        sb.append("Origin: ").append(content);
        sb.append("\n").append("---------------").append("\n");
        sb.append("NowContent:").append(removeLine());
        mTvContent.setText(sb.toString());
    }

    @Override
    protected void initView() {
        super.initView();
        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mDeviceBattery = (TextView) findViewById(R.id.device_battery);

        mBtnGetBattery = (Button) findViewById(R.id.btn_get_device_battery);
        mDeviceBattery1 = (TextView) findViewById(R.id.device_battery_1);
        mTvContent = (TextView) findViewById(R.id.tv_content);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerReceiver(mBatteryReceiver, mIntentFilter);
            }
        });
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregisterReceiver(mBatteryReceiver);
                mDeviceBattery.setText(null);
            }
        });
        mBtnGetBattery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent intent = registerReceiver(null, intentFilter);
                int level = intent.getIntExtra("level", 0);
                mDeviceBattery1.setText("电池电量：" + level);
            }
        });
    }

    private String removeLine() {
        String result = "";
        result = content.replaceAll("\n", ", ");
        return result;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_battery_collect;
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBatteryReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBatteryReceiver);
    }

    private class BatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(action, Intent.ACTION_BATTERY_CHANGED)) {
                int level = intent.getIntExtra("level", 0);
                mDeviceBattery.setText("手机电量为:" + level + "%");
            }
        }
    }
}
