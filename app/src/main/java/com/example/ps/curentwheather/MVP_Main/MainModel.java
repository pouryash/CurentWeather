package com.example.ps.curentwheather.MVP_Main;

import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.ps.curentwheather.Api.ApiService;
import com.example.ps.curentwheather.Model.Weather;
import java.util.List;

public class MainModel implements MVP.ProvidedModelOps {

private MVP.RequiredPresenterOps mPresenter;


    public MainModel(MVP.RequiredPresenterOps presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void getCurentWeather(double lat, double lon) {
        final ApiService apiService = new ApiService(mPresenter.getAppContext(),lat,lon);
        apiService.getWeather(new ApiService.OnResultCallBack<Weather>() {
            @Override
            public void OnRecived(Weather weather) {
                mPresenter.onResive(weather);
            }

            @Override
            public void OnError(VolleyError error) {

                if (error instanceof NetworkError) {
                    mPresenter.onError("Cannot connect to Internet...Please check your connection and try again");
                    apiService.request.cancel();

                } else if (error instanceof TimeoutError) {
                    mPresenter.onError("Connection TimeOut! Please check your internet connection and try again");
                    apiService.request.cancel();


                } else if (error instanceof ServerError) {
                    mPresenter.onError("The server could not be found. Please try again after some time and try again");
                    apiService.request.cancel();

                } else {

                    mPresenter.onError(error+"");
                }
            }
        });
    }
}
