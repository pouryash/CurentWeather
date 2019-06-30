package com.example.ps.curentwheather;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ps.curentwheather.Api.ApiService;
import com.example.ps.curentwheather.MVP_Main.MVP;
import com.example.ps.curentwheather.MVP_Main.MainPresenter;
import com.example.ps.curentwheather.Model.Weather;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

public class MainActivity extends RuntimePermissionsActivity implements MVP.RequiredViewOps {

    private static final int ACCESS_COARSE_LOCATION = 10;
    private ImageView mainSun;
    private TextView mainDegree;
    private TextView mainTime;
    private TextView mainLocation;
    private LinearLayout mainRoot;
    private Weather weather;
    Date currentTime;
    LocationManager mLocationManager;
    private ProgressDialog progress;
    double lat, lon;
    private FusedLocationProviderClient clint;


    @Inject
    MVP.ProvidedPresenterOps mPresenter = new MainPresenter(this, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.super.requestAppPermissions
                (new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_COARSE_LOCATION);


        initViews();
        AndroidService.getInstance().statusCheck(MainActivity.this);
        getLocation();
        mPresenter.onCreate();




//        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
//                    1, new android.location.LocationListener() {
//                        @Override
//                        public void onLocationChanged(Location location) {
//                             lat = location.getLatitude();
//                             lon = location.getLongitude();
//                             mPresenter.onGetLocation(lat,lon);
//                        }
//
//                        @Override
//                        public void onStatusChanged(String s, int i, Bundle bundle) {
//
//                        }
//
//                        @Override
//                        public void onProviderEnabled(String s) {
//
//                        }
//
//                        @Override
//                        public void onProviderDisabled(String s) {
//
//                        }
//                    });
//        }


    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    void getLocation(){
        LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            clint = LocationServices.getFusedLocationProviderClient(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            clint.getLastLocation().addOnSuccessListener(MainActivity.this,
                    new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                            mPresenter.onGetLocation(lat, lon);
                        }
                    });
        }
    }


    private void initViews() {

        progress = new ProgressDialog(this);
        progress.setMessage("Updating...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        currentTime = Calendar.getInstance().getTime();
        mainDegree = findViewById(R.id.Main_degree);
        mainLocation = findViewById(R.id.Main_location);
        mainRoot = findViewById(R.id.Main_root);
        mainSun = findViewById(R.id.Main_sun);
        mainTime = findViewById(R.id.Main_time);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupViews() {

        mainRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MoreDetails.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lon",lon);
                startActivity(intent);
            }
        });
        mainDegree.setText(Math.round(weather.getWeatherTemprature()) + "° C");
        mainLocation.setText(weather.getCityName() + " , " + weather.getCountry(weather));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss a");
        String currentDateandTime = sdf.format(new Date());
        Date date = new Date();
        if (date.getHours() > 19 || date.getHours() < 7) {
            mainRoot.setBackgroundResource(R.drawable.background_night);
            mainSun.setImageResource(R.drawable.ic_moon);
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorBlack));
        } else if (date.getHours() < 16) {
            mainRoot.setBackgroundResource(R.drawable.background_day);
            mainSun.setImageResource(R.drawable.ic_sun);
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorWاhite));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else if (date.getHours() > 17 || date.getHours() < 20) {
            mainRoot.setBackgroundResource(R.drawable.background_afternon);
            mainSun.setImageResource(R.drawable.ic_half_sun);
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorStatusBarAfternon));
        }
        mainTime.setText(currentDateandTime);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        mPresenter.PermissionsGranted(requestCode);
    }

    @Override
    public void onPermissionsDeny(int requestCode) {
        this.finish();
    }


    @Override
    public Context getAppContext1() {
        return getApplicationContext();
    }

    @Override
    public Activity getActivityContext1() {
        return MainActivity.this;
    }

    @Override
    public void showToast(Toast toast) {
        toast.show();
    }

    @Override
    public void onWeatherResived(Weather weather) {
        this.weather = weather;
        progress.dismiss();
        setupViews();
    }

}

