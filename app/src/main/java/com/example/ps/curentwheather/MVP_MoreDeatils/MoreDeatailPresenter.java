package com.example.ps.curentwheather.MVP_MoreDeatils;

import android.content.Context;
import android.widget.Toast;
import com.example.ps.curentwheather.AndroidService;
import com.example.ps.curentwheather.MVP_Main.MainModel;
import com.example.ps.curentwheather.Model.Weather;
import java.lang.ref.WeakReference;
import java.util.List;

public class MoreDeatailPresenter implements MVP.PrvidedPresenterOps,
MVP.RequiredPresenterOps{

    WeakReference<MVP.RequiredViewOps> mView;
    MVP.PrvidedModelHourOps mModelHour;

    public MoreDeatailPresenter(MVP.RequiredViewOps view) {
        mView =new WeakReference<>(view);
        mModelHour = new MoreDetailModel(this);
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onGetLocation(double lat, double lon) {
        mModelHour.getHourWeather(lat,lon);
        mModelHour.getWeather(lat,lon);
        mModelHour.getDaysWeather(lat,lon);
        //<--TODO -->
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
    public void onResiveWeather(Weather weather) {
        mView.get().onWeatherResived(weather);
    }

    @Override
    public void onErrorWeather(String message) {
        mView.get().showToast(Toast.makeText(mView.get().getAppContext1(),message,Toast.LENGTH_LONG));
    }

    @Override
    public void onResiveHourWeather(List<Weather> weathers) {
        mView.get().onHourWeatherResived(weathers);
    }

    @Override
    public void onErrorHourWeather(String message) {
        mView.get().showToast(Toast.makeText(mView.get().getAppContext1(),message,Toast.LENGTH_LONG));
    }

    @Override
    public void onResiveDaysWeather(List<Weather> weathers) {
        mView.get().onDaysWeatherResived(weathers);
    }

    @Override
    public void onErrorDaysWeather(String message) {

        mView.get().showToast(Toast.makeText(mView.get().getAppContext1(),message,Toast.LENGTH_LONG));
    }
}
