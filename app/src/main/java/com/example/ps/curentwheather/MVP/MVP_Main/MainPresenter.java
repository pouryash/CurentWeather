package com.example.ps.curentwheather.MVP.MVP_Main;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import com.example.ps.curentwheather.Model.Weather;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainPresenter implements MVP.ProvidedPresenterOps,
        MVP.RequiredPresenterOps {

    WeakReference<MVP.RequiredViewOps> mView;
    MVP.ProvidedModelOps mModel ;
    Context con;
    Location location;
    LocationManager locationManager;



    public MainPresenter(MVP.RequiredViewOps view) {

        mView =new WeakReference<>(view);
        mModel = new MainModel(this,getAppContext());
    }

    @Override
    public void setView(MVP.RequiredViewOps view) {
    }


    @Override
    public void onStart() {
    }
    @Override
    public void onStop() {

    }

    @Override
    public void onCreate() {

    }
    @Override
    public void onPause() {
    }

    @Override
    public void onGetLocation(double lat, double lon) {
        mModel.insertWeather(lat,lon);
    }

    @Override
    public void PermissionsGranted(int requestCode) {

    }

    @Override
    public void getOfflineWeather() {
        List<Weather> list = mModel.selectWeathers();

        if (list.size() == 0){
            mView.get().showToast(Toast.makeText(getAppContext(),"Please Cheack Your Internet Connection And Try Again!",Toast.LENGTH_LONG));
//            mView.get().getActivityContext1().finish();
        }else {
            onResive(mModel.selectWeathers().get(0),false);
        }
    }


    @Override
    public Context getAppContext() {
        return mView.get().getAppContext1();
    }

    @Override
    public Context getActivityContext() {
        return mView.get().getActivityContext1();
    }

    @Override
    public void onResive(Weather weather,Boolean isUpdated) {
        mView.get().onWeatherResived(weather,isUpdated);
    }

    @Override
    public void onError(String message) {
        mView.get().showToast(Toast.makeText(getAppContext(),message,Toast.LENGTH_LONG));
        getOfflineWeather();
    }

}