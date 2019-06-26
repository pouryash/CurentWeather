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



public class ApiService {

    private static final String BASE_URL = "https://openweathermap.org/data/2.5/weather?";
    private static final String TAG = "APIServise";
    private static final String APPID = "b6907d289e10d714a6e88b30761fae22";
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
                BASE_URL+"lat="+lat+"&lon="+lon+"&appid="+APPID,
                null, new Response.Listener<JSONObject>() {
            Weather weather = new Weather();
            @Override
            public void onResponse(JSONObject response) {

//                for (int i = 0; i < response.length(); i++) {

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

                onResultCallBack.OnRecived(weather);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onResultCallBack.OnError(error+"");
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(context).add(jsonArrayRequest);

    }

    public interface OnResultCallBack<T>{
        void OnRecived(T t);
        void OnError(String message);
    }
}
