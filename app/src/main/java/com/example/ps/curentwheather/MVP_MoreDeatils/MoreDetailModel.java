package com.example.ps.curentwheather.MVP_MoreDeatils;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.ps.curentwheather.Api.ApiService;
import com.example.ps.curentwheather.Model.Weather;

import java.util.List;

public class MoreDetailModel implements MVP.PrvidedModelHourOps {

    MVP.RequiredPresenterOps mPresenter;
    ApiService apiService;

    public MoreDetailModel(MVP.RequiredPresenterOps mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void getHourWeather(double lat, double lon) {
        apiService = new ApiService(mPresenter.getAppContext(), lat, lon);

        apiService.getHourWeather(new ApiService.onHourWeatherCallBack<Weather>() {
            @Override
            public void OnHourWeatherRecived(List<Weather> response) {
                mPresenter.onResiveHourWeather(response);
            }

            @Override
            public void OnHourWeatherError(VolleyError error) {

                if (error instanceof NetworkError) {
                    mPresenter.onErrorHourWeather("Cannot connect to Internet...Please check your connection and try again");
                    apiService.request.cancel();

                } else if (error instanceof TimeoutError) {
                    mPresenter.onErrorHourWeather("Connection TimeOut! Please check your internet connection and try again");
                    apiService.request.cancel();


                } else if (error instanceof ServerError) {
                    mPresenter.onErrorHourWeather("The server could not be found. Please try again after some time and try again");
                    apiService.request.cancel();

                } else {

                    mPresenter.onErrorHourWeather(error + "");
                }

            }
        });

    }

    @Override
    public void getWeather(double lat, double lon) {
        apiService = new ApiService(mPresenter.getAppContext(), lat, lon);
        apiService.getWeather(new ApiService.OnResultCallBack<Weather>() {
            @Override
            public void OnWeatherRecived(Weather weather) {
                mPresenter.onResiveWeather(weather);
            }

            @Override
            public void OnWeatherError(VolleyError error) {
                if (error instanceof NetworkError) {
                    mPresenter.onErrorWeather("Cannot connect to Internet...Please check your connection and try again");
                    apiService.request.cancel();

                } else if (error instanceof TimeoutError) {
                    mPresenter.onErrorWeather("Connection TimeOut! Please check your internet connection and try again");
                    apiService.request.cancel();


                } else if (error instanceof ServerError) {
                    mPresenter.onErrorWeather("The server could not be found. Please try again after some time and try again");
                    apiService.request.cancel();

                } else {

                    mPresenter.onErrorWeather(error + "");
                }
            }


        });

    }

    @Override
    public void getDaysWeather(double lat, double lon) {
        apiService = new ApiService(mPresenter.getAppContext(), lat, lon);
        apiService.getDaysWeather(new ApiService.onDaysWeatherCallBack<Weather>() {
            @Override
            public void OnDaysWeatherRecived(List<Weather> response) {
                mPresenter.onResiveDaysWeather(response);
            }

            @Override
            public void OnDaysWeatherError(VolleyError error) {
                if (error instanceof NetworkError) {
                    mPresenter.onErrorDaysWeather("Cannot connect to Internet...Please check your connection and try again");
                    apiService.request.cancel();

                } else if (error instanceof TimeoutError) {
                    mPresenter.onErrorDaysWeather("Connection TimeOut! Please check your internet connection and try again");
                    apiService.request.cancel();


                } else if (error instanceof ServerError) {
                    mPresenter.onErrorDaysWeather("The server could not be found. Please try again after some time and try again");
                    apiService.request.cancel();

                } else {

                    mPresenter.onErrorDaysWeather(error + "");
                }
            }
        });
    }
}
