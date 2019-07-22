package com.example.ps.curentwheather;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.example.ps.curentwheather.Data.DBSchema;
import com.example.ps.curentwheather.Model.Weather;

import org.junit.Before;
import org.junit.Test;
import org.junit.internal.builders.JUnit3Builder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "/src/main/AndroidManifest.xml")
public class TestModel {

    DBSchema mHelper;
    String SORT_ORDER_DEFULT;
    Context context;

    @Before
    public void setup() {
        context = RuntimeEnvironment.application;
        mHelper = new DBSchema(context);
        SORT_ORDER_DEFULT = mHelper.COLUMN_ID + " DESC";
    }


    private SQLiteDatabase getReadDB() {
        return mHelper.getReadableDatabase();
    }

    private SQLiteDatabase getWriteDB() {
        return mHelper.getWritableDatabase();
    }

    @Test
    public void inserWeather() {

        Weather weather = this.fakeData();

//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put(mHelper.COLUMN_WEATHER_NAME, weather.getWeatherName());
        value.put(mHelper.COLUMN_WEATHER_DESCRIPTION, weather.getWeatherDescription());
        value.put(mHelper.COLUMN_HUMIDITY, weather.getHumidity());
        value.put(mHelper.COLUMN_WEATHER_TEMPRTURE, weather.getWeatherTemprature());
        value.put(mHelper.COLUMN_MIN_TEMPRTURE, weather.getMinTemprature());
        value.put(mHelper.COLUMN_MAX_TEMPRTURE, weather.getMaxTemprature());
        value.put(mHelper.COLUMN_CITY_NAME, weather.getCityName());
        value.put(mHelper.COLUMN_PRESSURE, weather.getPressure());
        value.put(mHelper.COLUMN_COUNTRY_NAME, weather.getCountry());
        value.put(mHelper.COLUMN_TIME, Commen.getDate());
        value.put(mHelper.COLUMN_ICON, weather.getIcon());
        value.put(mHelper.COLUMN_DAY, weather.getDay());

        long id = getWriteDB().insert(mHelper.TABLE_WEATHER, null, value);
        assertTrue(id > -1);
        getReadDB().close();
    }

    @Test
    public void inserHoursWeather() {
        List<Weather> weathers = new ArrayList<>();
        weathers.add(fakeData());
        weathers.add(fakeData());
        weathers.add(fakeData());
        ContentValues value = new ContentValues();


        for (int i = 0; i < weathers.size(); i++) {

            value.put(mHelper.COLUMN_WEATHER_TEMPRTURE, weathers.get(i).getWeatherTemprature());
            value.put(mHelper.COLUMN_TIME, weathers.get(i).getTime());
            value.put(mHelper.COLUMN_ICON, weathers.get(i).getIcon());

            long id = getWriteDB().insert(mHelper.TABLE_HOURS_WEATHER, null, value);
            assertTrue(id > -1);
        }
        getReadDB().close();

    }

    @Test
    public void inserDaysWeather() {

        List<Weather> weathers = new ArrayList<>();
        weathers.add(fakeData());
        weathers.add(fakeData());
        weathers.add(fakeData());

        ContentValues value = new ContentValues();


        for (int i = 0; i < weathers.size(); i++) {

            value.put(mHelper.COLUMN_MIN_TEMPRTURE, weathers.get(i).getMinTemprature());
            value.put(mHelper.COLUMN_MAX_TEMPRTURE, weathers.get(i).getMaxTemprature());
            value.put(mHelper.COLUMN_DAY, weathers.get(i).getDay());
            value.put(mHelper.COLUMN_ICON, weathers.get(i).getIcon());

            long id = getWriteDB().insert(mHelper.TABLE_DAYS_WEATHER, null, value);
            assertTrue(id > -1);
        }
        getReadDB().close();
    }

    public List<Weather> SelectAllWeather() {

        List<Weather> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + mHelper.TABLE_WEATHER + " ORDER BY "
                + SORT_ORDER_DEFULT;

//        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = getWriteDB().rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

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

    @Test
    public void SelectAllHoursWeather() {

        AddAsyncTask addAsyncTask = mock(AddAsyncTask.class);
        Mockito.when(addAsyncTask.fakeData()).thenReturn(null);
        Weather weather1 = addAsyncTask.fakeData();
        //means verify that fakedata called atleast 1 time
        verify(addAsyncTask, atLeastOnce()).fakeData();

        inserHoursWeather();
        List<Weather> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + mHelper.TABLE_HOURS_WEATHER + " ORDER BY "
                + SORT_ORDER_DEFULT;

        Cursor cursor = getWriteDB().rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
                Weather weather = new Weather();
                weather.setId(cursor.getInt(cursor.getColumnIndex(mHelper.COLUMN_ID)));
                weather.setWeatherTemprature(cursor.getDouble(cursor.getColumnIndex(mHelper.COLUMN_WEATHER_TEMPRTURE)));
                weather.setTime((cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_TIME))));
                weather.setIcon(cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_ICON)));

                list.add(weather);
            } while (cursor.moveToNext());

        }
        getWriteDB().close();

        Collections.reverse(list);

        assertTrue(list.size() > 0);
    }

    public List<Weather> SelectAllDaysWeather() {

        List<Weather> list = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + mHelper.TABLE_DAYS_WEATHER + " ORDER BY "
                + SORT_ORDER_DEFULT;

        Cursor cursor = getWriteDB().rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
                Weather weather = new Weather();
                weather.setId(cursor.getInt(cursor.getColumnIndex(mHelper.COLUMN_ID)));
                weather.setMinTemprature(cursor.getDouble(cursor.getColumnIndex(mHelper.COLUMN_MIN_TEMPRTURE)));
                weather.setMaxTemprature((cursor.getDouble(cursor.getColumnIndex(mHelper.COLUMN_MAX_TEMPRTURE))));
                weather.setIcon(cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_ICON)));
                weather.setDay(cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_DAY)));

                list.add(weather);
            } while (cursor.moveToNext());

        }
        getWriteDB().close();
        Collections.reverse(list);
        return list;
    }

    @Test
    public void updateHoursWeather() {

        List<Weather> weathers = new ArrayList<>();
        weathers.add(fakeData());
        weathers.add(fakeData());
        weathers.add(fakeData());

        ContentValues values = new ContentValues();
        for (int i = 0; i < weathers.size(); i++) {

            values.put(mHelper.COLUMN_WEATHER_TEMPRTURE, weathers.get(i).getWeatherTemprature());
            values.put(mHelper.COLUMN_TIME, weathers.get(i).getTime());
            values.put(mHelper.COLUMN_ICON, weathers.get(i).getIcon());

            long id = getWriteDB().update(mHelper.TABLE_HOURS_WEATHER, values, mHelper.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(i)});

            assertTrue(id > -1);
        }

        // updating row
    }

    public int updateDaysWeather(List<Weather> weathers) {

        ContentValues values = new ContentValues();
        for (int i = 0; i < weathers.size(); i++) {


            values.put(mHelper.COLUMN_MIN_TEMPRTURE, weathers.get(i).getMinTemprature());
            values.put(mHelper.COLUMN_MAX_TEMPRTURE, weathers.get(i).getMaxTemprature());
            values.put(mHelper.COLUMN_ICON, weathers.get(i).getIcon());
            values.put(mHelper.COLUMN_DAY, weathers.get(i).getDay());

            getWriteDB().update(mHelper.TABLE_DAYS_WEATHER, values, mHelper.COLUMN_ID + " = ?",
                    new String[]{String.valueOf(i + 1)});
        }

        // updating row
        return 1;

    }


    public int updateWeather(Weather weather) {

        ContentValues values = new ContentValues();
        values.put(mHelper.COLUMN_WEATHER_NAME, weather.getWeatherName());
        values.put(mHelper.COLUMN_WEATHER_DESCRIPTION, weather.getWeatherDescription());
        values.put(mHelper.COLUMN_HUMIDITY, weather.getHumidity());
        values.put(mHelper.COLUMN_WEATHER_TEMPRTURE, weather.getWeatherTemprature());
        values.put(mHelper.COLUMN_MIN_TEMPRTURE, weather.getMinTemprature());
        values.put(mHelper.COLUMN_MAX_TEMPRTURE, weather.getMaxTemprature());
        values.put(mHelper.COLUMN_CITY_NAME, weather.getCityName());
        values.put(mHelper.COLUMN_PRESSURE, weather.getPressure());
        values.put(mHelper.COLUMN_COUNTRY_NAME, weather.getCountry());
        values.put(mHelper.COLUMN_TIME, Commen.getDate());
        values.put(mHelper.COLUMN_ICON, weather.getIcon());
        values.put(mHelper.COLUMN_DAY, weather.getDay());

        // updating row

        return getWriteDB().update(mHelper.TABLE_WEATHER, values, mHelper.COLUMN_ID + " = ?",
                new String[]{"1"});
    }

    Weather getWeather(long id) {

        Cursor cursor = getReadDB().query(mHelper.TABLE_WEATHER,
                new String[]{mHelper.COLUMN_ID, mHelper.COLUMN_WEATHER_NAME, mHelper.COLUMN_CITY_NAME},
                mHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        int aa = cursor.getCount();

        // prepare note object
        Weather note = new Weather();
        note.setCityName(cursor.getString(cursor.getColumnIndex(mHelper.COLUMN_CITY_NAME)));

        // close the db connection
        cursor.close();
        return note;
    }

    public Weather fakeData() {

        Weather weather = new Weather();
        weather.setCityName("sanandaj");
        weather.setWeatherName("sunny");
        weather.setWeatherDescription("sun");
        weather.setDay("sunday");
        weather.setMaxTemprature(39);
        weather.setMinTemprature(15);
        weather.setIcon("1d0");
        weather.setCountry("IR");
        weather.setPressure(10);
        weather.setHumidity(15);
        weather.setId(1);

        return weather;

    }

}
