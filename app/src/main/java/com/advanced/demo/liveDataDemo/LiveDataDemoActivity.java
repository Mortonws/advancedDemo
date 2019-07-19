package com.advanced.demo.liveDataDemo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by morton_ws on 2019-07-18.
 */
public class LiveDataDemoActivity extends BaseActivity {

    private Button mBtnLoadData;
    private TextView mContent;

    private DemoDataViewModel mViewModel;

    @Override
    protected void initView() {
        super.initView();
        mBtnLoadData = findViewById(R.id.btn_load_data);
        mContent = findViewById(R.id.content);
    }

    @Override
    protected void initPages() {
        super.initPages();
        mViewModel = ViewModelProviders.of(this).get(DemoDataViewModel.class);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContent.setText("loading data ...");
                mViewModel.loadData();
            }
        });
        mViewModel.getContentLiveData().observeForever(new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.e(getTag(), "[onChanged] content: " + s);
                mContent.setText(s);
            }
        });
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(getTag(), "onAttachedToWindow");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(getTag(), "onDetachedFromWindow");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_live_data_demo;
    }
}
