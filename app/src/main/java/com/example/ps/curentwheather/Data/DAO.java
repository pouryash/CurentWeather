package com.example.ps.curentwheather.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ps.curentwheather.Commen;
import com.example.ps.curentwheather.Model.Weather;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAO {

    private DBSchema mHelper;
    public Context context;
    public static final String SORT_ORDER_DEFULT = DBSchema.COLUMN_ID + " DESC";

    public DAO(Context context) {
        this.mHelper = new DBSchema(context);
        this.context = context;
    }


    private SQLiteDatabase getReadDB() {
        return mHelper.getReadableDatabase();
    }

    private SQLiteDatabase getWriteDB() {
        return mHelper.getWritableDatabase();
    }

    public void inserWeather(Weather weather){
//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(mHelper.COLUMN_WEATHER_NAME,weather.getWeatherName());
        value.put(mHelper.COLUMN_WEATHER_DESCRIPTION,weather.getWeatherDescription());
        value.put(mHelper.COLUMN_HUMIDITY,weather.getHumidity());
        value.put(mHelper.COLUMN_WEATHER_TEMPRTURE,weather.getWeatherTemprature());
        value.put(mHelper.COLUMN_MIN_TEMPRTURE,weather.getMinTemprature());
        value.put(mHelper.COLUMN_MAX_TEMPRTURE,weather.getMaxTemprature());
        value.put(mHelper.COLUMN_CITY_NAME,weather.getCityName());
        value.put(mHelper.COLUMN_PRESSURE,weather.getPressure());
        value.put(mHelper.COLUMN_COUNTRY_NAME,weather.getCountry());
        value.put(mHelper.COLUMN_TIME,Commen.getDate());
        value.put(mHelper.COLUMN_ICON,weather.getIcon());
        value.put(mHelper.COLUMN_DAY,weather.getDay());

        long id = getWriteDB().insert(mHelper.TABLE_NAME,null,value);
        getReadDB().close();
    }

    public List<Weather> SelectAllWeather(){

        List<Weather> list = new ArrayList<>();

        String selectQuery = "SELECT * FROm " + mHelper.TABLE_NAME + " ORDER BY "
                + SORT_ORDER_DEFULT;

//        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getWriteDB().rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){

            do {
                Weather weather = new Weather();
                weather.setId(cursor.getInt(cursor.getColumnIndex(mHelper.COLUMN_ID)));
                weather.setWeatherName(cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_WEATHER_NAME)));
                weather.setWeatherDescription(cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_WEATHER_DESCRIPTION)));
                weather.setHumidity(cursor.getInt(cursor.getColumnIndex(mHelper.COLUMN_HUMIDITY)));
                weather.setWeatherTemprature(cursor.getDouble(cursor.getColumnIndex(mHelper.COLUMN_WEATHER_TEMPRTURE)));
                weather.setMinTemprature(cursor.getDouble(cursor.getColumnIndex(mHelper.COLUMN_MIN_TEMPRTURE)));
                weather.setMaxTemprature(cursor.getDouble(cursor.getColumnIndex(mHelper.COLUMN_MAX_TEMPRTURE)));
                weather.setPressure(cursor.getInt(cursor.getColumnIndex(mHelper.COLUMN_PRESSURE)));
                weather.setCityName(cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_CITY_NAME)));
                weather.setCountry(cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_COUNTRY_NAME)));
                weather.setTime((cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_TIME))));
                weather.setDay(cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_DAY)));
                weather.setIcon(cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_ICON)));

                list.add(weather);
            } while (cursor.moveToNext());

        }
        getWriteDB().close();

        return list;
    }

    public int updateWeather(Weather weather) {

        ContentValues values = new ContentValues();
        values.put(mHelper.COLUMN_WEATHER_NAME,weather.getWeatherName());
        values.put(mHelper.COLUMN_WEATHER_DESCRIPTION,weather.getWeatherDescription());
        values.put(mHelper.COLUMN_HUMIDITY,weather.getHumidity());
        values.put(mHelper.COLUMN_WEATHER_TEMPRTURE,weather.getWeatherTemprature());
        values.put(mHelper.COLUMN_MIN_TEMPRTURE,weather.getMinTemprature());
        values.put(mHelper.COLUMN_MAX_TEMPRTURE,weather.getMaxTemprature());
        values.put(mHelper.COLUMN_CITY_NAME,weather.getCityName());
        values.put(mHelper.COLUMN_PRESSURE,weather.getPressure());
        values.put(mHelper.COLUMN_COUNTRY_NAME,weather.getCountry());
        values.put(mHelper.COLUMN_TIME,Commen.getDate());
        values.put(mHelper.COLUMN_ICON,weather.getIcon());
        values.put(mHelper.COLUMN_DAY,weather.getDay());

        // updating row

        return getWriteDB().update(mHelper.TABLE_NAME, values, mHelper.COLUMN_ID + " = ?",
                new String[]{"1"});
    }

    Weather getWeather(long id){

        Cursor cursor = getReadDB().query(mHelper.TABLE_NAME,
                new String[]{mHelper.COLUMN_ID, mHelper.COLUMN_WEATHER_NAME, mHelper.COLUMN_CITY_NAME},
                mHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        int aa=cursor.getCount();

        // prepare note object
        Weather note = new Weather();
        note.setCityName(cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_CITY_NAME)));

        // close the db connection
        cursor.close();
        return note;
    }
}
