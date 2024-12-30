package com.example.android.utils;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.navigation.Navigation;

import com.example.android.ActivityHomepage;
import com.example.android.R;


public class NoticeUtil {

    public static void showNotice(Context context,NotificationManager notificationManager,int count) {
        //定义一个PendingIntent点击Notification后启动一个Activity
        Intent it = new Intent(context, ActivityHomepage.class);
        it.putExtra("jumpId",1);

        String NOTIFICATION_CHANNEL_ID = "10001";
        PendingIntent pit = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pit = PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pit = PendingIntent.getActivity(context, 0, it, PendingIntent.FLAG_ONE_SHOT);
        }


        //设置图片,通知标题,发送时间,提示方式等属性
        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setContentTitle("阳光社区")                        //标题
                .setContentText("您有 " + count + " 条消息~")                 //内容
                .setWhen(System.currentTimeMillis())           //设置通知时间
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
//                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.biaobiao))  //设置自定义的提示音
                .setAutoCancel(true)                           //设置点击后取消Notification
                .setVibrate(new long[]{2000,1000,4000})
                .setContentIntent(pit);                        //设置PendingIntent

                /*.setSmallIcon(R.drawable.bottom_talking)            //设置小图标*/

        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        int NOTIFYID_1 = 1;
        Notification notify = mBuilder.build();

        notificationManager.notify(NOTIFYID_1, notify);
    }

}
