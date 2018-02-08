package com.advanced.demo.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by wusang on 2018/2/8.
 */

public class PendingNotificationActivity extends BaseActivity {
    private Button mBtnSendPendingNotificationMsg;

    @Override
    protected void initView() {
        super.initView();
        mBtnSendPendingNotificationMsg = findViewById(R.id.btn_send_pending_notification_msg);
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
    }

    private void sendPendingNotificationMsg() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "Demo")
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Demo Title")
                        .setContentText("Demo, Hello World!");

        Intent resultIntent = new Intent(this, DemoActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        int notificationId = 1001;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        if (notificationManager != null) {
            notificationManager.notify(notificationId, notification);
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_pending_notification;
    }
}