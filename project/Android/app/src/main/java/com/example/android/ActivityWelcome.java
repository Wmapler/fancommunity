package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.android.utils.Apiurls;
import com.example.android.utils.MyActivity;
import com.example.android.utils.NetUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityWelcome extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
                Integer auto = pref.getInt("auto", 0);
                //在这里判断，是否需要自动登录
                Intent mainIntent;
                if (auto == 0) {
                    mainIntent = new Intent(ActivityWelcome.this, ActivitySignIn.class);
                } else {
                    mainIntent = new Intent(ActivityWelcome.this, ActivityHomepage.class);
                }
                ActivityWelcome.this.startActivity(mainIntent);
                ActivityWelcome.this.finish();
            }
        },2000);
    }
}