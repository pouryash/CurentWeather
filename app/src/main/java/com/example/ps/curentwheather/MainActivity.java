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
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.ps.curentwheather.MVP.MVP_Main.MVP;
import com.example.ps.curentwheather.MVP.MVP_Main.MainPresenter;
import com.example.ps.curentwheather.Model.Weather;
import com.gauravbhola.ripplepulsebackground.RipplePulseLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;


public class MainActivity extends RuntimePermissionsActivity implements MVP.RequiredViewOps,
        LocationListener {

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
    Location location;
    //    private FusedLocationProviderClient clint;
    FirebaseAnalytics firebaseAnalytics;
    private LocationManager locationManager;
    private Criteria criteria;
    private String bestProvider;
    private RipplePulseLayout mRipplePulseLayout ;
    int backClickCount;


    @Inject
    MVP.ProvidedPresenterOps mPresenter = new MainPresenter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);


        MainActivity.super.requestAppPermissions
                (new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_COARSE_LOCATION);


        initViews();
        if (Commen.isNetworkConnectes(this)) {
            mPresenter.getOfflineWeather();
            setupProgressDialog();
            AndroidService.getInstance().GpsEnabled(MainActivity.this,progress);
            getLocation();

        } else {
            Toast.makeText(this, "You're Not Connected To Internet!", Toast.LENGTH_LONG).show();
            mPresenter.getOfflineWeather();
        }

        mPresenter.onCreate();

        if (progress.isShowing()){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Snackbar.make(mainRoot,"Something wrong Restart Your Location Service And try Again!"
                            ,5000).
                            setAction("Exit", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            }).show();
                    progress.hide();
                }
            },12000);
        }



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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AndroidService.getInstance().GpsEnabled(this,progress);
        if (requestCode == 1) {

        getLocation();


        }
    }

    @Override
    public void onBackPressed() {
        backClickCount += 1;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                backClickCount = 0;
            }
        }, 3000);

        if (backClickCount > 1) {
            this.finish();
        } else {
            Toast.makeText(getApplicationContext(), "Press Back Again to Exit", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    protected void getLocation() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true));

        //You can still do this if you like, you might get lucky:
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null && location.getLatitude() != 0 && location.getLongitude() != 0) {
                Log.e("TAG", "GPS is on");

                this.location = location;
                mPresenter.onGetLocation(location.getLatitude(),location.getLongitude());
            }
//                Toast.makeText(MainActivity.this, "latitude:" + lat + " longitude:" + lon, Toast.LENGTH_SHORT).show();
            else {
                //This is what you need:
                locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);

            }
        }

    }




//    void getLocation(){
//        LocationManager manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            clint = LocationServices.getFusedLocationProviderClient(this);
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            clint.getLastLocation().addOnSuccessListener(MainActivity.this,
//                    new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            lat = location.getLatitude();
//                            lon = location.getLongitude();
//                            mPresenter.onGetLocation(lat, lon);
//                        }
//                    });
//        }
//    }


    private void initViews() {

        mRipplePulseLayout = findViewById(R.id.layout_ripplepulse);
        progress = new ProgressDialog(this);
        currentTime = Calendar.getInstance().getTime();
        mainDegree = findViewById(R.id.Main_degree);
        mainLocation = findViewById(R.id.Main_location);
        mainRoot = findViewById(R.id.Main_root);
        mainSun = findViewById(R.id.Main_sun);
        mainTime = findViewById(R.id.Main_time);

    }

    private void setupProgressDialog(){
        progress.setMessage("Updating...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.show();
    }


    private void setupViews() {

        mRipplePulseLayout.startRippleAnimation();
        mainRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MoreDetails.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                if (location!= null){
                    intent.putExtra("lat", location.getLatitude());
                    intent.putExtra("lon", location.getLongitude());
                }
                startActivity(intent);

            }
        });
        mainDegree.setText(Math.round(weather.getWeatherTemprature()) + "° C");
        mainLocation.setText(weather.getCityName() + " , " + weather.getCountry(weather));
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss a");
//        String currentDateandTime = sdf.format(new Date());
        Date date = new Date();
        if (date.getHours() > 19 || date.getHours() < 7) {
            mainRoot.setBackgroundResource(R.drawable.background_night);
            mainSun.setImageResource(R.drawable.ic_moon);
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorBlack));
            }
        } else if (date.getHours() < 16) {
            mainRoot.setBackgroundResource(R.drawable.background_day);
            mainSun.setImageResource(R.drawable.ic_sun);
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorWاhite));
            }
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else if (date.getHours() > 17 || date.getHours() < 20) {
            mainRoot.setBackgroundResource(R.drawable.background_afternon);
            mainSun.setImageResource(R.drawable.ic_half_sun);
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorStatusBarAfternon));
            }
        }

        mainTime.setText(weather.getTime() + "");
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
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
//        getLocation();
        if (Commen.isNetworkConnectes(this)){
            getLocation();
            mPresenter.PermissionsGranted(requestCode);
        }else {
            Toast.makeText(this, "You're Not Connected To Internet!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPermissionsDeny(int requestCode) {
        this.finish();
    }


    @Override
    public Context getAppContext1() {
        return this;
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
    public void onWeatherResived(Weather weather,Boolean isUpdated) {
        if (isUpdated){
            Toast.makeText(this,"Data Updated",Toast.LENGTH_SHORT).show();
            progress.hide();
        }
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "City");
        bundle.putString(FirebaseAnalytics.Param.CONTENT, weather.getCityName());
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
        this.weather = weather;

        setupViews();
    }

    @Override
    public void onLocationChanged(Location location) {

        locationManager.removeUpdates(this);

        this.location = location;
        mPresenter.onGetLocation(location.getLatitude(), location.getLongitude());
//        Toast.makeText(MainActivity.this, "latitude:" + lat + " longitude:" + lon, Toast.LENGTH_SHORT).show();

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

