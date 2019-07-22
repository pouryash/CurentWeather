package com.example.ps.curentwheather.MVP.MVP_MoreDeatils;

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
        void onHourWeatherResived(List<Weather> weathers,Boolean isUpdated);
        void onDaysWeatherResived(List<Weather> weathers);
    }

    interface PrvidedPresenterOps{

        void onStart();
        void onGetLocation(double lat,double lon);
        void getOfflineWeather();
    }

    interface RequiredPresenterOps{
        Context getAppContext();
        Context getActivityContext();
        void onResiveWeather(Weather weather);
        void onErrorWeather(String message);
        void onResiveHourWeather(List<Weather> weathers ,Boolean isUpdated);
        void onErrorHourWeather(String message);
        void onResiveDaysWeather(List<Weather> weathers);
        void onErrorDaysWeather(String message);

    }

    interface PrvidedModelMoreDetailOps {
        void insertWeather(double lat, double lon);
        List<Weather> selectWeathers();
        int updateWeather(Weather weather);
        void insertHoursWeather(double lat, double lon);
        List<Weather> selectHoursWeathers();
        int updateHoursWeather(List<Weather> weathers);
        void insertDaysWeather(double lat, double lon);
        List<Weather> selectDaysdWeathers();
        int updateDaysWeather(List<Weather> weathers);
    }
}
