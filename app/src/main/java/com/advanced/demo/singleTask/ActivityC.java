package com.advanced.demo.singleTask;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by sangw on 2017/8/15.
 *
 * standard
 */

public class ActivityC extends BaseActivity {
    private final static String TAG = "launchMode.C";
    private TextView mStartNextActivity;
    private TextView mPageName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean hideLogoView = getIntent().getBooleanExtra("hide_logo_view", false);
        sShowLogoView = !hideLogoView;
    }

    @Override
    protected void initView() {
        super.initView();
        mStartNextActivity = findViewById(R.id.btn_start_next);
        mStartNextActivity.setText("Start Page A");
        mPageName = findViewById(R.id.page_name);
        mPageName.setText("Page C");
    }

    @Override
    protected void initListener() {
        super.initListener();
        mStartNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ActivityA.class));
            }
        });
        mPageName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ActivityD.class));
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
        Log.i(TAG, "ActivityC finish");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "ActivityC Destroy");
    }
}
