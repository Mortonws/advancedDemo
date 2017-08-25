package com.advanced.baselib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author by morton_ws on 2017/8/11.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        mContext = this;
        initView();
        initPages();
        initListener();
    }

    protected void initPages() {
    }

    protected void initView() {

    }

    protected void initListener() {

    }

    protected abstract int setLayoutId();

    protected <T extends View> T getViewById(@IdRes int resourceId) {
        return (T) findViewById(resourceId);
    }
}
