package com.example.ps.curentwheather.Api;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ps.curentwheather.Model.Weather;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ApiService {

    private static final String BASE_URL_MAIN = "https://openweathermap.org/data/2.5/weather?";
    private static final String BASE_URL_HOUR = "https://openweathermap.org/data/2.5/forecast/hourly?";
    private static final String TAG = "APIServise";
    private static final String APPID = "b6907d289e10d714a6e88b30761fae22";
    public Request request;
    private Context context;
    private double lat;
    private double lon;


    public ApiService(Context context , double lat , double lon) {
        this.context = context;
        this.lat=lat;
        this.lon=lon;
    }

    public void getWeather(final OnResultCallBack<Weather> onResultCallBack) {


        final JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                BASE_URL_MAIN+"lat="+lat+"&lon="+lon+"&appid="+APPID,
                null, new Response.Listener<JSONObject>() {
            Weather weather = new Weather();
            @Override
            public void onResponse(JSONObject response) {


                try {
                        JSONObject mainWeather = response.getJSONObject("main");
                        JSONObject windWeather = response.getJSONObject("wind");
                        JSONObject sysWeather = response.getJSONObject("sys");
                        JSONArray arWeather = response.getJSONArray("weather");
                        JSONObject obWeather = arWeather.getJSONObject(0);
                        weather.setHimidity(mainWeather.getInt("humidity"));
                        weather.setMaxTemprature(mainWeather.getDouble("temp_max"));
                        weather.setMinTemprature(mainWeather.getDouble("temp_min"));
                        weather.setWeatherTemprature(mainWeather.getDouble("temp"));
                        weather.setPressure(mainWeather.getInt("pressure"));
                        weather.setWindDegree(windWeather.getDouble("deg"));
                        weather.setWindSpeed(windWeather.getDouble("speed"));
                        weather.setCountry(sysWeather.getString("country"));
                        weather.setCityName(response.getString("name"));
                        weather.setWeatherDescription(obWeather.getString("description"));
                        weather.setWeatherName(obWeather.getString("main"));

                } catch (JSONException e) {
                    Log.e(TAG , "Response:"+ e.toString());
                }

                onResultCallBack.OnWeatherRecived(weather);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onResultCallBack.OnWeatherError(error);
                error.printStackTrace();
            }
        });
        request = Volley.newRequestQueue(context).add(jsonArrayRequest);

    }

    public void getHourWeather(final onHourWeatherCallBack<Weather> onResultCallBack) {


        final JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.GET,
                BASE_URL_HOUR+"lat="+lat+"&lon="+lon+"&appid="+APPID,
                null, new Response.Listener<JSONObject>() {
            List<Weather> hourWeatherList = new ArrayList<>();
            @Override
            public void onResponse(JSONObject response) {

                JSONArray hourWeathers = null;
                try {
                    hourWeathers = response.getJSONArray("list");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Weather weather = null;
                for (int i = 0; i < hourWeathers.length(); i++) {
                    weather = new Weather();

                    JSONObject hourWeahter = null;
                    try {
                        hourWeahter = hourWeathers.getJSONObject(i);
                        weather.setWeatherTemprature(hourWeahter.getJSONObject("main").getDouble("temp"));
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = format.parse(hourWeahter.getString("dt_txt"));
                            System.out.println(date);
                            weather.setTime(date);
                            hourWeatherList.add(weather);

                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }

                }
                onResultCallBack.OnHourWeatherRecived(hourWeatherList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onResultCallBack.OnHourWeatherError(error);
                error.printStackTrace();
            }
        });
        request = Volley.newRequestQueue(context).add(jsonArrayRequest);

    }


    public interface OnResultCallBack<T>{
        void OnWeatherRecived(T t);
        void OnWeatherError(VolleyError error);

    }
    public interface onHourWeatherCallBack<T>{
        void OnHourWeatherRecived(List<T> response);
        void OnHourWeatherError(VolleyError error);
    }

}
