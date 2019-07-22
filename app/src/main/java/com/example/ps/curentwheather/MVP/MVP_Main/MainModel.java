package com.example.ps.curentwheather.MVP.MVP_Main;

import android.content.Context;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.ps.curentwheather.Api.ApiService;
import com.example.ps.curentwheather.Data.DAO;
import com.example.ps.curentwheather.Model.Weather;

import java.util.List;

public class MainModel implements MVP.ProvidedModelOps{

private MVP.RequiredPresenterOps mPresenter;
DAO mDAO;


    public MainModel(MVP.RequiredPresenterOps presenter , Context context) {
        this.mPresenter = presenter;
        mDAO = new DAO(context);
    }

    @Override
    public void insertWeather(double lat, double lon) {
        if (selectWeathers().size() == 1){
            final ApiService apiService = new ApiService(mPresenter.getAppContext(),lat,lon);
            apiService.getWeather(new ApiService.OnResultCallBack<Weather>() {
                @Override
                public void OnWeatherRecived(Weather weather) {
//                    mPresenter.onResive(weather);
                    updateWeather(weather);
                    List<Weather> list =selectWeathers();
                    mPresenter.onResive(selectWeathers().get(0),true);
                }

                @Override
                public void OnWeatherError(VolleyError error) {
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

        }else {
            final ApiService apiService = new ApiService(mPresenter.getAppContext(),lat,lon);
            apiService.getWeather(new ApiService.OnResultCallBack<Weather>() {
                @Override
                public void OnWeatherRecived(Weather weather) {
//                    mPresenter.onResive(weather);
                    mDAO.inserWeather(weather);
                    mPresenter.onResive(selectWeathers().get(0),true);
                }

                @Override
                public void OnWeatherError(VolleyError error) {
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

    @Override
    public List<Weather> selectWeathers() {
        return mDAO.SelectAllWeather();
    }

    @Override
    public int updateWeather(Weather weather) {
        return mDAO.updateWeather(weather);
    }


}
