package com.example.ps.curentwheather.MVP_Main;

import android.content.Context;
import android.widget.Toast;

import com.example.ps.curentwheather.Model.Weather;

import java.util.List;

public interface MVP {



interface RequiredViewOps{

    Context getAppContext1();
    Context getActivityContext1();
    void showToast(Toast toast);
    void onWeatherResived(Weather weather);

}

interface ProvidedPresenterOps {

    void setView(RequiredViewOps view);
    void onMainactivityClicked();
    void onStart();
    void onStop();
    void PermissionsGranted(int requestCode);
    void onGetLocation(double lat,double lon);

}

    interface RequiredPresenterOps {
        Context getAppContext();
        Context getActivityContext();
        void onResive(Weather weather);
        void onError(String message);

    }

    interface ProvidedModelOps {
        void getCurentWeather(double lat,double lon);
    }

}
