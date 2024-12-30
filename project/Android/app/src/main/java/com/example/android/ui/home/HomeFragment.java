package com.example.android.ui.home;

import static com.example.android.ActivitySignUp.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.method.QwertyKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.example.android.ActivityOneNotice;
import com.example.android.ActivitySignIn;
import com.example.android.MainActivity;
import com.example.android.R;
import com.example.android.adapter.NoticeAdapter;
import com.example.android.adapter.WeatherAdapter;
import com.example.android.databinding.FragmentHomeBinding;

import com.example.android.enity.NoticeClass;
import com.example.android.enity.PageRequest;
import com.example.android.enity.WeatherClass;
import com.example.android.utils.Apiurls;
import com.example.android.utils.MyApplication;
import com.example.android.utils.NetUtil;
import com.example.android.utils.ToastUtil;
import com.google.gson.Gson;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.base.Lang;
import com.qweather.sdk.bean.base.Unit;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment {

    private TextView homename;
    private TextView city;
    private TextView condition;
    private TextView date;
    private TextView wind;
    private TextView humidity;
    private ImageView today;
    private TextView currenttemp;

    private TextView foretime;

    private RecyclerView noticerecyclerView;
    //private RecyclerView weatherrecyclerView;

    private String localcity = "";
    private FragmentHomeBinding binding;

    ///


    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homename = (TextView) root.findViewById(R.id.text_homename);
        city = (TextView) root.findViewById(R.id.frag_tv_city);
        condition = (TextView) root.findViewById(R.id.frag_tv_condition);
        date = (TextView) root.findViewById(R.id.frag_tv_date);
        wind = (TextView) root.findViewById(R.id.frag_tv_wind);
        humidity = (TextView) root.findViewById(R.id.frag_tv_temprange);
        today = (ImageView) root.findViewById(R.id.frag_iv_today);
        currenttemp = (TextView) root.findViewById(R.id.frag_tv_currenttemp);
        noticerecyclerView = root.findViewById(R.id.frag_rv_notice);
        //weatherrecyclerView = root.findViewById(R.id.frag_rv_weather);
        //foretime = root.findViewById(R.id.frag_tv_foretime);
        //设置小区名字获取
        String address = "";
        SharedPreferences user_data = MyApplication.context.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE);
        address = user_data.getString("address", "");
        Integer no = user_data.getInt("no", 0);
        homename.setText("");
        //HeConfig.init("HE2304221536271337", "2a0d9987f0b24fb195e367d83f20ea68");
        //切换至免费订阅
        //HeConfig.switchToDevService();
        /*这里写天气相关的东西*/
        init();

        SharedPreferences data = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        //判断是否认证
        int isok = data.getInt("myIdentity", 0);
        if (isok == 0) {
            new AlertDialog.Builder(getContext())
                    .setTitle("未认证")
                    .setMessage("请等待认证，认证后可正常登录，点击退出")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //退出登录
                            JSONObject params = new JSONObject();
                            String url = Apiurls.server + Apiurls.logout;
                            NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
                                @Override
                                public <T> void onResponse(JSONObject response) throws Exception {
                                    //退出登录
                                    ToastUtil.showMsg(getContext(),"退出成功");
                                    //清空本地存储
                                    SharedPreferences preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putInt("auto", 0);
                                    editor.commit();
                                    Intent intent = new Intent(getContext(), ActivitySignIn.class);
                                    //下面2个flags ,可以将原有任务栈清空
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            };
                            NetUtil.requestSimple(getContext(), NetUtil.POST, url, params, listenerT);
                            //退出登录
                        }
                    }).show();
        }

        /**/


        //获取省份并查询
        //下面开始写通知从后端获取，调用下面的函数
        if (no!=0) {//如有小区名字，直接算
            homename.setText(address);
            List<Map<String, String>> noticelist = new ArrayList<>();
            JSONObject params = new JSONObject();
            try {
                params.put("pagingRequest", PageRequest.getPage());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            String url = Apiurls.server + Apiurls.showNotice;
            NetUtil.NetListenerT ListenerT = new NetUtil.NetListenerT() {
                @Override
                public <T> void onResponse(JSONObject response) throws Exception {
                    List<JSONObject> list = new ArrayList();
                    JSONArray data = response.getJSONObject("data").getJSONArray("records");
                    for(int i = 0;i < data.length();i++) {
                        Map<String,String> map = new HashMap<>();
                        map.put("title", data.getJSONObject(i).getString("title"));
                        map.put("note", data.getJSONObject(i).getString("note"));
                        noticelist.add(map);
                    }
                    initList(noticelist, noticerecyclerView);
                }
            };
            Context context = MyApplication.context;
            NetUtil.requestSimple(context, NetUtil.POST, url, params, ListenerT);
        }
        return root;
    }

    //下面需要调用一下
    public void initList(List<Map<String, String>> noticelist, RecyclerView recyclerView) {
        ArrayList<NoticeClass> noticeClasses = new ArrayList<>();
        NoticeAdapter noticeAdapter = new NoticeAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.context) {
            @Override
            public boolean canScrollVertically() {
                return super.canScrollVertically();//false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        for (Map<String, String> map :
                noticelist) {
            NoticeClass noticeClass = new NoticeClass();
            noticeClass.setTitle(map.get("title"));
            noticeClass.setContent("  "+map.get("note"));
            noticeClasses.add(noticeClass);
        }
        noticeAdapter = new NoticeAdapter(noticeClasses);
        recyclerView.setAdapter(noticeAdapter);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void init() {
        //初始化定位
        try {
            AMapLocationClient.updatePrivacyShow(getContext(), true, true);
            AMapLocationClient.updatePrivacyAgree(getContext(), true);
            mLocationClient = new AMapLocationClient(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    private class MyAMapLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    localcity = aMapLocation.getCity();
                    localcity = localcity.substring(0, 2);
                    city.setText(localcity);
                    //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
                    WeatherSearchQuery mquery1 = new WeatherSearchQuery(localcity, WeatherSearchQuery.WEATHER_TYPE_LIVE);
                    //WeatherSearchQuery mquery2 = new WeatherSearchQuery(localcity, WeatherSearchQuery.WEATHER_TYPE_FORECAST);
                    try {
                        WeatherSearch weatherSearch = new WeatherSearch(getContext());
                        weatherSearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
                            @Override
                            public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {

                                if (i == 1000) {
                                    if (localWeatherLiveResult != null&&localWeatherLiveResult.getLiveResult() != null) {
                                        LocalWeatherLive weatherlive = localWeatherLiveResult.getLiveResult();
                                        date.setText(weatherlive.getReportTime()+"发布");
                                        condition.setText(weatherlive.getWeather());
                                        currenttemp.setText(weatherlive.getTemperature()+"°");
                                        wind.setText(weatherlive.getWindDirection()+"风     "+weatherlive.getWindPower()+"级");
                                        humidity.setText("湿度         " + weatherlive.getHumidity() + "%");

                                        switch (weatherlive.getWeather()) {
                                            case "晴":
                                                today.setImageResource(R.mipmap.icon_100d);
                                                break;
                                            case "多云":
                                                today.setImageResource(R.mipmap.icon_101d);
                                                break;
                                            case "阴":
                                                today.setImageResource(R.mipmap.icon_102d);
                                                break;
                                            case "小雨":
                                                today.setImageResource(R.mipmap.icon_305d);
                                                break;
                                            case "中雨":
                                                today.setImageResource(R.mipmap.icon_306d);
                                                break;
                                            case "大雨":
                                                today.setImageResource(R.mipmap.icon_307d);
                                                break;
                                            case "暴雨":
                                                today.setImageResource(R.mipmap.icon_308d);
                                                break;
                                            case "阵雨":
                                                today.setImageResource(R.mipmap.icon_301d);
                                                break;
                                            case "雷阵雨":
                                                today.setImageResource(R.mipmap.icon_303d);
                                                break;
                                            case "霾":
                                                today.setImageResource(R.mipmap.icon_513d);
                                                break;
                                            case "雾":
                                                today.setImageResource(R.mipmap.icon_509d);
                                                break;
                                            case "小雪":
                                                today.setImageResource(R.mipmap.icon_400d);
                                                break;
                                            case "中雪":
                                                today.setImageResource(R.mipmap.icon_402d);
                                                break;
                                            case "大雪":
                                                today.setImageResource(R.mipmap.icon_401d);
                                                break;
                                            case "暴雪":
                                                today.setImageResource(R.mipmap.icon_403d);
                                                break;
                                            case "雨夹雪":
                                                today.setImageResource(R.mipmap.icon_404d);
                                                break;
                                            case "扬沙":
                                                today.setImageResource(R.mipmap.icon_503d);
                                                break;
                                            default:
                                                today.setImageResource(R.mipmap.icon_100d);
                                                break;
                                        }
                                        //today.setBackground();
                                    }else {
                                        ToastUtil.showMsg(getContext(),"获取失败");
                                    }
                                }else {
                                    ToastUtil.showMsg(getContext(), String.valueOf(i));
                                }
                            }

                            @Override
                            public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {
                                if (i == 1000) {
                                    if (localWeatherForecastResult != null&&localWeatherForecastResult.getForecastResult() != null) {
                                        LocalWeatherForecast forecastResult = localWeatherForecastResult.getForecastResult();
                                        List<Map<String, String>> weatherlist = new ArrayList<>();
                                        for (int j = 1; j < 4; j++) {
                                            Map weathermap = new HashMap();
                                            weathermap.put("date", forecastResult.getWeatherForecast().get(j).getDate());
                                            weathermap.put("week", forecastResult.getWeatherForecast().get(j).getWeek());
                                            switch (forecastResult.getWeatherForecast().get(j).getWeek()) {
                                                case "1":
                                                    weathermap.put("week", "星期一");
                                                    break;
                                                case "2":
                                                    weathermap.put("week", "星期二");
                                                    break;
                                                case "3":
                                                    weathermap.put("week", "星期三");
                                                    break;
                                                case "4":
                                                    weathermap.put("week", "星期四");
                                                    break;
                                                case "5":
                                                    weathermap.put("week", "星期五");
                                                    break;
                                                case "6":
                                                    weathermap.put("week", "星期六");
                                                    break;
                                                case "7":
                                                    weathermap.put("week", "星期日");
                                                    break;
                                            }
                                            weathermap.put("weather", forecastResult.getWeatherForecast().get(j).getDayWeather());
                                            weathermap.put("temp", forecastResult.getWeatherForecast().get(j).getDayTemp() + "/" + forecastResult.getWeatherForecast().get(j).getNightTemp());
                                            weatherlist.add(weathermap);
                                        }
                                        //initForeWeather(weatherlist, weatherrecyclerView);
                                    }
                                }
                            }
                        });
                        weatherSearch.setQuery(mquery1);
                        //weatherSearch.setQuery(mquery2);
                        weatherSearch.searchWeatherAsyn();/*异步搜索*/
                    } catch (AMapException e) {
                        throw new RuntimeException(e);
                    }
                    /*Log.e("地址",localcity );
                    Log.e("位置：", aMapLocation.getAddress());*/
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    }


    public void initForeWeather(List<Map<String,String>> weatherlist,RecyclerView recyclerView){
        ArrayList<WeatherClass> weatherClasses = new ArrayList<>();
        WeatherAdapter weatherAdapter = new WeatherAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.context) {
            @Override
            public boolean canScrollVertically() {
                return super.canScrollVertically();//false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        for (Map<String, String> map :
                weatherlist) {
            WeatherClass weatherClass = new WeatherClass();
            weatherClass.setDate(map.get("date"));
            weatherClass.setWeek(map.get("week"));
            weatherClass.setWeather(map.get("weather"));
            weatherClass.setTemp(map.get("temp"));
            weatherClasses.add(weatherClass);
        }
        weatherAdapter = new WeatherAdapter(weatherClasses);
        recyclerView.setAdapter(weatherAdapter);
    }

    /*获取调整SH1*/
    /*public static String sHA1(Context context){
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    /**
     * 实时天气查询回调
     */
    /*@Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult , int rCode) {
        if (rCode == 1000) {
            if (weatherLiveResult != null&&weatherLiveResult.getLiveResult() != null) {
                LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                date.setText(weatherlive.getReportTime()+"发布");
                condition.setText(weatherlive.getWeather());
                currenttemp.setText(weatherlive.getTemperature()+"°");
                wind.setText(weatherlive.getWindDirection()+"风     "+weatherlive.getWindPower()+"级");
                humidity.setText("湿度         "+weatherlive.getHumidity()+"%");
            }else {
                ToastUtil.showMsg(getContext(),"获取失败");
            }
        }else {
            ToastUtil.showMsg(getContext(), String.valueOf(rCode));
        }
    }*/

}