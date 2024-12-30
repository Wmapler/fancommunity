package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.utils.MyActivity;

public class ActivityOneApply extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar buttonBack;
    //private ImageButton buttonSpeak;
    private TextView oneTitle;
    private TextView oneContent;
    private TextView beginTime;
    private TextView endTime;
    private TextView duringTime;

    private TextView needCount;
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
        setContentView(R.layout.activity_one_apply);

        buttonBack = findViewById(R.id.button_one_apply_back);
        //buttonSpeak = findViewById(R.id.event_detail_speak);
        oneTitle = findViewById(R.id.apply_detail_title);
        oneContent = findViewById(R.id.apply_detail_content);
        beginTime = findViewById(R.id.apply_start_time);
        endTime = findViewById(R.id.apply_end_time);
        duringTime = findViewById(R.id.apply_during_time);
        needCount = findViewById(R.id.apply_need_count);

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
    }
}