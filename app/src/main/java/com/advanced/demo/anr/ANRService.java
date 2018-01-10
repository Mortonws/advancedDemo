package com.advanced.demo.anr;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.Locale;

/**
 * @author by sangw on 2018/1/10.
 */

public class ANRService extends Service {

    public final static String EXTRA_RUN_OUT_TIME = "com.advanced.demo.anr.RUN_OUT_TIME";
    public final static String EXTRA_RUM_SECONDS = "com.advanced.demo.anr.RUN_SECONDS";

    public final static String ACTION_RUN_OUT_TIME = "com.advanced.demo.anr.action.RUN_OUT_TIME";
    private final static String TAG = "ANR.Service";
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(action, ACTION_RUN_OUT_TIME)) {
                boolean runOutTime = intent.getBooleanExtra(EXTRA_RUN_OUT_TIME, false);
                int runSeconds = intent.getIntExtra(EXTRA_RUM_SECONDS, 30);
                String logMsg = String.format(Locale.getDefault(), "onReceive, runOutTime; runOutTime:%s; runSeconds:%s", runOutTime, runSeconds);
                Log.i(TAG, logMsg);
                if (runOutTime) {
                    runOutTime(runSeconds);
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter(ACTION_RUN_OUT_TIME);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean runOutTime = intent.getBooleanExtra(EXTRA_RUN_OUT_TIME, false);
        int runSeconds = intent.getIntExtra(EXTRA_RUM_SECONDS, 30);
        String logMsg = String.format(Locale.getDefault(), "onStartCommand; runOutTime:%s; runSeconds:%s", runOutTime, runSeconds);
        Log.i(TAG, logMsg);
        if (runOutTime) {
            runOutTime(runSeconds);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void runOutTime(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }
}
