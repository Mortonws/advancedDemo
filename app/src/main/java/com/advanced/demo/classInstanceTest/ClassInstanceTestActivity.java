package com.advanced.demo.classInstanceTest;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by morton_ws on 2019-10-25.
 */
public class ClassInstanceTestActivity extends BaseActivity {
    private final static String TAG = ClassInstanceTestActivity.class.getSimpleName();
    private Button mBtnInstanceOf;
    private Button mBtnIsInstance;

    private List<BaseInstance> mList = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        mBtnInstanceOf = findViewById(R.id.btn_instance_of);
        mBtnIsInstance = findViewById(R.id.btn_is_instance);
    }

    @Override
    protected void initPages() {
        super.initPages();
        mList.add(new InstanceA());
        mList.add(new InstanceB());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnInstanceOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int index =0; index < mList.size(); index++) {
                    boolean result = mList.get(index) instanceof InstanceA;
                    Log.e(TAG, "result = " + result);
                }
            }
        });
        mBtnIsInstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Class clazz = InstanceB.class;
                for (int index =0; index < mList.size(); index++) {
                    boolean result = clazz.isInstance(mList.get(index));
                    Log.e(TAG, "result = " + result);
                }
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_class_instance_test;
    }
}
