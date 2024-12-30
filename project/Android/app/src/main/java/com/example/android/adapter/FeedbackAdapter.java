package com.example.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.ActivityOneFeedback;
import com.example.android.R;
import com.example.android.enity.FeedbackClass;
import com.example.android.utils.MyApplication;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {

    ArrayList<FeedbackClass> feedbacklist = new ArrayList<>();
    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView FeedbackTitle;
        TextView FeedbackContent;
        TextView FeedbackStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            FeedbackTitle = (TextView) itemView.findViewById(R.id.item_feedback_title);
            FeedbackContent = (TextView) itemView.findViewById(R.id.item_feedback_content);
            FeedbackStatus = (TextView) itemView.findViewById(R.id.item_feedback_status);
        }
    }

    public FeedbackAdapter(){}

    public FeedbackAdapter(ArrayList<FeedbackClass> feedbackClasses) {
        feedbacklist = feedbackClasses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feedback, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                //点击进入详情
                FeedbackClass feedbackClass = feedbacklist.get(position);
                Context context = MyApplication.context;
                ActivityOneFeedback.title = feedbackClass.getTitle();
                ActivityOneFeedback.content = feedbackClass.getContent();
                ActivityOneFeedback.reply = feedbackClass.getReply();
                if (feedbackClass.getStatus() == 0) {
                    ActivityOneFeedback.status = "未解决";
                } else if (feedbackClass.getStatus() == 1) {
                    ActivityOneFeedback.status = "已解决";
                }else if (feedbackClass.getStatus() == 2){
                    ActivityOneFeedback.status = "解决中";
                }
                Intent intent = new Intent(context, ActivityOneFeedback.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedbackClass feedbackClass = feedbacklist.get(position);
        holder.FeedbackTitle.setText(feedbackClass.getTitle());
        holder.FeedbackContent.setText(feedbackClass.getContent());
        if (feedbackClass.getStatus() == 0) {
            holder.FeedbackStatus.setText("未解决");
        } else if (feedbackClass.getStatus() == 1) {
            holder.FeedbackStatus.setText("已解决");
        }else if (feedbackClass.getStatus() == 2){
            holder.FeedbackStatus.setText("解决中");
        }
    }

    @Override
    public int getItemCount() {
        return feedbacklist.size();
    }
}
