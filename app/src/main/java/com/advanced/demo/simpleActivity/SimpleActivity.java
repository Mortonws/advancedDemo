package com.advanced.demo.simpleActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

import java.util.Random;

/**
 * @author by morton_ws on 2019-10-16.
 */
public class SimpleActivity extends BaseActivity {

    Button mBtnShow;
    Button mBtnUpdate;
    TextView mContent;
    Button mBtnHide;

    @Override
    protected void initPages() {
        super.initPages();
    }

    @Override
    protected void initView() {
        super.initView();
        mContent = findViewById(R.id.content);
        mBtnShow = findViewById(R.id.btn_show);
        mBtnUpdate = findViewById(R.id.btn_update);
        mBtnHide = findViewById(R.id.btn_hide);
    }

    Random random = new Random();
    @Override
    protected void initListener() {
        super.initListener();
        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = random.nextInt(100);
                mContent.setText("resule: " + result);
            }
        });
        mBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContent.setVisibility(View.VISIBLE);
            }
        });
        mBtnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContent.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_simple;
    }
}
