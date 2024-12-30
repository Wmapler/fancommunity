package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.utils.Apiurls;
import com.example.android.utils.MyActivity;
import com.example.android.utils.NetUtil;
import com.example.android.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityOneEvent extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar buttonBack;
    //private ImageButton buttonSpeak;
    private TextView oneTitle;
    private TextView oneContent;
    private TextView beginTime;
    private TextView endTime;
    private TextView duringTime;

    private TextView needCount;
    private Button enroll;

    public static String EventTitle;
    public static String EventContent;
    public static String EventBegin;
    public static String EventEnd;
    public static String EventNeed;
    public static String EventDuring;

    public static Integer id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_event);

        buttonBack = findViewById(R.id.button_one_event_back);
        //buttonSpeak = findViewById(R.id.event_detail_speak);
        oneTitle = findViewById(R.id.event_detail_title);
        oneContent = findViewById(R.id.event_detail_content);
        beginTime = findViewById(R.id.event_start_time);
        endTime = findViewById(R.id.event_end_time);
        duringTime = findViewById(R.id.event_during_time);
        needCount = findViewById(R.id.event_need_count);
        enroll = findViewById(R.id.event_detail_enroll);

        oneTitle.setText(EventTitle);
        oneContent.setText(EventContent);
        beginTime.setText(EventBegin);
        endTime.setText(EventEnd);
        needCount.setText(EventNeed);
        duringTime.setText(EventDuring);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //点击报名

        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //根据活动id报名
                String url = Apiurls.server + Apiurls.applyActivity;
                JSONObject params = new JSONObject();
                try {
                    params.put("id", id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
                    @Override
                    public <T> void onResponse(JSONObject jsonObject) {
                        ToastUtil.showMsg(getApplicationContext(), "报名成功!");
                        finish();
                    }
                };
                NetUtil.requestSimple(getApplicationContext(), NetUtil.POST, url, params, listenerT);
            }
        });


    }
}