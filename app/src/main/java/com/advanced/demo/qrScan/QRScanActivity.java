package com.advanced.demo.qrScan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;
import com.google.zxing.client.android.CaptureActivity;

/**
 * @author by morton_ws on 2017/11/17.
 */

public class QRScanActivity extends BaseActivity {
    private final static int REQUEST_CODE_SCAN = 1001;


    private Button mBtnQRScan;
    private TextView mScanResult;

    @Override
    protected void initPages() {
        super.initPages();
    }

    @Override
    protected void initView() {
        super.initView();

        mBtnQRScan = (Button) findViewById(R.id.btn_start_scan);
        mScanResult = (TextView) findViewById(R.id.tv_scan_result);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnQRScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_SCAN) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    mScanResult.setText("Scan is Null");
                    return;
                }
                String result = bundle.getString("result");
                mScanResult.setText(result);
            }
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_qr_scan;
    }
}
