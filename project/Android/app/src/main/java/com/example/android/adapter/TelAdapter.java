package com.example.android.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.R;
import com.example.android.enity.TelClass;
import com.example.android.utils.MyApplication;

import java.util.ArrayList;


public class TelAdapter extends RecyclerView.Adapter<TelAdapter.ViewHolder> {

    ArrayList<TelClass> tellist = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView TelNote;
        TextView Teltel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            TelNote = (TextView) itemView.findViewById(R.id.item_tel_note);
            Teltel = (TextView) itemView.findViewById(R.id.item_tel_tel);
        }
    }

    public TelAdapter(){}

    public TelAdapter(ArrayList<TelClass> telClasses) {
        tellist = telClasses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tel, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                TelClass telClass = tellist.get(position);

                Context context = MyApplication.context;
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("确认向" + telClass.getNote() + "拨打电话？");//设置对话框的标题
                builder.setMessage("电话:" + telClass.getTel());//设置对话框的内容
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //点击拨打
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + telClass.getTel()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //取消
                    }
                });
                AlertDialog call = builder.create();
                call.show();
            }
        });

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TelClass telClass = tellist.get(position);
        holder.TelNote.setText(telClass.getNote());
        holder.Teltel.setText(telClass.getTel());
    }

    @Override
    public int getItemCount() {
        return tellist.size();
    }
}
