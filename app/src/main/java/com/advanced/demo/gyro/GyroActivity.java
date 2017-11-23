package com.advanced.demo.gyro;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;
import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @author by morton_ws on 2017/11/23.
 */

public class GyroActivity extends BaseActivity {
    private Button mGetGyro;
    private TextView mGyroResult;

    @Override
    protected void initPages() {
        super.initPages();
    }

    @Override
    protected void initView() {
        super.initView();
        mGetGyro = (Button) findViewById(R.id.btn_get_gyro);
        mGyroResult = (TextView) findViewById(R.id.gyro_result);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mGetGyro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGyro();
            }
        });
        findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGyroResult.setText(null);
            }
        });
    }

    private void getGyro() {
        PropertyCollector collector = new PropertyCollector();
        collector.collect(mContext, new DevicePropertyCollectListener() {
            @Override
            public void onCollect(Map<String, String> property) {
                String propertyJson = JSON.toJSONString(property);
                mGyroResult.setText(propertyJson);
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_gyro;
    }
}
