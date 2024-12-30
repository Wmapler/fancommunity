package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.android.adapter.NoticeAdapter;
import com.example.android.enity.NoticeClass;
import com.example.android.enity.PageRequest;
import com.example.android.utils.Apiurls;
import com.example.android.utils.MyActivity;
import com.example.android.utils.MyApplication;
import com.example.android.utils.NetUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityNoticeList extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar back;
    private ImageButton speak;

    private RecyclerView noticerecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        back = findViewById(R.id.button_notice_list_back);
        noticerecyclerView = findViewById(R.id.frag_rv_notice_list);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //调用下下面的函数初始化界面

        List<Map<String, String>> noticelist = new ArrayList<>();
        JSONObject params = new JSONObject();
        try {
            params.put("pagingRequest", PageRequest.getPage());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = Apiurls.server + Apiurls.showNotice;
        NetUtil.NetListenerT ListenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject response) throws Exception {
                List<JSONObject> list = new ArrayList();
                JSONArray data = response.getJSONObject("data").getJSONArray("records");
                for(int i = 0;i < data.length();i++) {
                    Map<String,String> map = new HashMap<>();
                    map.put("title", data.getJSONObject(i).getString("title"));
                    map.put("note", data.getJSONObject(i).getString("note"));
                    noticelist.add(map);
                }
                initList(noticelist, noticerecyclerView);
            }
        };
        NetUtil.requestSimple(getApplicationContext(), NetUtil.POST, url, params, ListenerT);
    }

    //需要调用的初始化通知公告
    public void initList(List<Map<String, String>> noticelist, RecyclerView recyclerView) {
        ArrayList<NoticeClass> noticeClasses = new ArrayList<>();
        NoticeAdapter noticeAdapter = new NoticeAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.context) {
            @Override
            public boolean canScrollVertically() {
                return super.canScrollVertically();//false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        for (Map<String, String> map :
                noticelist) {
            NoticeClass noticeClass = new NoticeClass();
            noticeClass.setTitle(map.get("title"));
            noticeClass.setContent("  "+map.get("note"));
            noticeClasses.add(noticeClass);
        }
        noticeAdapter = new NoticeAdapter(noticeClasses);
        recyclerView.setAdapter(noticeAdapter);
    }
}