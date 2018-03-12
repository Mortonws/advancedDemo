package com.advanced.demo;

import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.anr.ANRActivity;
import com.advanced.demo.camera.TakePicByCameraActivity;
import com.advanced.demo.cameraDetect.CameraDetectActivity;
import com.advanced.demo.cameraPreview.CameraPreviewActivity;
import com.advanced.demo.contacts.ReadContactActivity;
import com.advanced.demo.devicePropteryCollect.BatteryCollectActivity;
import com.advanced.demo.edittextUI.EditTextUIActivity;
import com.advanced.demo.emoji.EmojiActivity;
import com.advanced.demo.emulator.EmulatorTestActivity;
import com.advanced.demo.fragment.HelloWorldFragmentActivity;
import com.advanced.demo.gyro.GyroActivity;
import com.advanced.demo.lockDevice.LockDeviceActivity;
import com.advanced.demo.mainPage.MainAdapter;
import com.advanced.demo.notification.PendingNotificationActivity;
import com.advanced.demo.propertyAnimator.PropertyAnimatorActivity;
import com.advanced.demo.qrScan.QRScanActivity;
import com.advanced.demo.retrofit.RetrofitRequestActivity;
import com.advanced.demo.rotationSensor.RotationSensorActivity;
import com.advanced.demo.singleTask.ActivityD;
import com.advanced.demo.transctionView.CircleTransactionViewActivity;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.Locale;

public class MainActivity extends BaseActivity {
    private final static String TAG = "MainActivity";

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
        mAdapter.addData(PendingNotificationActivity.class, "Pending Notification Msg");
        mAdapter.addData(ANRActivity.class, "ANR");
        mAdapter.addData(HelloWorldFragmentActivity.class, "Hello Fragment");
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

//        if (BuildConfig.DEBUG) {
//            Toast.makeText(mContext, BuildConfig.debugConfigKey, Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    protected void initView() {
        super.initView();
        RecyclerView mainRecyclerView = findViewById(R.id.main_recycler_view);

        mAdapter = new MainAdapter(mContext);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mainRecyclerView.setAdapter(mAdapter);
        mainRecyclerView.setLayoutManager(layoutManager);
        mainRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.e(TAG, "isEmulator: " + isEmulator());

        float density = getResources().getDisplayMetrics().density;
        int screenWidth_px = getResources().getDisplayMetrics().widthPixels;
        int screenHeight_px = getResources().getDisplayMetrics().heightPixels;
        int dpi = getResources().getDisplayMetrics().densityDpi;

        int screenWidth_dp = (int) (screenWidth_px / density);
        int screenHeight_dp = (int) (screenHeight_px / density);

        String screenLog = String.format(Locale.getDefault(), "density: %s; widthPx: %s; heightPx: %s; widthDP: %s; heightDP: %s",
                density, screenWidth_px, screenHeight_px, screenWidth_dp, screenHeight_dp);
        Log.e(TAG, screenLog);
        Log.e(TAG, "dpi: " + dpi);
        Log.e(TAG, "enum male: " + Person.Male);
        Log.e(TAG, "enum female: " + Person.Female);
        Log.e(TAG, "enum child: " + Person.Child);
        Log.e(TAG, "enum oldPeople: " + Person.OldPeople);

        TwinklingRefreshLayout refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setPureScrollModeOn();

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    private enum Person {
        Male,
        Female,
        Child,
        OldPeople
    }
}
