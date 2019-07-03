package com.example.ps.curentwheather.MVP.MVP_MoreDeatils;

import android.content.Context;

import com.android.volley.NetworkError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.example.ps.curentwheather.Api.ApiService;
import com.example.ps.curentwheather.Data.DAO;
import com.example.ps.curentwheather.Model.Weather;

import java.util.List;

public class MoreDetailModel implements MVP.PrvidedModelMoreDetailOps {

    MVP.RequiredPresenterOps mPresenter;
    ApiService apiService;
    DAO mDAO;

    public MoreDetailModel(MVP.RequiredPresenterOps mPresenter , Context context) {
        this.mPresenter = mPresenter;
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
                    mPresenter.onResiveWeather(selectWeathers().get(0));
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

                        mPresenter.onErrorWeather(error+"");
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
                    mPresenter.onResiveWeather(selectWeathers().get(0));
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

                        mPresenter.onErrorWeather(error+"");
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

    @Override
    public void insertHoursWeather(final double lat, final double lon) {
        apiService = new ApiService(mPresenter.getAppContext(), lat, lon);


        if (selectHoursWeathers().size() > 0){
            apiService.getHourWeather(new ApiService.onHourWeatherCallBack<Weather>() {
                @Override
                public void OnHourWeatherRecived(List<Weather> response) {
                    mPresenter.onResiveHourWeather(response);

                    updateHoursWeather(response);
                    mPresenter.onResiveHourWeather(selectHoursWeathers());
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

        }else {

            apiService.getHourWeather(new ApiService.onHourWeatherCallBack<Weather>() {
                @Override
                public void OnHourWeatherRecived(List<Weather> response) {

                    mDAO.inserHoursWeather(response);
                    mPresenter.onResiveHourWeather(selectHoursWeathers());
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
    }

    @Override
    public List<Weather> selectHoursWeathers() {
        return mDAO.SelectAllHoursWeather();
    }

    @Override
    public int updateHoursWeather(List<Weather> weathers) {
        return mDAO.updateHoursWeather(weathers);
    }

    @Override
    public void insertDaysWeather(double lat, double lon) {
        if (selectDaysdWeathers().size() > 0){

            apiService = new ApiService(mPresenter.getAppContext(), lat, lon);
            apiService.getDaysWeather(new ApiService.onDaysWeatherCallBack<Weather>() {
                @Override
                public void OnDaysWeatherRecived(List<Weather> response) {

                    updateDaysWeather(response);
                    mPresenter.onResiveDaysWeather(selectDaysdWeathers());
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

        }else {
            apiService = new ApiService(mPresenter.getAppContext(), lat, lon);
            apiService.getDaysWeather(new ApiService.onDaysWeatherCallBack<Weather>() {
                @Override
                public void OnDaysWeatherRecived(List<Weather> response) {

                    mDAO.inserDaysWeather(response);
                    mPresenter.onResiveDaysWeather(selectDaysdWeathers());
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

    @Override
    public List<Weather> selectDaysdWeathers() {
        return mDAO.SelectAllDaysWeather();
    }

    @Override
    public int updateDaysWeather(List<Weather> weathers) {
        return mDAO.updateDaysWeather(weathers);
    }
}
