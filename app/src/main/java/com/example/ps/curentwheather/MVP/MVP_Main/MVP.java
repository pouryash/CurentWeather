package com.example.ps.curentwheather.MVP.MVP_Main;

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
    void onWeatherResived(Weather weather,Boolean isUpdated);

}

interface ProvidedPresenterOps {

    void setView(RequiredViewOps view);
    void onStart();
    void onStop();
    void onCreate();
    void onPause();
    void onGetLocation(double lat,double lon);
    void PermissionsGranted(int requestCode);
    void getOfflineWeather();
}

    interface RequiredPresenterOps {
        Context getAppContext();
        Context getActivityContext();
        void onResive(Weather weather,Boolean isUpdated);
        void onError(String message);

    }

    interface ProvidedModelOps {
        void insertWeather(double lat, double lon);
        List<Weather> selectWeathers();
        int updateWeather(Weather weather);
    }

}
