package com.example.ps.curentwheather;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ps.curentwheather.Api.ApiService;
import com.example.ps.curentwheather.MVP_Main.MVP;
import com.example.ps.curentwheather.MVP_Main.MainPresenter;
import com.example.ps.curentwheather.Model.Weather;

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

    @Inject
    MVP.ProvidedPresenterOps mPresenter = new MainPresenter(this, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity.super.requestAppPermissions
                (new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        ACCESS_COARSE_LOCATION);

        initViews();

    }


    private void initViews() {
        currentTime = Calendar.getInstance().getTime();
        mainDegree = findViewById(R.id.Main_degree);
        mainLocation = findViewById(R.id.Main_location);
        mainRoot = findViewById(R.id.Main_root);
        mainSun = findViewById(R.id.Main_sun);
        mainTime = findViewById(R.id.Main_time);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupViews() {
        mainDegree.setText(Math.round(weather.getWeatherTemprature())+"° C");
        mainLocation.setText(weather.getCityName()+" , "+weather.getCountry(weather));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss a");
        String currentDateandTime = sdf.format(new Date());
        Date date=new Date();
        if (date.getHours()> 19 || date.getHours() < 7){
            mainRoot.setBackgroundResource(R.drawable.background_night);
            mainSun.setImageResource(R.drawable.ic_moon);
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorBlack));
        }else if (date.getHours()<12){
            mainRoot.setBackgroundResource(R.drawable.background_day);
            mainSun.setImageResource(R.drawable.ic_sun);
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorWاhite));
        }else if (date.getHours() > 16){
            mainRoot.setBackgroundResource(R.drawable.background_afternon);
            mainSun.setImageResource(R.drawable.ic_half_sun);
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorStatusBarAfternon));
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
    public Context getActivityContext1() {
        return MainActivity.this;
    }

    @Override
    public void showToast(Toast toast) {
        toast.show();
    }

    @Override
    public void onWeatherResived(Weather weather) {
        this.weather=weather;
        setupViews();
    }
}

