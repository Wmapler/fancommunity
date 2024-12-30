package com.example.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.R;
import com.example.android.enity.WeatherClass;



import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    ArrayList<WeatherClass> weatherlist = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView ViewDate;
        TextView ViewWeek;
        TextView ViewWeather;
        TextView ViewTemp;

        TextView ViewWind;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ViewDate = (TextView) itemView.findViewById(R.id.item_weather_date);
            ViewWeek = (TextView) itemView.findViewById(R.id.item_weather_week);
            ViewWeather = (TextView) itemView.findViewById(R.id.item_weather_weather);
            ViewTemp =(TextView)  itemView.findViewById(R.id.item_weather_temp);
            ViewWind =(TextView)  itemView.findViewById(R.id.item_weather_wind);

        }
    }


    public WeatherAdapter(){

    }
    public WeatherAdapter(ArrayList<WeatherClass> weatherClasses) {
        weatherlist = weatherClasses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherClass weatherClass = weatherlist.get(position);

        holder.ViewDate.setText(weatherClass.getDate());
        holder.ViewWeek.setText(weatherClass.getWeek());
        holder.ViewWeather.setText(weatherClass.getWeather());
        holder.ViewTemp.setText(weatherClass.getTemp());
        holder.ViewWind.setText(weatherClass.getWind());
    }

    @Override
    public int getItemCount() {
        return weatherlist.size();
    }
}
