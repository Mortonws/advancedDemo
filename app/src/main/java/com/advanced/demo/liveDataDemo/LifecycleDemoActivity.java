package com.advanced.demo.liveDataDemo;

import android.arch.lifecycle.LifecycleObserver;
import android.view.View;
import android.widget.Button;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by morton_ws on 2019-07-17.
 */
public class LifecycleDemoActivity extends BaseActivity {

    Button mBtnRegister;
    Button mBtnUnregister;

    private LifecycleObserver mObserver;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_livecycle_demo;
    }

    @Override
    protected void initView() {
        super.initView();
        mBtnRegister = findViewById(R.id.btn_register);
        mBtnUnregister = findViewById(R.id.btn_unregister);
    }

    @Override
    protected void initPages() {
        super.initPages();
    }


    @Override
    protected void initListener() {
        super.initListener();
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mObserver == null) {
                    mObserver = new DemoLiveObserver();
                    getLifecycle().addObserver(mObserver);
                }
            }
        });
        mBtnUnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mObserver != null) {
                    getLifecycle().removeObserver(mObserver);
                    mObserver = null;
                }
            }
        });
    }
}
