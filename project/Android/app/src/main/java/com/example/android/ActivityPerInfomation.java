package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.utils.Apiurls;
import com.example.android.utils.MyActivity;
import com.example.android.utils.NetUtil;
import com.example.android.utils.ShowImageUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityPerInfomation extends AppCompatActivity {

    private ImageView cheadPic;//头像
    private TextView cname;//姓名
    private TextView cgender;//性别
    private TextView ctel;//电话
    private TextView ctime;//志愿时长
    private TextView cvillage;//小区名字
    private TextView cbuilding;//楼号
    private TextView cunit;//单元
    private TextView croom;//具体房间号
    private Button btn_edit;//修改

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_infomation);

        cheadPic =(ImageView) findViewById(R.id.info_iv_s_card);
        cname = findViewById(R.id.info_tv_s_name);
        cgender = findViewById(R.id.info_tv_s_gender);
        ctel = findViewById(R.id.info_tv_s_telephone);
        ctime = findViewById(R.id.info_tv_s_time);
        cvillage = findViewById(R.id.info_tv_s_village);
        cbuilding = findViewById(R.id.info_tv_s_building);
        cunit = findViewById(R.id.info_tv_s_unit);
        croom = findViewById(R.id.info_tv_s_room);
        btn_edit = findViewById(R.id.per_info_mod_information);
        getInfo();
        androidx.appcompat.widget.Toolbar back = findViewById(R.id.edit_per_toolbar);
        btn_edit =(Button) findViewById(R.id.per_info_mod_information);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityPerInfomation.this, ActivityCompeleteInformation.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getInfo();
    }

    public void getInfo() {
        JSONObject params = new JSONObject();
        String url = Apiurls.server + Apiurls.getInformation;

        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject response) throws JSONException {
                JSONObject data = response.getJSONObject("data");
                cname.setText(data.getString("name"));
                String gender = (data.getString("gender").equals("0") ? "女" : "男");
                cgender.setText(gender);
                ctel.setText(data.getString("userId"));
                ctime.setText((data.getString("time")==null?data.getString("time"):"0") + "h");
                cvillage.setText(data.getString("address"));
                cbuilding.setText(data.getString("build"));
                cunit.setText(data.getString("major"));
                croom.setText(data.getString("house"));
                ShowImageUtils.showImage(getApplicationContext(), data.getString("headPicUrl"), cheadPic);
            }
        };
        NetUtil.requestSimple(getApplicationContext(), NetUtil.POST, url, params, listenerT);
    }
}