package com.advanced.demo.singleTask;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by sangw on 2017/8/15.
 *         <p>
 *         standard
 */

public class ActivityB extends BaseActivity {
    private final static String TAG = "launchMode.B";

    private TextView mStartNextActivity;

    @Override
    protected void initView() {
        super.initView();
        mStartNextActivity = (TextView) findViewById(R.id.btn_start_next);
        mStartNextActivity.setText("Start Page C");
        ((TextView) findViewById(R.id.page_name)).setText("Page B");
    }

    @Override
    protected void initListener() {
        super.initListener();
        mStartNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ActivityC.class));
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_single_task;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume; ThreadID: " + Thread.currentThread().getName());
    }

    @Override
    public void finish() {
        super.finish();
        Log.i(TAG, "ActivityB finish");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ActivityB Destroy");
    }
}
