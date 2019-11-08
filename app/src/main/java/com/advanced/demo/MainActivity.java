package com.advanced.demo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.anr.ANRActivity;
import com.advanced.demo.app.DemoApplication;
import com.advanced.demo.camera.TakePicByCameraActivity;
import com.advanced.demo.cameraDetect.CameraDetectActivity;
import com.advanced.demo.cameraPreview.CameraPreviewActivity;
import com.advanced.demo.classInstanceTest.ClassInstanceTestActivity;
import com.advanced.demo.contacts.ReadContactActivity;
import com.advanced.demo.devicePropteryCollect.BatteryCollectActivity;
import com.advanced.demo.dialogActivity.DialogActivity;
import com.advanced.demo.edittextUI.EditTextUIActivity;
import com.advanced.demo.emoji.EmojiActivity;
import com.advanced.demo.emulator.EmulatorTestActivity;
import com.advanced.demo.eventBus.EventBusActivity;
import com.advanced.demo.fragment.HelloWorldFragmentActivity;
import com.advanced.demo.fragmentAdapter.UserViewPagerActivity;
import com.advanced.demo.gyro.GyroActivity;
import com.advanced.demo.liveDataDemo.LifecycleDemoActivity;
import com.advanced.demo.liveDataDemo.LiveDataDemoActivity;
import com.advanced.demo.lockDevice.LockDeviceActivity;
import com.advanced.demo.mainPage.MainAdapter;
import com.advanced.demo.newScrollerDemo.NewScrollerDemoActivity;
import com.advanced.demo.notification.DemoActivity;
import com.advanced.demo.notification.PendingNotificationActivity;
import com.advanced.demo.openSocial.OpenSocialActivity;
import com.advanced.demo.propertyAnimator.PropertyAnimatorActivity;
import com.advanced.demo.qrScan.QRScanActivity;
import com.advanced.demo.retrofit.RetrofitRequestActivity;
import com.advanced.demo.rotationSensor.RotationSensorActivity;
import com.advanced.demo.scrollView.DispatchScrollActivity;
import com.advanced.demo.simpleActivity.SimpleActivity;
import com.advanced.demo.singleTask.ActivityD;
import com.advanced.demo.threadTest.ThreadDemoActivity;
import com.advanced.demo.transctionView.CircleTransactionViewActivity;
import com.advanced.demo.writeText2SDFile.WriteText2SDFileActivity;

import java.io.BufferedReader;
import java.io.FileReader;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends BaseActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private MainAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handlePush();
    }

    private void handlePush() {
        Intent pushIntent = getIntent();
        if (pushIntent != null && pushIntent.hasExtra(PendingNotificationActivity.PUSH_EXTRA_KEY)) {
            String pushKey = pushIntent.getStringExtra(PendingNotificationActivity.PUSH_EXTRA_KEY);
            if (TextUtils.equals(pushKey, "pending_push")) {
                Intent demoActivityIntent = new Intent(this, DemoActivity.class);
                startActivity(demoActivityIntent);
            }
        }
    }


    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void initPages() {
        super.initPages();
        mAdapter.addData(ClassInstanceTestActivity.class, "Class Instance Test");
        mAdapter.addData(WriteText2SDFileActivity.class, "Write Text To File");
        mAdapter.addData(SimpleActivity.class, "Simple Activity");
        mAdapter.addData(NewScrollerDemoActivity.class, "New Scroller View");
        mAdapter.addData(ThreadDemoActivity.class, "Thread Demo Test");
        mAdapter.addData(DialogActivity.class, "Dialog Activity");
        mAdapter.addData(LiveDataDemoActivity.class, "LiveData Study");
        mAdapter.addData(LifecycleDemoActivity.class, "Lifecycle Study");
        mAdapter.addData(UserViewPagerActivity.class, "User ViewPager");
        mAdapter.addData(EventBusActivity.class, "Event Bus");
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

        mReceiver = new LockScreenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(mReceiver, filter);
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
    protected void onResume() {
        super.onResume();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenUnLock = pm.isScreenOn();
        if (isScreenUnLock) {
            Log.e(TAG, "[onResume]isScreenUnLock=");
        }

//        String shuabaoPageUrl = "shuabao://page/home/me/task/mine?name=SangW"
//        Uri uri = Uri.parse(shuabaoPageUrl);
//        Log.e(TAG, "host = " + uri.getHost() + "; path = " + uri.getPath().replaceFirst("/",""));
        Log.e(TAG, "[onResume]call" + "; appisForeground=" + DemoApplication.isAppForeground());

        getMemoryInfo();
    }


    private void getMemoryInfo() {
        int M = 1024 * 1024;
        Runtime r = Runtime.getRuntime();

        Log.e(TAG, "最大可用内存:" + r.maxMemory() / M + "M");
        Log.e(TAG, "当前可用内存:" + r.totalMemory() / M + "M");
        Log.e(TAG, "当前空闲内存:" + r.freeMemory() / M + "M");
        Log.e(TAG, "当前已使用内存:" + (r.totalMemory() - r.freeMemory()) / M + "M");

        Log.e(TAG, "----\n总的内存： " + getTotalRam());
    }

    public static String getTotalRam(){//GB
        String path = "/proc/meminfo";
        String firstLine = null;
        int totalRam = 0 ;
        try{
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader,8192);
            firstLine = br.readLine().split("\\s+")[1];
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(firstLine != null){
            totalRam = (int)Math.ceil((Float.valueOf(Float.valueOf(firstLine) / (1024 * 1024)).doubleValue()));
        }

        return totalRam + "GB";//返回1GB/2GB/3GB/4GB
    }

    private String getTotalMemory() {
        ActivityManager actManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        long totalMemory = memInfo.totalMem;
        return String.valueOf(totalMemory);// Byte转换为KB或者MB，内存大小规格化
    }

    public long getFreeMem() {
        ActivityManager manager = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(info);
        // 单位Byte
        return info.totalMem;
    }

    private LockScreenReceiver mReceiver;
    private boolean mHasPresent = true;

    private class LockScreenReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String change = "";
                if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                    change = "亮屏";
                    mHasPresent = false;
                } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    change = "锁屏";
                    mHasPresent = false;
                } else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
                    change = "解锁";
                    mHasPresent = true;
                }
                Log.e(TAG, "[onReceive]change="+change);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.e(TAG, "[onPause]");
//        try {
//            Thread.sleep(1500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            Log.e(TAG, "[onPause] finish Thread 1500");
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Log.e(TAG, "[onStop]");
//        try {
//            Thread.sleep(1500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            Log.e(TAG, "[onStop] finish Thread 1500");
//        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        unregisterReceiver(mReceiver);
    }
}
