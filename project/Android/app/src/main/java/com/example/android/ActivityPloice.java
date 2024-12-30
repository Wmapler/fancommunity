package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.android.adapter.TelAdapter;
import com.example.android.enity.TelClass;
import com.example.android.utils.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityPloice extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar back;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ploice);


        back = findViewById(R.id.button_ploice_list_back);
        recyclerView = findViewById(R.id.frag_rv_ploice_list);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        List<Map<String, String>> tellist = new ArrayList<>();

        Map<String, String> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        Map<String, String> map3 = new HashMap<>();
        Map<String, String> map4 = new HashMap<>();
        map1.put("note", "公安");
        map1.put("tel", "110");
        map2.put("note", "火警");
        map2.put("tel", "119");
        map3.put("note", "医疗救护");
        map3.put("tel", "120");
        map4.put("note", "保卫处");
        map4.put("tel", "9999999");
        tellist.add(map1);
        tellist.add(map2);
        tellist.add(map3);
        tellist.add(map4);
        initList(tellist, recyclerView);
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