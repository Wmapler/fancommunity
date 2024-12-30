package com.example.android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.utils.Apiurls;
import com.example.android.utils.MyActivity;
import com.example.android.utils.NetUtil;
import com.example.android.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.HashMap;

public class ActivityForRepair extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar back;
    private EditText ctitle;
    private EditText ccontent;
    private Button complete;
    private TextView ToList;

    private ImageView upimg;
    private Button upbtn;
    public static final int CHOOSE_PHOTO = 2;

    private String b64="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_repair);

        back = findViewById(R.id.button_for_repair_back);
        ctitle = findViewById(R.id.for_repair_et_title);
        ccontent = findViewById(R.id.for_repair_et_content);
        complete = findViewById(R.id.for_repair_bt_yes);
        ToList = findViewById(R.id.for_repair_tv_tolist);
        upimg = findViewById(R.id.for_repair_im_up_img);
        upbtn = findViewById(R.id.for_repair_bt_up_img);


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
                int type = 3;
                if (!title.isEmpty() && !content.isEmpty()) {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("typeId", type);
                        params.put("title", title);
                        params.put("content", content);
                        params.put("img", b64);
                        //Log.d("wf", b64);
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
                    ToastUtil.showMsg(ActivityForRepair.this,"请填写完整");
                }
            }
        });

        ToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityForRepair.this, ActivityRepairList.class);
                startActivity(intent);
            }
        });


        upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(ActivityForRepair.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ActivityForRepair.this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOOSE_PHOTO);
                } else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        } else {
            switch (requestCode) {
                case CHOOSE_PHOTO:
                    if (resultCode == RESULT_OK) {
                        handleImageOnKitKat(data);
                    }
                    break;
                default:
                    break;
            }
        }


        /*switch (requestCode) {
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
                Glide.with(getApplicationContext()).load(path).into(upimg);
                HashMap<String, Object> params = new HashMap<>();
                String base64=bitmapToBase64(bitmap);
                //params.put("file",bitmap);
                params.put("img", base64);
                System.out.println(base64);
                String url = Apiurls.server+Apiurls.uploadHeadPic;
                NetUtil.uploadFile(ActivityForRepair.this,url,params);
                break;
        }*/
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Bitmap bitmap = null;
        if (DocumentsContract.isDocumentUri(this, uri)) {
            //如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的 id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的Uri,则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的U「i,直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); //根据图片路径显示图片
    }

    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            b64 = bitmapToBase64(bitmap);
            upimg.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }
    }
    //打开相册
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }
    public String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}