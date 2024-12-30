package com.example.android;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.android.utils.AlarmService;
import com.example.android.utils.Apiurls;
import com.example.android.utils.MyActivity;
import com.example.android.utils.NetUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.android.databinding.ActivityHomepageBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityHomepage extends AppCompatActivity {

    private ActivityHomepageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_homepage);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        getInfo();

        //Intent intent=new Intent(ActivityHomepage.this, AlarmService.class);
        //startService(intent);//开启服务

    }



    public void getInfo() {

        JSONObject params = new JSONObject();
        String url = Apiurls.server+Apiurls.getInformation;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject response) throws JSONException {
                JSONObject data = response.getJSONObject("data");
                String name = (data.getString("name"));
                if(name.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityHomepage.this);
                    builder.setMessage("您的个人信息还不完善，请前往完善个人信息。").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ActivityHomepage.this,ActivityCompeleteInformation.class);
                            startActivity(intent);
                        }
                    }).show();
                }

            }
        };
        NetUtil.requestSimple(getApplicationContext(),NetUtil.POST,url,params,listenerT);
    }

}