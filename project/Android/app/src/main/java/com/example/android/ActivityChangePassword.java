package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.utils.Apiurls;
import com.example.android.utils.NetUtil;
import com.example.android.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityChangePassword extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar back;
    private EditText Epassword;
    private EditText Enewpassword;
    private EditText Enewpassword2;

    private Button yes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        back = findViewById(R.id.button_for_changepassword_back);
        Epassword = findViewById(R.id.for_changepassword_et_password);
        Enewpassword = findViewById(R.id.for_changepassword_et_newpassword);
        Enewpassword2 = findViewById(R.id.for_changepassword_et_newpassword2);
        yes = findViewById(R.id.for_changepassword_bt_yes);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = Epassword.getText().toString();
                String newpassword = Enewpassword.getText().toString();
                String newpassword2 = Enewpassword2.getText().toString();

                if (!password.isEmpty() && !newpassword.isEmpty() && !newpassword2.isEmpty()) {
                    String url = Apiurls.server + Apiurls.changePassword;
                    JSONObject params = new JSONObject();
                    try {
                        params.put("password", password);
                        params.put("newPassword", newpassword);
                        params.put("newPassword2", newpassword2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
                        @Override
                        public <T> void onResponse(JSONObject jsonObject) throws JSONException {
                            ToastUtil.showMsg(getApplicationContext(),"修改成功");
                            finish();
                        }
                    };
                    NetUtil.requestSimple(getApplicationContext(),NetUtil.POST,url,params,listenerT);


                } else {
                    ToastUtil.showMsg(getApplicationContext(), "请输入完整!");
                }
            }
        });

    }
}