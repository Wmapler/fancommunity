package com.example.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.ActivityOneNotice;
import com.example.android.R;
import com.example.android.enity.NoticeClass;
import com.example.android.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    ArrayList<NoticeClass> noticelist = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView NoticeTitle;
        TextView NoticeContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            NoticeTitle = (TextView) itemView.findViewById(R.id.item_notice_title);
            NoticeContent = (TextView) itemView.findViewById(R.id.item_notice_content);
        }
    }


    public NoticeAdapter(){

    }
    public NoticeAdapter(ArrayList<NoticeClass> noticeClasses) {
        noticelist = noticeClasses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice, parent, false);
        ViewHolder holder = new ViewHolder(view);



        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                //点击进入详情
                NoticeClass noticeClass = noticelist.get(position);
                Context context = MyApplication.context;
                ActivityOneNotice.title = noticeClass.getTitle();
                ActivityOneNotice.content = noticeClass.getContent();
                Intent intent = new Intent(context, ActivityOneNotice.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoticeClass noticeClass = noticelist.get(position);
        holder.NoticeTitle.setText(noticeClass.getTitle());
        holder.NoticeContent.setText(noticeClass.getContent());
    }

    @Override
    public int getItemCount() {
        return noticelist.size();
    }

}
