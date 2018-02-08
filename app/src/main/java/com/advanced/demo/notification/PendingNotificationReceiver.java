package com.advanced.demo.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

/**
 * @author by WuSang on 2018/2/8.
 */

public class PendingNotificationReceiver extends BroadcastReceiver {
    public final static String ACTION = "com.advanced.demo.notification.ACTION_SEND_PENDING_MSG";
    public final static String TAG = "PendingReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (TextUtils.equals(action, ACTION)) {
            Log.e(TAG, "receive action notification msg");
        }
    }
}
