package com.advanced.demo;

import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.camera.TakePicByCameraActivity;
import com.advanced.demo.cameraDetect.CameraDetectActivity;
import com.advanced.demo.cameraPreview.CameraPreviewActivity;
import com.advanced.demo.contacts.ReadContactActivity;
import com.advanced.demo.devicePropteryCollect.BatteryCollectActivity;
import com.advanced.demo.edittextUI.EditTextUIActivity;
import com.advanced.demo.emoji.EmojiActivity;
import com.advanced.demo.emulator.EmulatorTestActivity;
import com.advanced.demo.gyro.GyroActivity;
import com.advanced.demo.lockDevice.LockDeviceActivity;
import com.advanced.demo.mainPage.MainAdapter;
import com.advanced.demo.propertyAnimator.PropertyAnimatorActivity;
import com.advanced.demo.qrScan.QRScanActivity;
import com.advanced.demo.retrofit.RetrofitRequestActivity;
import com.advanced.demo.rotationSensor.RotationSensorActivity;
import com.advanced.demo.singleTask.ActivityD;
import com.advanced.demo.transctionView.CircleTransactionViewActivity;

public class MainActivity extends BaseActivity {

    private MainAdapter mAdapter;

    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    @Override
    protected void initPages() {
        super.initPages();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mAdapter.addData(GyroActivity.class, "Gyro Result");
        mAdapter.addData(QRScanActivity.class, "QR SCAN");
        mAdapter.addData(BatteryCollectActivity.class, "Battery Collect");
        mAdapter.addData(EmulatorTestActivity.class, "Emulator Test");
        mAdapter.addData(RetrofitRequestActivity.class, "Retrofit Request");
        mAdapter.addData(EmojiActivity.class, "Emoji");
        mAdapter.addData(ActivityD.class, "Start Page D");
        mAdapter.addData(PropertyAnimatorActivity.class, "PropertyValueAnimator");
        mAdapter.addData(EditTextUIActivity.class, "EditText Delete");
        mAdapter.addData(LockDeviceActivity.class, "Lock Device");
        mAdapter.addData(TakePicByCameraActivity.class, "Take Pic");
        mAdapter.addData(RotationSensorActivity.class, "Rotation Param");
        mAdapter.addData(ReadContactActivity.class, "Read Contact");
        mAdapter.addData(CircleTransactionViewActivity.class, "Transaction View");
        mAdapter.addData(CameraDetectActivity.class, "Camera Detect");
        mAdapter.addData(CameraPreviewActivity.class, "Activity Preview");
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
        Log.e("MainActivity", "isEmulator: " + isEmulator());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }
}
