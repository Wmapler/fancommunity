package com.example.android.ui.dashboard;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.ActivityDefraud;
import com.example.android.ActivityEventList;
import com.example.android.ActivityForFeedback;
import com.example.android.ActivityForRepair;
import com.example.android.ActivityNearby;
import com.example.android.ActivityNoticeList;
import com.example.android.ActivityOneNotice;
import com.example.android.ActivityPloice;
import com.example.android.ActivityTelList;
import com.example.android.ActivityWeather;
import com.example.android.R;
import com.example.android.databinding.FragmentDashboardBinding;
import com.example.android.utils.MyApplication;

public class DashboardFragment extends Fragment {

    private ImageView notice;
    private ImageView event;

    private ImageView defraud;

    private ImageView repairs;
    private ImageView weather;
    private ImageView nearby;
    private ImageView telephone;
    private ImageView feedback;
    private ImageView police;




    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        notice = (ImageView) root.findViewById(R.id.frag_iv_notice);
        event = (ImageView) root.findViewById(R.id.frag_iv_event);
        defraud = (ImageView) root.findViewById(R.id.frag_iv_defraud);
        repairs = (ImageView) root.findViewById(R.id.frag_iv_repairs);
        weather = (ImageView) root.findViewById(R.id.frag_iv_tools);
        nearby = (ImageView) root.findViewById(R.id.frag_iv_nearby);
        telephone = (ImageView) root.findViewById(R.id.frag_iv_telephone);
        feedback = (ImageView) root.findViewById(R.id.frag_iv_feedback);
        police = (ImageView) root.findViewById(R.id.frag_iv_police);

        Context context = MyApplication.context;

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityNoticeList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityEventList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        repairs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityForRepair.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityForFeedback.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityTelList.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityWeather.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        defraud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityDefraud.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityPloice.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityNearby.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}