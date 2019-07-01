package com.example.ps.curentwheather.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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

public class DaysWeatherAdp extends RecyclerView.Adapter<DaysWeatherAdp.DaysWeatherVH> {


    private List<Weather> daysWeatherList;
    Context context;

    public DaysWeatherAdp(List<Weather> daysWeatherList, Context context) {
        this.daysWeatherList = daysWeatherList;
        this.context = context;
    }

    @NonNull
    @Override
    public DaysWeatherVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.adp_day_weather, viewGroup, false);
        return new DaysWeatherVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DaysWeatherVH daysWeatherVH, int i) {
        daysWeatherVH.bindDaysWeather(daysWeatherList.get(i));
    }

    @Override
    public int getItemCount() {
        return daysWeatherList.size();
    }


    class DaysWeatherVH extends RecyclerView.ViewHolder {
        ImageView tempIv;
        TextView weatherTv;
        TextView dayTv;

        public DaysWeatherVH(@NonNull View itemView) {
            super(itemView);
            tempIv = itemView.findViewById(R.id.adpday_tempIv);
            weatherTv = itemView.findViewById(R.id.adpday_WeatherTv);
            dayTv = itemView.findViewById(R.id.adpday_DayTv);
        }

        public void bindDaysWeather(Weather weather) {
            Glide.with(context).load(Commen.getIconDay(weather.getIcon()))
                    .apply(new RequestOptions().override(72,72)).into(tempIv);
            dayTv.setText(weather.getDay());
            String text = "<font color='white'>" + Math.round(weather.getMaxTemprature()) + "°"
                    + "</font><font color='#C3E3F1'>/" + Math.round(weather.getMinTemprature()) + "°" + "</font>";
            weatherTv.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

            //<--TODO: Math.round-->

        }
    }
}
