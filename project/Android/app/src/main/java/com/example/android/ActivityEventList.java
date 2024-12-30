package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.android.adapter.EventAdapter;
import com.example.android.enity.EventClass;
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

public class ActivityEventList extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar back;

    private RecyclerView eventrecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        back = findViewById(R.id.button_event_list_back);
        eventrecyclerView = findViewById(R.id.frag_rv_event_list);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //需要调用一下下面的初始化函数
        List<Map<String, String>> eventlist = new ArrayList<>();
        JSONObject params = new JSONObject();
        try {
            params.put("pagingRequest", PageRequest.getPage());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = Apiurls.server + Apiurls.showActivity;
        NetUtil.NetListenerT ListenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject response) throws Exception {
                List<JSONObject> list = new ArrayList();
                JSONArray data = response.getJSONObject("data").getJSONArray("records");
                for(int i = 0;i < data.length();i++) {
                    Map<String,String> map = new HashMap<>();
                    map.put("title", data.getJSONObject(i).getString("title"));
                    map.put("note", data.getJSONObject(i).getString("note"));
                    map.put("startTime", data.getJSONObject(i).getString("startTime"));
                    map.put("endTime", data.getJSONObject(i).getString("endTime"));
                    map.put("needCount", String.valueOf(data.getJSONObject(i).getInt("needCount")));
                    map.put("during", String.valueOf(data.getJSONObject(i).getDouble("during")));
                    map.put("id", String.valueOf(data.getJSONObject(i).getInt("id")));
                    eventlist.add(map);
                }
                initList(eventlist, eventrecyclerView);
            }
        };
        NetUtil.requestSimple(getApplicationContext(), NetUtil.POST, url, params, ListenerT);



    }

    //需要调用的初始化活动列表
    public void initList(List<Map<String, String>> eventlist, RecyclerView recyclerView) {
        ArrayList<EventClass> eventClasses = new ArrayList<>();
        EventAdapter eventAdapter = new EventAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.context) {
            @Override
            public boolean canScrollVertically() {
                return super.canScrollVertically();//false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        for (Map<String, String> map :
                eventlist) {
            EventClass eventClass = new EventClass();
            eventClass.setTitle(map.get("title"));
            eventClass.setContent(map.get("note"));
            eventClass.setStartTime("开始时间:"+map.get("startTime"));
            eventClass.setEndTime("结束时间:"+map.get("endTime"));
            eventClass.setNeedCount("需要人数:" + map.get("needCount"));
            eventClass.setDuring("志愿时长" + map.get("during") + "h");
            eventClass.setId(Integer.valueOf(map.get("id")));
            eventClasses.add(eventClass);
        }
        eventAdapter = new EventAdapter(eventClasses);
        recyclerView.setAdapter(eventAdapter);
    }
}