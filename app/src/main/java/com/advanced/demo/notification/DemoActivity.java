package com.advanced.demo.notification;

import android.view.View;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by wusang on 2018/2/8.
 */

public class DemoActivity extends BaseActivity {
    @Override
    protected void initListener() {
        super.initListener();
        findViewById(R.id.demo_text_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_demo;
    }
}
