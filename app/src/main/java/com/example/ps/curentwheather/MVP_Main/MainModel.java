package com.example.ps.curentwheather.MVP_Main;

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
        ApiService apiService = new ApiService(mPresenter.getAppContext(),lat,lon);
        apiService.getWeather(new ApiService.OnResultCallBack<Weather>() {
            @Override
            public void OnRecived(Weather weather) {
                mPresenter.onResive(weather);
            }

            @Override
            public void OnError(String message) {
                mPresenter.onError(message);
            }
        });
    }
}
