package com.example.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.ActivityOneRepair;
import com.example.android.R;
import com.example.android.enity.RepairClass;
import com.example.android.utils.MyApplication;

import java.util.ArrayList;

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.ViewHolder> {

    ArrayList<RepairClass> repairlist = new ArrayList<>();
    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView RepairTitle;
        TextView RepairContent;
        TextView RepairStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            RepairTitle = (TextView) itemView.findViewById(R.id.item_repair_title);
            RepairContent = (TextView) itemView.findViewById(R.id.item_repair_content);
            RepairStatus = (TextView) itemView.findViewById(R.id.item_repair_status);
        }
    }

    public RepairAdapter(){}
    public RepairAdapter(ArrayList<RepairClass> repairClasses){
        repairlist = repairClasses;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repair, parent, false);
        ViewHolder holder = new ViewHolder(view);


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                //点击进入详情
                RepairClass repairClass = repairlist.get(position);
                Context context = MyApplication.context;
                ActivityOneRepair.title = repairClass.getTitle();
                ActivityOneRepair.content = repairClass.getContent();
                if (repairClass.getStatus() == 0) {
                    ActivityOneRepair.status = "未解决";
                } else if (repairClass.getStatus() == 1) {
                    ActivityOneRepair.status = "已解决";
                }else if (repairClass.getStatus() == 2){
                    ActivityOneRepair.status = "解决中";
                }
                Intent intent = new Intent(context, ActivityOneRepair.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RepairClass repairClass = repairlist.get(position);
        holder.RepairTitle.setText(repairClass.getTitle());
        holder.RepairContent.setText(repairClass.getContent());
        if (repairClass.getStatus() == 0) {
            holder.RepairStatus.setText("未解决");
        } else if (repairClass.getStatus() == 1) {
            holder.RepairStatus.setText("已解决");
        }else if (repairClass.getStatus() == 2){
            holder.RepairStatus.setText("解决中");
        }
    }

    @Override
    public int getItemCount() {
        return repairlist.size();
    }


}
