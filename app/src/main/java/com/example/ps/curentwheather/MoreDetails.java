package com.example.ps.curentwheather;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ps.curentwheather.Adapter.DaysWeatherAdp;
import com.example.ps.curentwheather.Adapter.HourWeatherAdp;
import com.example.ps.curentwheather.MVP.MVP_MoreDeatils.MVP;
import com.example.ps.curentwheather.MVP.MVP_MoreDeatils.MoreDeatailPresenter;
import com.example.ps.curentwheather.Model.Weather;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class MoreDetails extends AppCompatActivity implements MVP.RequiredViewOps,
        SwipeRefreshLayout.OnRefreshListener, LocationListener {


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
    ProgressDialog progress;
    //    FusedLocationProviderClient clint;
    Weather weather;
    Location location = new Location("");
    LocationManager locationManager;
    Criteria criteria;
    String bestProvider;

    @Inject
    MVP.PrvidedPresenterOps mPresenter = new MoreDeatailPresenter(this, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);


        initView();
        toolbar.setTitle("");
        if (Commen.isNetworkConnectes(this)) {
            mPresenter.getOfflineWeather();
            AndroidService.getInstance().GpsEnabled(this, swipeRefreshLayout);
            getLocation();

        } else {
            mPresenter.getOfflineWeather();
        }

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
//        this.location.setLatitude(getIntent().getDoubleExtra("lat", 0));
//        this.location.setLongitude(getIntent().getDoubleExtra("lon", 0));
    }


    void setupProgressdialog() {
        progress = new ProgressDialog(this);
        progress.setMessage("Updating...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCanceledOnTouchOutside(false);
        progress.show();
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

        Date date = new Date();
        if (date.getHours() > 19 || date.getHours() < 7) {

            root.setBackgroundResource(R.drawable.background_more_night);
            int image = Commen.getIconNight(weather.getIcon());
            Glide.with(getAppContext1()).load(image).
                    apply(new RequestOptions().override(450, 450)).into(weatherIv);

        } else if (date.getHours() < 17 || date.getHours() < 20) {

            root.setBackgroundResource(R.drawable.background_more_day);
            int image = Commen.getIconDay(weather.getIcon());
            Glide.with(getAppContext1()).load(image).
                    apply(new RequestOptions().override(450, 450)).into(weatherIv);
        }
        humidityProgressBar.setProgress(weather.getHumidity());
        progressHumidityTv.setText(weather.getHumidity() + "%");
        pressureTv.setText("Pressure : " + weather.getPressure() + " hpa");
        humidityTv.setText("Humidity : " + weather.getHumidity() + "%");

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
    public void onHourWeatherResived(List<Weather> weathers ,Boolean isUpdated) {
        weatherList = weathers;
        if (isUpdated) {
            progress.dismiss();
        }
        hourRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        hourWeatherAdp = new HourWeatherAdp(weatherList, this);
        hourRv.setAdapter(hourWeatherAdp);
    }

    @Override
    public void onDaysWeatherResived(List<Weather> weathers) {
        daysweatherList = weathers;
        daysRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        daysWeatherAdp = new DaysWeatherAdp(daysweatherList, this);
        daysRv.setAdapter(daysWeatherAdp);
        if (swipeRefreshLayout.isRefreshing()) {
            Toast.makeText(MoreDetails.this, "Data is Updated", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
//        progress.dismiss();


    }

    protected void getLocation() {
        double lat = getIntent().getDoubleExtra("lat", 0);
        double lon = getIntent().getDoubleExtra("lon", 0);
        if (lat != 0 && lon != 0) {
            location.setLongitude(getIntent().getDoubleExtra("lon", 0));
            location.setLatitude(getIntent().getDoubleExtra("lat", 0));
            mPresenter.onGetLocation(location.getLatitude(), location.getLongitude());


        } else {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Location location = locationManager.getLastKnownLocation(bestProvider);
                if (location != null && location.getLatitude() != 0 && location.getLongitude() != 0) {
                    Log.e("TAG", "GPS is on");
                    this.location = location;
                    mPresenter.onGetLocation(location.getLatitude(), location.getLongitude());
                }
//                Toast.makeText(MainActivity.this, "latitude:" + lat + " longitude:" + lon, Toast.LENGTH_SHORT).show();
                else {
                    //This is what you need:
                    locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
                }
            }
        }
        setupProgressdialog();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Exit_Menu:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        if (Commen.isNetworkConnectes(this)) {
            final LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (location == null || (location.getLatitude() == 0 && location.getLongitude() == 0)) {
                    getLocation();
                    mPresenter.onGetLocation(location.getLatitude(), location.getLongitude());
                } else {
                    mPresenter.onGetLocation(location.getLatitude(), location.getLongitude());
                }
            } else {
                AndroidService.getInstance().GpsEnabled(this, swipeRefreshLayout);
                mPresenter.onGetLocation(location.getLatitude(), location.getLongitude());
            }


        } else {
            Toast.makeText(MoreDetails.this, "Please Cheack Your Internet Connection!", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AndroidService.getInstance().GpsEnabled(this, swipeRefreshLayout);
        if (requestCode == 1) {

            getLocation();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        locationManager.removeUpdates(this);

        //open the map:
        this.location = location;
        mPresenter.onGetLocation(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
