package com.advanced.demo.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.MainActivity;
import com.advanced.demo.R;

/**
 * @author by wusang on 2018/2/8.
 */

public class PendingNotificationActivity extends BaseActivity {
    private Button mBtnSendPendingNotificationMsg;
    private PendingNotificationReceiver mReceiver;

    @Override
    protected void initView() {
        super.initView();
        mBtnSendPendingNotificationMsg = findViewById(R.id.btn_send_pending_notification_msg);
    }

    @Override
    protected void initPages() {
        super.initPages();
        mReceiver = new PendingNotificationReceiver();
        IntentFilter filter = new IntentFilter(PendingNotificationReceiver.ACTION);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnSendPendingNotificationMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPendingNotificationMsg();
            }
        });
        findViewById(R.id.btn_send_pending_receiver_msg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(PendingNotificationReceiver.TAG, "receiver msg click");
                sendPendingReceiverMsg();
            }
        });
    }

    public final static String PUSH_EXTRA_KEY = "com.advanced.demo.PUSH_EXTRA_KEY";
    private void sendPendingNotificationMsg() {
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra(PUSH_EXTRA_KEY, "pending_push");
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationUtil.showSimpleNotification(this,resultPendingIntent, "sangwTicker", "Demo Title", "Demo, Hello World!");
    }

    private void sendPendingReceiverMsg() {
        Intent intent = new Intent(this, PendingNotificationReceiver.class);
        intent.setAction(PendingNotificationReceiver.ACTION);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 500, pendingIntent);
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_pending_notification;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}