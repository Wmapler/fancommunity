package com.example.android.ui.notifications;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.ActivityAbout;
import com.example.android.ActivityApplyList;
import com.example.android.ActivityChangePassword;
import com.example.android.ActivityClean;
import com.example.android.ActivityFeedbackList;
import com.example.android.ActivityPerInfomation;
import com.example.android.ActivityRepairList;
import com.example.android.ActivitySignIn;
import com.example.android.ActivityTelList;
import com.example.android.R;
import com.example.android.databinding.FragmentNotificationsBinding;
import com.example.android.utils.Apiurls;
import com.example.android.utils.MyApplication;
import com.example.android.utils.NetUtil;
import com.example.android.utils.ShowImageUtils;
import com.example.android.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationsFragment extends Fragment {

    private ImageView icon;
    private TextView cname;
    private TextView ctel;
    private ImageView info;
    private TextView repair;
    private TextView event;
    private TextView message;
    private TextView feedback;
    private TextView questionnaire;
    private TextView editPassword;
    private TextView clean;
    private TextView about;
    private TextView logout;


    private FragmentNotificationsBinding binding;

    public static NotificationsFragment newInstance(String s1, String s2) {
        NotificationsFragment fragment = new NotificationsFragment();
        Bundle args = new Bundle();
        Context context = MyApplication.context;
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        icon = root.findViewById(R.id.myself_iv_icon);
        cname = root.findViewById(R.id.myself_tv_name);
        ctel = root.findViewById(R.id.myself_tv_phone);
        info = root.findViewById(R.id.myself_toinfo_im_info);
        repair = root.findViewById(R.id.myself_tv_repair);
        event = root.findViewById(R.id.myself_tv_event_enroll);
        message = root.findViewById(R.id.myself_tv_message);
        feedback = root.findViewById(R.id.myself_tv_feedback);
        //questionnaire = root.findViewById(R.id.myself_tv_questionnaire);
        editPassword = root.findViewById(R.id.myself_tv_edit_password);
        clean = root.findViewById(R.id.myself_tv_clean_data);
        about = root.findViewById(R.id.myself_about_version);
        logout = root.findViewById(R.id.myself_tv_logout);
        String tel = "";
        String name = "";
        SharedPreferences user_data = MyApplication.context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE);
        tel = user_data.getString("tel", "");
        name = user_data.getString("name", "");


        ctel.setText(tel);
        cname.setText(name);

        Context context = MyApplication.context;
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityPerInfomation.class);
                startActivity(intent);
            }
        });

        repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityRepairList.class);
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityFeedbackList.class);
                startActivity(intent);
            }
        });

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityApplyList.class);
                startActivity(intent);
            }
        });

        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityChangePassword.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityAbout.class);
                startActivity(intent);
            }
        });

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityClean.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("确认退出?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        JSONObject params = new JSONObject();
                        String url = Apiurls.server + Apiurls.logout;
                        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
                            @Override
                            public <T> void onResponse(JSONObject response) throws Exception {
                                //退出登录
                                ToastUtil.showMsg(context,"退出成功");
                                //清空本地存储
                                SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putInt("auto", 0);
                                editor.commit();
                                Intent intent = new Intent(context, ActivitySignIn.class);
                                //下面2个flags ,可以将原有任务栈清空
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        };
                        NetUtil.requestSimple(context, NetUtil.POST, url, params, listenerT);
                        //退出登录
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


        getInfo();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getInfo() {
        JSONObject params = new JSONObject();
        String url = Apiurls.server + Apiurls.getInformation;

        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject response) throws JSONException {
                JSONObject data = response.getJSONObject("data");
                ShowImageUtils.showImage(getContext(), data.getString("headPicUrl"), icon);
            }
        };
        NetUtil.requestSimple(getContext(), NetUtil.POST, url, params, listenerT);
    }
}