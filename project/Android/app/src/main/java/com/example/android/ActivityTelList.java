package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.android.adapter.TelAdapter;
import com.example.android.enity.PageRequest;
import com.example.android.enity.TelClass;
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

public class ActivityTelList extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar back;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_list);

        back = findViewById(R.id.button_tel_list_back);
        recyclerView = findViewById(R.id.frag_rv_tel_list);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        List<Map<String, String>> tellist = new ArrayList<>();
        JSONObject params = new JSONObject();
        try {
            params.put("pagingRequest", PageRequest.getPage());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = Apiurls.server + Apiurls.showTel;
        NetUtil.NetListenerT ListenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject response) throws Exception {
                List<JSONObject> list = new ArrayList();
                JSONArray data = response.getJSONObject("data").getJSONArray("records");
                for (int i = 0; i < data.length(); i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("note", data.getJSONObject(i).getString("note"));
                    map.put("tel", data.getJSONObject(i).getString("tel"));
                    tellist.add(map);
                }
                initList(tellist, recyclerView);
            }
        };
        NetUtil.requestSimple(getApplicationContext(), NetUtil.POST, url, params, ListenerT);
    }


    public void initList(List<Map<String, String>> tellist, RecyclerView recyclerView) {
        ArrayList<TelClass> telClasses = new ArrayList<>();
        TelAdapter telAdapter = new TelAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.context) {
            @Override
            public boolean canScrollVertically() {
                return super.canScrollVertically();//false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        for (Map<String, String> map :
                tellist) {
            TelClass telClass = new TelClass();
            telClass.setNote(map.get("note"));
            telClass.setTel(map.get("tel"));
            telClasses.add(telClass);
        }
        telAdapter = new TelAdapter(telClasses);
        recyclerView.setAdapter(telAdapter);
    }


}