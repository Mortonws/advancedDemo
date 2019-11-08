package com.advanced.demo.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.advanced.demo.R;

/**
 * Created by tujiong on 2018/11/29.
 */
public class NotificationUtil {

    private static int NOTIFICATION_ID = 610;
    private static int NOTIFICATION = 121112;

    public static void showSimpleNotification(Context context, PendingIntent contentIntent, String ticker, String title, String description) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify = buildNotification(context,notificationManager,contentIntent,ticker,title,description,null);
        NOTIFICATION_ID += 1;
        notificationManager.notify(NOTIFICATION_ID, notify);
    }

    public static Notification buildNotification(Context context,NotificationManager notificationManager, PendingIntent contentIntent, String ticker, String title, String description, Bitmap bigIcon) {
        Notification.Builder builder = new Notification.Builder(context);
        setChannelCompat(notificationManager, builder,
                NotificationUtil.CHANNEL_ID, NotificationUtil.CHANNEL_NAME);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(ticker)//状态栏信息
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)//下拉title
                .setContentText(description)//下拉详情
                .setAutoCancel(true)
                .setContentIntent(contentIntent).setPriority(Notification.PRIORITY_MAX);

        if(bigIcon != null) { builder.setLargeIcon(bigIcon);
        }
        return builder.build();
    }

    public static final String CHANNEL_ID = "com.advanced.demo";
    public static final String CHANNEL_NAME = "sangw";

    public static void setChannelCompat(NotificationManager manager, Notification.Builder builder, String channelId, String channelName) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = createChannel(channelId, channelName);
            if (channel == null) {
                return;
            }
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
            if (builder != null) {
                builder.setChannelId(channelId);//这个id参数要与上面channel构建的第一个参数对应
            }
        }
    }

    public static void setChannelCompat(NotificationManager manager, NotificationCompat.Builder builder, String channelId, String channelName) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = createChannel(channelId, channelName);
            if (channel == null) {
                return;
            }
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
            if (builder != null) {
                builder.setChannelId(channelId);//这个id参数要与上面channel构建的第一个参数对应
            }
        }
    }

    private static NotificationChannel createChannel(String channelId, String channelName) {
        if (Build.VERSION.SDK_INT >= 26) {
            if (TextUtils.isEmpty(channelId) || TextUtils.isEmpty(channelName)) {
                Log.e("Notification", "channelId , channelName can not null sdk 26");
                return null;
            }

            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.canBypassDnd();
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setBypassDnd(true);
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_SECRET);
            return channel;
        }
        return null;
    }
}
