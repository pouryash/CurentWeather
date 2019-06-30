package com.example.ps.curentwheather.MVP_MoreDeatils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.ps.curentwheather.Model.Weather;

import java.util.List;

public interface MVP {


    interface RequiredViewOps{
        Context getAppContext1();
        Activity getActivityContext1();
        void showToast(Toast toast);
        void onWeatherResived(Weather weather);
        void onHourWeatherResived(List<Weather> weathers);
        void onDaysWeatherResived(List<Weather> weathers);
    }

    interface PrvidedPresenterOps{

        void onStart();
        void onGetLocation(double lat,double lon);
    }

    interface RequiredPresenterOps{
        Context getAppContext();
        Context getActivityContext();
        void onResiveWeather(Weather weather);
        void onErrorWeather(String message);
        void onResiveHourWeather(List<Weather> weathers);
        void onErrorHourWeather(String message);
        void onResiveDaysWeather(List<Weather> weathers);
        void onErrorDaysWeather(String message);

    }

    interface PrvidedModelHourOps{
        void getHourWeather(double lat,double lon);
        void getWeather(double lat,double lon);
        void getDaysWeather(double lat,double lon);
    }
}
