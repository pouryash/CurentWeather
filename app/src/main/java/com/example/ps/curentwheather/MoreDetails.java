package com.example.ps.curentwheather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.android.volley.VolleyError;
import com.example.ps.curentwheather.Adapter.HourWeatherAdp;
import com.example.ps.curentwheather.Api.ApiService;
import com.example.ps.curentwheather.Model.Weather;

import java.util.ArrayList;
import java.util.List;

public class MoreDetails extends AppCompatActivity implements ApiService.onHourWeatherCallBack {


    Toolbar toolbar;
    RecyclerView hourRv;
    HourWeatherAdp hourWeatherAdp;
    List<Weather> weatherList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);

        ApiService apiService = new ApiService(this, 35, 139);
        apiService.getHourWeather(this);

        initView();
        setupView();
    }



    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.moreDetail_toolbar);
        hourRv = findViewById(R.id.moreDetail_HourRv);

    }
    private void setupView() {
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("Main Page");
//        }
//        toolbar.setSubtitle("Test Subtitle");

        toolbar.inflateMenu(R.menu.menu);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void OnHourWeatherRecived(List response) {
        weatherList = response;
        hourRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        hourWeatherAdp = new HourWeatherAdp(weatherList, this);
        hourRv.setAdapter(hourWeatherAdp);
    }

    @Override
    public void OnHourWeatherError(VolleyError error) {

    }
}
