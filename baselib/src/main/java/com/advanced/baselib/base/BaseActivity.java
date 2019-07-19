package com.advanced.baselib.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.advanced.baselib.R;

/**
 * @author by morton_ws on 2017/8/11.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public static boolean sShowLogoView = false;

    protected Context mContext;
    private FrameLayout mFrameLayout;
    private View mLayoutLogoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        mFrameLayout = (FrameLayout) getWindow().getDecorView();
        mContext = this;
        initView();
        initPages();
        initListener();
    }

    protected void initPages() {
    }

    protected void initView() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_logo, mFrameLayout, true);
        mLayoutLogoView = view.findViewById(R.id.logo_root_layout);
    }

    protected void initListener() {

    }

    protected abstract int setLayoutId();

    protected String getTag() {
        return this.getClass().getSimpleName();
    }
}
