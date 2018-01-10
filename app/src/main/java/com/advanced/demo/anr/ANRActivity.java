package com.advanced.demo.anr;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by sangw on 2018/1/10.
 */

public class ANRActivity extends BaseActivity {

    private Button mStartService;
    private EditText mValue;
    private EditText mRunSeconds;
    private Button mSendBroadcast;

    @Override
    protected void initView() {
        super.initView();
        mStartService = (Button) findViewById(R.id.btn_start_service);
        mValue = (EditText) findViewById(R.id.value);
        mRunSeconds = (EditText) findViewById(R.id.run_seconds);
        mSendBroadcast = (Button) findViewById(R.id.btn_send_broadcast);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valueStr = mValue.getText().toString();
                String secondsStr = mRunSeconds.getText().toString();
                int runSeconds = 0;
                int value = 0;
                try {
                    value = Integer.valueOf(valueStr);
                    runSeconds = Integer.valueOf(secondsStr);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                boolean runOouTime = value % 2 == 0;
                Intent intent = new Intent(mContext, ANRService.class);
                intent.putExtra(ANRService.EXTRA_RUN_OUT_TIME, runOouTime);
                intent.putExtra(ANRService.EXTRA_RUM_SECONDS, runSeconds);
                startService(intent);
            }
        });
        mSendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valueStr = mValue.getText().toString();
                String secondsStr = mRunSeconds.getText().toString();
                int runSeconds = 0;
                int value = 0;
                try {
                    value = Integer.valueOf(valueStr);
                    runSeconds = Integer.valueOf(secondsStr);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                boolean runOouTime = value % 2 == 0;
                Intent intent = new Intent();
                intent.setAction(ANRService.ACTION_RUN_OUT_TIME);
                intent.putExtra(ANRService.EXTRA_RUN_OUT_TIME, runOouTime);
                intent.putExtra(ANRService.EXTRA_RUM_SECONDS, runSeconds);
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_anr;
    }
}
