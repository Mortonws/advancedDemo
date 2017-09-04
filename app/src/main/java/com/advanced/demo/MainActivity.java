package com.advanced.demo;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.edittextUI.EditTextUIActivity;
import com.advanced.demo.emoji.EmojiActivity;
import com.advanced.demo.lockDevice.LockDeviceActivity;
import com.advanced.demo.mainPage.MainAdapter;
import com.advanced.demo.propertyAnimator.PropertyAnimatorActivity;
import com.advanced.demo.singleTask.ActivityD;

public class MainActivity extends BaseActivity {

    private MainAdapter mAdapter;


    @Override
    protected void initPages() {
        super.initPages();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mAdapter.addData(EmojiActivity.class, "Emoji");
        mAdapter.addData(ActivityD.class, "Start Page D");
        mAdapter.addData(PropertyAnimatorActivity.class, "PropertyValueAnimator");
        mAdapter.addData(EditTextUIActivity.class, "EditText Delete");
        mAdapter.addData(LockDeviceActivity.class, "Lock Device");
    }

    @Override
    protected void initView() {
        super.initView();
        RecyclerView mainRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);

        mAdapter = new MainAdapter(mContext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mainRecyclerView.setAdapter(mAdapter);
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }
}
