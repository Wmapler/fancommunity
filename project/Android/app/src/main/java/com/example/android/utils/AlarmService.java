package com.example.android.utils;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class AlarmService extends Service {


    SharedPreferences preferences = MyApplication.context.getSharedPreferences("user", Context.MODE_PRIVATE);
    Integer oldCount = preferences.getInt("oldCount",0);
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable(){
            @Override
            public void run() {//可以在该线程中做需要处理的事
//                System.out.println("当前时间："+new Date().toString());//获取日期时间
//                查询未读消息的网络请求
                String url = Apiurls.server + Apiurls.countUnreadMessage;
                JSONObject params = new JSONObject();
                NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
                    @Override
                    public <T> void onResponse(JSONObject response) throws JSONException, InterruptedException {
                        int count = response.getInt("data");
                    if(count > 0 && oldCount != count) {
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putInt("oldCount", count);
                        editor.commit();
                        NoticeUtil.showNotice(getApplicationContext(),(NotificationManager) getSystemService(NOTIFICATION_SERVICE),count);
                    }

                    }
                };
                NetUtil.requestSimple(getApplicationContext(),NetUtil.POST,url,params,listenerT);

                stopSelf();//关闭服务
            }
        }).start();
        AlarmManager manger=(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i=new Intent(this,AlarmReceiver.class);//广播接收
        //PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity2_Text.this, 0, intent, 0);//意图为开启活动

        PendingIntent pendingIntent=PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_IMMUTABLE);//意图为开启广播

        long triggerAtTime = SystemClock.elapsedRealtime();//开机至今的时间毫秒数
        triggerAtTime = triggerAtTime + 2 * 1000;//比开机至今的时间增长2秒

        manger.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);//设置为开机至今的模式，时间，PendingIntent
        return super.onStartCommand(intent, flags, startId);
    }
}
