package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.utils.Apiurls;
import com.example.android.utils.MyActivity;
import com.example.android.utils.NetUtil;
import com.example.android.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityForFeedback extends AppCompatActivity {
    private androidx.appcompat.widget.Toolbar back;
    private EditText ctitle;
    private EditText ccontent;
    private Button complete;
    private TextView ToList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_feed_back);

        back = findViewById(R.id.button_for_feedback_back);
        ctitle = findViewById(R.id.for_feedback_et_title);
        ccontent = findViewById(R.id.for_feedback_et_content);
        complete = findViewById(R.id.for_feedback_bt_yes);
        ToList = findViewById(R.id.for_feedback_tv_tolist);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = ctitle.getText().toString();
                String content = ccontent.getText().toString();
                String url = Apiurls.server + Apiurls.addMultiple;
                int type = 1;
                if (!title.isEmpty() && !content.isEmpty()) {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("typeId", type);
                        params.put("title", title);
                        params.put("content", content);
                        params.put("img", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
                        @Override
                        public <T> void onResponse(JSONObject jsonObject) {
                            ToastUtil.showMsg(getApplicationContext(), "发送成功!");
                            finish();
                        }
                    };
                    NetUtil.requestSimple(getApplicationContext(), NetUtil.POST, url, params, listenerT);

                } else {//若空
                    ToastUtil.showMsg(ActivityForFeedback.this,"请填写完整");
                }
            }
        });

        ToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityForFeedback.this, ActivityFeedbackList.class);
                startActivity(intent);
            }
        });
    }
}