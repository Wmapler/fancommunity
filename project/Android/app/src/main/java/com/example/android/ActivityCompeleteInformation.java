package com.example.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.android.utils.Apiurls;
import com.example.android.utils.MyActivity;
import com.example.android.utils.MyApplication;
import com.example.android.utils.NetUtil;
import com.example.android.utils.ShowImageUtils;
import com.example.android.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityCompeleteInformation extends AppCompatActivity {
    private ImageView cheadPic;//头像
    private EditText cname;//姓名
    private Spinner cgender;//性别
    private TextView ctel;//电话
    //Spinner province;//
    //Spinner city;//
    private TextView ctime;
    private Spinner cvillage;//小区名字
    private EditText cbuilding;//楼号
    private EditText cunit;//单元
    private EditText croom;//具体房间号
    private Button btn_complete;
    //private TextView change;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compelete_information);
        cheadPic =(ImageView) findViewById(R.id.info_iv_c_card);
        cname = findViewById(R.id.info_et_c_name);
        cgender = findViewById(R.id.info_sp_c_gender);
        ctel = findViewById(R.id.info_tv_c_telephone);
        ctime = findViewById(R.id.info_tv_c_time);
        cvillage = findViewById(R.id.info_sp_c_village);
        cbuilding = findViewById(R.id.info_et_c_building);
        cunit = findViewById(R.id.info_et_c_unit);
        croom = findViewById(R.id.info_et_c_room);
        btn_complete = findViewById(R.id.info_bt_c_complete);
        //change = findViewById(R.id.info_tv_c_photo);
        //设置电话
        SharedPreferences user_data = MyApplication.context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE);
        String tel = user_data.getString("tel","");
        ctel.setText(tel);
        getInfo();
        androidx.appcompat.widget.Toolbar back = findViewById(R.id.edit_per_toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = Apiurls.server + Apiurls.completeInformation;
                String name = cname.getText().toString();
                int sex = (int) cgender.getSelectedItemId();
                String address = (String) cvillage.getSelectedItem();
                String build = cbuilding.getText().toString();
                String major = cunit.getText().toString();
                String house = croom.getText().toString();

                if (!name.isEmpty() && !address.isEmpty() && !build.isEmpty() && !major.isEmpty()&&!house.isEmpty()) {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("name", name);
                        params.put("gender", sex);
                        params.put("address", address);
                        params.put("build", build);
                        params.put("major", major);
                        params.put("house", house);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
                        @Override
                        public <T> void onResponse(JSONObject jsonObject) {
                            ToastUtil.showMsg(getApplicationContext(), "完善成功!");
                            SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString("name", name);
                            editor.putString("tel", tel);
                            editor.putString("address", address);
                            editor.putInt("no", 1);
                            editor.commit();
                            finish();
                        }
                    };
                    NetUtil.requestSimple(getApplicationContext(), NetUtil.POST, url, params, listenerT);


                } else {
                    ToastUtil.showMsg(getApplicationContext(), "信息不完整（*必填）");
                }
            }
        });

        cheadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                //处理返回集
                startActivityForResult(intent, 2);
            }
        });
    }
    public void getInfo() {
        JSONObject params = new JSONObject();
        String url = Apiurls.server + Apiurls.getInformation;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject response) throws JSONException {

                JSONObject data = response.getJSONObject("data");

                cname.setText(data.getString("name"));
                int gender = (data.getString("gender").equals("0") ? 0 : 1);
                cgender.setSelection(gender);
                ctel.setText(data.getString("userId"));
                ctime.setText((data.getString("time")==null?data.getString("time"):"0") + "h");
                Resources res = getResources();
                String[] house = res.getStringArray(R.array.housing);
                String house1 = data.getString("address");
                int i = 0;
                for (i = 0; i < house.length; i++) {
                    if (house1.equals(house[i])) {
                        break;
                    }
                }
                if (i== house.length) i = 0;
                cvillage.setSelection(i);
                cbuilding.setText(data.getString("build"));
                cunit.setText(data.getString("major"));
                croom.setText(data.getString("house"));
                ShowImageUtils.showImage(getApplicationContext(), data.getString("headPicUrl"), cheadPic);
                ctel.setText(data.getString("user_id"));
                //ShowImageUtils.showImage(getApplicationContext(), data.getString("headPicUrl"), cheadPic);
            }
        };
        NetUtil.requestSimple(getApplicationContext(), NetUtil.POST, url, params, listenerT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1://拍照处理

            case 2://相册处理
                Uri path = data.getData();
                Bitmap bitmap = null;
                String img = "";
                try {
                    InputStream is = getContentResolver().openInputStream(path);
                    bitmap = BitmapFactory.decodeStream(is);
//                    img =bitmapToBase64(bitmap);
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //利用框架加载图片资源'
                Glide.with(getApplicationContext()).load(path).into(cheadPic);
                HashMap<String, Object> params = new HashMap<>();
                params.put("file",bitmap);
                String url = Apiurls.server+Apiurls.uploadHeadPic;
                NetUtil.uploadFile(ActivityCompeleteInformation.this,url,params);
                break;
        }
    }

    public String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bitmapBytes = baos.toByteArray();
//                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}