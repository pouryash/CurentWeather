package com.example.ps.curentwheather;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Commen {


    public static int getIconDay(String icon){
        int image = 0;

        switch (icon){
            case "01d":
            case "01n":
                image= R.drawable.ic_sunny;
                break;
            case "02d":
            case "02n":
                image= R.drawable.ic_few_clouds;
            break;
            case "03d":
            case "03n":
                image= R.drawable.ic_scattered_clouds;
            break;
            case "04d":
            case "04n":
                image= R.drawable.ic_broken_clouds_day;
            break;
            case "09d":
            case "09n":
                image= R.drawable.ic_shower_rain_day;
            break;
            case "10d":
            case "10n":
                image= R.drawable.ic_rain_day;
            break;
            case "11d":
            case "11n":
                image= R.drawable.ic_thunderstorm_day;
            break;
            case "13d":
            case "13n":
                image= R.drawable.ic_snow_day;
            break;
            case "50d":
            case "50n":
                image= R.drawable.ic_mist;
            break;

        }
        
        return image;
    }

    public static int getIconNight(String icon){
        int image = 0;

        switch (icon){
            case "01n":
            case "01d":
                image= R.drawable.ic_night_moon;
                break;
            case "03n":
            case "03d":
                image= R.drawable.ic_scattered_clouds;
                break;
            case "04n":
            case "04d":
                image= R.drawable.ic_broken_clouds_night;
                break;
            case "09n":
            case "09d":
                image= R.drawable.ic_shower_rain_night;
                break;
            case "10n":
            case "10d":
                image= R.drawable.ic_rain_night;
                break;
            case "11n":
            case "11d":
                image= R.drawable.ic_thunderstorm_night;
                break;
            case "13n":
            case "13d":
                image= R.drawable.ic_snow_night;
                break;
            case "50n":
            case "50d":
                image= R.drawable.ic_mist;
                break;
            default:
                image= R.drawable.ic_scattered_clouds;
                break;

        }

        return image;
    }


    public static Date getDate(){

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss a");
        String currentDateandTime = sdf.format(new Date());
        Date date = new Date();

        return date;
    }
}
