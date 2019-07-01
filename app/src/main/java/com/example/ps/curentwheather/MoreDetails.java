package com.example.ps.curentwheather;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ps.curentwheather.Adapter.DaysWeatherAdp;
import com.example.ps.curentwheather.Adapter.HourWeatherAdp;
import com.example.ps.curentwheather.MVP_MoreDeatils.MVP;
import com.example.ps.curentwheather.MVP_MoreDeatils.MoreDeatailPresenter;
import com.example.ps.curentwheather.Model.Weather;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class MoreDetails extends AppCompatActivity implements MVP.RequiredViewOps,
        SwipeRefreshLayout.OnRefreshListener {


    ImageView weatherIv;
    TextView cityTv;
    TextView min_MaxTv;
    TextView tempTv;
    TextView humidityTv;
    TextView pressureTv;
    TextView progressHumidityTv;
    ProgressBar humidityProgressBar;
    LinearLayout root;
    Toolbar toolbar;
    RecyclerView hourRv;
    RecyclerView daysRv;
    HourWeatherAdp hourWeatherAdp;
    DaysWeatherAdp daysWeatherAdp;
    List<Weather> weatherList = new ArrayList<>();
    List<Weather> daysweatherList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    Weather weather;
    double lat, lon;
    private FusedLocationProviderClient clint;

    @Inject
    MVP.PrvidedPresenterOps mPresenter = new MoreDeatailPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);


        initView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    private void initView() {

        root = findViewById(R.id.moreDetail_Root);
        swipeRefreshLayout = findViewById(R.id.more_detail_swipTORefresh);
        humidityProgressBar = findViewById(R.id.circularProgressBar2);
        progressHumidityTv = findViewById(R.id.more_detail_progress);
        humidityTv = findViewById(R.id.more_detail_humidity);
        pressureTv = findViewById(R.id.more_detail_pressure);
        toolbar = (Toolbar) findViewById(R.id.moreDetail_toolbar);
        hourRv = findViewById(R.id.moreDetail_HourRv);
        daysRv = findViewById(R.id.moreDetail_DayRv);
        weatherIv = findViewById(R.id.moreDetail_WeatherIv);
        cityTv = findViewById(R.id.moreDetail_CityTv);
        min_MaxTv = findViewById(R.id.moreDitail_Min_MaxTv);
        tempTv = findViewById(R.id.moreDetail_TempTv);
        lat = getIntent().getDoubleExtra("lat", 0);
        lon = getIntent().getDoubleExtra("lon", 0);
        mPresenter.onGetLocation(lat, lon);


    }

//    void getLocation(){
//        LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            clint = LocationServices.getFusedLocationProviderClient(this);
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            clint.getLastLocation().addOnSuccessListener(MoreDetails.this,
//                    new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            lat = location.getLatitude();
//                            lon = location.getLongitude();
//                            mPresenter.onGetLocation(lat,lon);
//                        }
//                    });
//        }
//    }


    private void setupView() {
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("Main Page");
//        }
//        toolbar.setSubtitle("Test Subtitle");

        toolbar.inflateMenu(R.menu.menu);
        tempTv.setText(Math.round(weather.getWeatherTemprature()) + "° C");
        min_MaxTv.setText(Math.round(weather.getMaxTemprature()) + "°/"
                + Math.round(weather.getMinTemprature()) + "°");
        cityTv.setText(weather.getCityName());

        Date date = Commen.getDate();
        if (date.getHours() > 19 || date.getHours() < 7){

            root.setBackgroundResource(R.drawable.background_more_night);
            int image =Commen.getIconNight(weather.getIcon());
            Glide.with(getAppContext1()).load(image).
                    apply(new RequestOptions().override(450,450)).into(weatherIv);

        }else if (date.getHours() < 17 || date.getHours() < 20){

            root.setBackgroundResource(R.drawable.background_more_day);
            int image =Commen.getIconDay(weather.getIcon());
            Glide.with(getAppContext1()).load(image).
                    apply(new RequestOptions().override(450,450)).into(weatherIv);
        }
        humidityProgressBar.setProgress(weather.getHimidity());
        progressHumidityTv.setText(weather.getHimidity() + "%");
        pressureTv.setText("Pressure : " + weather.getPressure() + " hpa");
        humidityTv.setText("Humidity : " + weather.getHimidity() + "%");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public Context getAppContext1() {
        return this.getApplicationContext();
    }

    @Override
    public Activity getActivityContext1() {
        return this.getActivityContext1();
    }

    @Override
    public void showToast(Toast toast) {
        toast.show();
    }

    @Override
    public void onWeatherResived(Weather weather) {
        this.weather = weather;

        setupView();
    }

    @Override
    public void onHourWeatherResived(List<Weather> weathers) {
        weatherList = weathers;
        hourRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        hourWeatherAdp = new HourWeatherAdp(weatherList, this);
        hourRv.setAdapter(hourWeatherAdp);
    }

    @Override
    public void onDaysWeatherResived(List<Weather> weathers) {
        daysweatherList = weathers;
        daysRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        daysWeatherAdp = new DaysWeatherAdp(daysweatherList, this);
        daysRv.setAdapter(daysWeatherAdp);


    }

    @Override
    public void onRefresh() {
        mPresenter.onGetLocation(lat, lon);
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(MoreDetails.this, "Data Is Now updated", Toast.LENGTH_SHORT).show();
    }
}
