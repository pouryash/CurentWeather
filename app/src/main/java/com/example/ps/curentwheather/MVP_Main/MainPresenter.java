package com.example.ps.curentwheather.MVP_Main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.ps.curentwheather.Model.Weather;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.lang.ref.WeakReference;

public class MainPresenter implements MVP.ProvidedPresenterOps,
        MVP.RequiredPresenterOps{

    WeakReference<MVP.RequiredViewOps> mView;
    MVP.ProvidedModelOps mModel ;
    Context con;



    public MainPresenter(MVP.RequiredViewOps view, Context contex) {

        mView =new WeakReference<>(view);
        this.con = contex;
        mModel = new MainModel(this);
    }

    @Override
    public void setView(MVP.RequiredViewOps view) {
    }

    @Override
    public void onMainactivityClicked() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void PermissionsGranted(int requestCode) {

    }

    @Override
    public void onGetLocation(double lat, double lon) {
        mModel.getCurentWeather(lat,lon);
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
    public void onResive(Weather weather) {
        mView.get().onWeatherResived(weather);
    }

    @Override
    public void onError(String message) {
        mView.get().showToast(Toast.makeText(con,message,Toast.LENGTH_LONG));
    }
}