package com.advanced.demo.fragment;

import android.support.v4.app.FragmentManager;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by morton_ws on 2017/12/19.
 */

public class HelloWorldFragmentActivity extends BaseActivity {

    @Override
    protected void initPages() {
        super.initPages();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.contain_view, new HelloWorldFragment()).commit();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_fragment_activity;
    }
}
