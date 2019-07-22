package com.example.ps.curentwheather;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;

import androidx.appcompat.app.AlertDialog;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class AndroidService {

    public static final int LOCATION_SETTINGS_REQUEST = 1;
    private static AndroidService instance;
    private LocationManager locationManager;
    private Criteria criteria;
    private String bestProvider;


    private AndroidService(){

    }

    static {
        instance = new AndroidService();
    }

    public static AndroidService getInstance(){
        return instance;
    }

    public void GpsEnabled(Activity context , SwipeRefreshLayout swipeRefreshLayout){
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            GpsEnabledAlertMessage(context,swipeRefreshLayout);

        }
    }

    public void GpsDisabled(Activity context){
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            GpsDisabledAlertMessage(context);

        }
    }

    public void GpsDisabledAlertMessage(final Activity context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you Want Restart Your Location?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivityForResult(intent,LOCATION_SETTINGS_REQUEST);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        context.finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    public void GpsEnabledAlertMessage(final Activity context , final SwipeRefreshLayout swipeRefreshLayout) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivityForResult(intent,LOCATION_SETTINGS_REQUEST);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        if (swipeRefreshLayout != null){
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    public void GpsEnabled(Activity context , ProgressDialog progressDialog){
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            GpsEnabledAlertMessage(context,progressDialog);

        }
    }

    public void GpsEnabledAlertMessage(final Activity context , final ProgressDialog progressDialog) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivityForResult(intent,LOCATION_SETTINGS_REQUEST);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        if (progressDialog != null){
                            progressDialog.dismiss();
                        }
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

//    public Location getLocation(Context context, LocationListener locationListener) {
//        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//        criteria = new Criteria();
//        bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
//
//        //You can still do this if you like, you might get lucky:
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            Location location = locationManager.getLastKnownLocation(bestProvider);
//            if (location != null) {
//                Log.e("TAG", "GPS is on");
//                return location;
//            }
////                Toast.makeText(MainActivity.this, "latitude:" + lat + " longitude:" + lon, Toast.LENGTH_SHORT).show();
//            else {
//                //This is what you need:
//                locationManager.requestLocationUpdates(bestProvider, 1000, 0, locationListener);
//            }
//        }
//        return null;
//
//    }



}
