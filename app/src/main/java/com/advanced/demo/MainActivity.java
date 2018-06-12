package com.advanced.demo;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
import com.advanced.demo.openSocial.OpenSocialActivity;
import com.advanced.demo.propertyAnimator.PropertyAnimatorActivity;
import com.advanced.demo.qrScan.QRScanActivity;
import com.advanced.demo.retrofit.RetrofitRequestActivity;
import com.advanced.demo.rotationSensor.RotationSensorActivity;
import com.advanced.demo.scrollView.DispatchScrollActivity;
import com.advanced.demo.singleTask.ActivityD;
import com.advanced.demo.transctionView.CircleTransactionViewActivity;

public class MainActivity extends BaseActivity {
//    private final static String TAG = "MainActivity";

    private MainAdapter mAdapter;

    @Override
    protected void initPages() {
        super.initPages();
        mAdapter.addData(DispatchScrollActivity.class, "Dispatch Scroll");
        mAdapter.addData(OpenSocialActivity.class, "Open Social");
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
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }
}
