package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.utils.CacheDataManager;

public class ActivityClean extends AppCompatActivity {

    androidx.appcompat.widget.Toolbar back;
    TextView cleanSize;
    Button clean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean);
        back = findViewById(R.id.button_clean_back);
        cleanSize = findViewById(R.id.tv_clean_size);
        clean = findViewById(R.id.Button_clean_yes);

        try {
            cleanSize.setText(CacheDataManager.getTotalCacheSize(ActivityClean.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ActivityClean.this)
                        .setTitle("清除缓存")
                        .setMessage("此操作会删除本地所有数据，是否确定删除？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Thread(new clearCache()).start();
                            }
                        }).show();
            }
        });
    }
    /**
     * 创建Handler
     * 接收消息
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(ActivityClean.this, "清理完成", Toast.LENGTH_SHORT).show();

                    try {
                        cleanSize.setText(CacheDataManager.getTotalCacheSize(ActivityClean.this));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    /**
     * 创建内部类，清除缓存
     */
    class clearCache implements Runnable {
        @Override
        public void run() {
            try {
                CacheDataManager.clearAllCache(ActivityClean.this);

                Thread.sleep(1000);

                if (CacheDataManager.getTotalCacheSize(ActivityClean.this).startsWith("0")) {

                    handler.sendEmptyMessage(0);
                }
            } catch (Exception e) {
                return;
            }
        }
    }



}