package com.example.android.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        System.out.println("接收到意图开启的广播,当前时间为："+new Date().toString());
        Intent i=new Intent(context,AlarmService.class);
        //context.startService(i);//开启AlarmService服务
    }
}