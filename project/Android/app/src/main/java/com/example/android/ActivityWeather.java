package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import com.example.android.adapter.WeatherAdapter;
import com.example.android.enity.WeatherClass;
import com.example.android.ui.home.HomeFragment;
import com.example.android.utils.MyActivity;
import com.example.android.utils.MyApplication;
import com.example.android.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityWeather extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar back;

    private RecyclerView weatherrecyclerView;

    private TextView foretime;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new ActivityWeather.MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String localcity = "天津";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        back = findViewById(R.id.button_weather_list_back);
        weatherrecyclerView = findViewById(R.id.frag_rv_weather_list);
        foretime = findViewById(R.id.frag_tv_foretime);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        init();


    }

    private void init() {
        //初始化定位
        try {
            AMapLocationClient.updatePrivacyShow(getApplicationContext(), true, true);
            AMapLocationClient.updatePrivacyAgree(getApplicationContext(), true);
            mLocationClient = new AMapLocationClient(getApplicationContext());
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

                    //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
                    //WeatherSearchQuery mquery1 = new WeatherSearchQuery(localcity, WeatherSearchQuery.WEATHER_TYPE_LIVE);
                    WeatherSearchQuery mquery2 = new WeatherSearchQuery(localcity, WeatherSearchQuery.WEATHER_TYPE_FORECAST);
                    try {
                        WeatherSearch weatherSearch = new WeatherSearch(getApplicationContext());
                        weatherSearch.setOnWeatherSearchListener(new WeatherSearch.OnWeatherSearchListener() {
                            @Override
                            public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
                            }
                            @Override
                            public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {
                                if (i == 1000) {
                                    if (localWeatherForecastResult != null&&localWeatherForecastResult.getForecastResult() != null) {
                                        LocalWeatherForecast forecastResult = localWeatherForecastResult.getForecastResult();
                                        List<Map<String, String>> weatherlist = new ArrayList<>();

                                        foretime.setText(forecastResult.getReportTime());
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
                                            weathermap.put("temp", forecastResult.getWeatherForecast().get(j).getDayTemp() + "/" + forecastResult.getWeatherForecast().get(j).getNightTemp() + "℃");
                                            weathermap.put("wind", forecastResult.getWeatherForecast().get(j).getDayWindDirection()+"风" + forecastResult.getWeatherForecast().get(j).getDayWindPower()+"级");
                                            weatherlist.add(weathermap);
                                        }
                                        initForeWeather(weatherlist, weatherrecyclerView);
                                    }
                                }
                            }
                        });
                        //weatherSearch.setQuery(mquery1);
                        weatherSearch.setQuery(mquery2);
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
            weatherClass.setWind(map.get("wind"));
            weatherClasses.add(weatherClass);
        }
        weatherAdapter = new WeatherAdapter(weatherClasses);
        recyclerView.setAdapter(weatherAdapter);
    }
}