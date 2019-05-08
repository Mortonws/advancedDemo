package com.advanced.demo.scrollView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

import org.greenrobot.eventbus.EventBus;

/**
 * @author by morton_ws on 2018/4/28.
 */

public class DispatchScrollActivity extends BaseActivity {

    private ScrollRelativeLayout mRootView;
    private DispatchRecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mRootView = findViewById(R.id.root_view);
        mRecyclerView = findViewById(R.id.dispatch_recycler_view);
    }

    @Override
    protected void initPages() {
        super.initPages();
        DispatchIndexAdapter adapter = new DispatchIndexAdapter(mContext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRootView.setRecyclerView(mRecyclerView);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_dispatch_scroll;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}