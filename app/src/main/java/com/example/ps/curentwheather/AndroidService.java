package com.example.ps.curentwheather;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;

import androidx.appcompat.app.AlertDialog;

public class AndroidService {

    public static final int LOCATION_SETTINGS_REQUEST = 1;
    private static AndroidService instance;


    private AndroidService(){

    }

    static {
        instance = new AndroidService();
    }

    public static AndroidService getInstance(){
        return instance;
    }

    public void statusCheck(Activity context){
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            buildAlertMessageNoGps(context);

        }
    }

    public void buildAlertMessageNoGps(final Activity context) {
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
                        context.finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


}
