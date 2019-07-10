package com.example.ps.curentwheather.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ps.curentwheather.Commen;
import com.example.ps.curentwheather.Model.Weather;
import com.example.ps.curentwheather.R;

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

        Glide.with(context).load(Commen.getIconDay(weather.getIcon()))
                .apply(new RequestOptions().override(72,72)).into(tempIv);

        timeTv.setText(weather.getTime()+":00");

        weatherTv.setText(Math.round(weather.getWeatherTemprature())+" C°");

    }
}

}
