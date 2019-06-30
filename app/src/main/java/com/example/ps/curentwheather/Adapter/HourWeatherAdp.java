package com.example.ps.curentwheather.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ps.curentwheather.Model.Weather;
import com.example.ps.curentwheather.R;
import java.util.Date;
import java.util.List;

public class HourWeatherAdp extends RecyclerView.Adapter<HourWeatherAdp.HourVh> {

    private List<Weather> weatherList;
    Context context;

    public HourWeatherAdp(List<Weather> weatherList, Context context) {
        this.weatherList = weatherList;
        this.context = context;
    }

    @NonNull
    @Override
    public HourVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adp_hour_weather,viewGroup,false);
        return new HourVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourVh hourVh, int i) {
        hourVh.bindWeather(weatherList.get(i));
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class HourVh extends RecyclerView.ViewHolder {

        ImageView tempIv;
        TextView  weatherTv;
        TextView  timeTv;

    public HourVh(@NonNull View itemView) {
        super(itemView);
        tempIv = itemView.findViewById(R.id.adphour_tempIv);
        timeTv = itemView.findViewById(R.id.adphour_TimeTv);
        weatherTv = itemView.findViewById(R.id.adphour_WeatherTv);

    }

    void bindWeather(Weather weather){

        tempIv.setImageResource(R.drawable.ic_mosty_sunny_more_detail_24);
        Date date = weather.getTime();

        timeTv.setText(date.getHours()+":00");

        weatherTv.setText(Math.round(weather.getWeatherTemprature())+" C°");

    }
}

}
