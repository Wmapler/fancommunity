package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.android.adapter.FeedbackAdapter;
import com.example.android.enity.FeedbackClass;
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

public class ActivityFeedbackList extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar back;
    private Spinner spinner;
    private Button confirm;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);
        back = findViewById(R.id.button_feedback_list_back);
        spinner = findViewById(R.id.feedback_list_sp_status);
        confirm = findViewById(R.id.feedback_list_bt_ok);
        recyclerView = findViewById(R.id.frag_rv_feedback_list);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //

        List<Map<String, String>> feedbacklist = new ArrayList<>();
        JSONObject params = new JSONObject();
        try {
            params.put("pagingRequest", PageRequest.getPage());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = Apiurls.server + Apiurls.showMultiple;
        NetUtil.NetListenerT ListenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject response) throws Exception {
                List<JSONObject> list = new ArrayList();
                JSONArray data = response.getJSONObject("data").getJSONArray("records");
                for(int i = 0;i < data.length();i++) {
                    if (data.getJSONObject(i).getInt("typeId") == 1) {
                        if (data.getJSONObject(i).getString("state").equals("0")) {
                            Map<String, String> map = new HashMap<>();
                            map.put("title", data.getJSONObject(i).getString("title"));
                            map.put("content", data.getJSONObject(i).getString("content"));
                            map.put("status", data.getJSONObject(i).getString("state"));
                            map.put("reply", data.getJSONObject(i).getString("reply"));
                            feedbacklist.add(map);
                        }
                    }
                }
                initList(feedbacklist, recyclerView);
            }
        };
        NetUtil.requestSimple(getApplicationContext(), NetUtil.POST, url, params, ListenerT);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = ""+spinner.getSelectedItemPosition();
                List<Map<String, String>> feedbacklist = new ArrayList<>();
                JSONObject params = new JSONObject();
                try {
                    params.put("pagingRequest", PageRequest.getPage());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                String url = Apiurls.server + Apiurls.showMultiple;
                NetUtil.NetListenerT ListenerT = new NetUtil.NetListenerT() {
                    @Override
                    public <T> void onResponse(JSONObject response) throws Exception {
                        List<JSONObject> list = new ArrayList();
                        JSONArray data = response.getJSONObject("data").getJSONArray("records");
                        for(int i = 0;i < data.length();i++) {
                            if (data.getJSONObject(i).getInt("typeId") == 1) {
                                if (data.getJSONObject(i).getString("state").equals(status)) {
                                    Map<String,String> map = new HashMap<>();
                                    map.put("title", data.getJSONObject(i).getString("title"));
                                    map.put("content", data.getJSONObject(i).getString("content"));
                                    map.put("status", data.getJSONObject(i).getString("state"));
                                    map.put("reply", data.getJSONObject(i).getString("reply"));
                                    feedbacklist.add(map);
                                }
                            }
                        }
                        initList(feedbacklist, recyclerView);
                    }
                };
                NetUtil.requestSimple(getApplicationContext(), NetUtil.POST, url, params, ListenerT);
            }
        });

    }
    public void initList(List<Map<String, String>> feedbacklist, RecyclerView recyclerView) {
        ArrayList<FeedbackClass> feedbackClasses = new ArrayList<>();
        FeedbackAdapter feedbackAdapter = new FeedbackAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.context) {
            @Override
            public boolean canScrollVertically() {
                return super.canScrollVertically();//false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        for (Map<String, String> map :
                feedbacklist) {
            FeedbackClass feedbackClass = new FeedbackClass();
            feedbackClass.setTitle(map.get("title"));
            feedbackClass.setContent("  "+map.get("content"));
            feedbackClass.setStatus(Integer.parseInt(map.get("status")));
            feedbackClass.setReply(map.get("reply"));
            feedbackClasses.add(feedbackClass);
        }
        feedbackAdapter = new FeedbackAdapter(feedbackClasses);
        recyclerView.setAdapter(feedbackAdapter);
    }
}