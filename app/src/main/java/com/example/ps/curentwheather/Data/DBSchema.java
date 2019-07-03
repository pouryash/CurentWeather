package com.example.ps.curentwheather.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBSchema extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Weather_DB";
    public static final String TABLE_WEATHER = "weathers";
    public static final String TABLE_HOURS_WEATHER = "hour_weathers";
    public static final String TABLE_DAYS_WEATHER = "day_weathers";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WEATHER_NAME = "weather_name";
    public static final String COLUMN_WEATHER_DESCRIPTION = "weather_description";
    public static final String COLUMN_HUMIDITY = "weather_humidity";
    public static final String COLUMN_WEATHER_TEMPRTURE = "weather_weather_temprture";
    public static final String COLUMN_MIN_TEMPRTURE = "weather_min_temprture";
    public static final String COLUMN_MAX_TEMPRTURE = "weather_max_temprture";
    public static final String COLUMN_PRESSURE = "weather_pressure";
    public static final String COLUMN_CITY_NAME = "weather_city_name";
    public static final String COLUMN_COUNTRY_NAME = "weather_country_name";
    public static final String COLUMN_DAY = "weather_day";
    public static final String COLUMN_TIME = "weather_time";
    public static final String COLUMN_ICON = "weather_icon";

    public static final String CREATE_TABLE_WEATHER = "CREATE TABLE " + TABLE_WEATHER + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_WEATHER_NAME + " TEXT," + COLUMN_WEATHER_DESCRIPTION + " TEXT," +
            COLUMN_HUMIDITY + " INTIGER," + COLUMN_WEATHER_TEMPRTURE + " DOUBLE," +
            COLUMN_MIN_TEMPRTURE + " DOUBLE," + COLUMN_MAX_TEMPRTURE + " DOUBLE," +
            COLUMN_PRESSURE + " TEXT," + COLUMN_CITY_NAME + " TEXT," +
            COLUMN_COUNTRY_NAME + " TEXT," + COLUMN_DAY + " TEXT," +
            COLUMN_TIME + " TEXT," + COLUMN_ICON + " TEXT " + ")";

    public static final String CREATE_TABLE_HOURS_WEATHER = "CREATE TABLE " + TABLE_HOURS_WEATHER + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_WEATHER_TEMPRTURE + " DOUBLE,"
            + COLUMN_TIME + " TEXT,"+ COLUMN_ICON + " TEXT " + ")";

    public static final String CREATE_TABLE_DAYS_WEATHER = "CREATE TABLE " + TABLE_DAYS_WEATHER + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_MIN_TEMPRTURE + " DOUBLE,"
            + COLUMN_MAX_TEMPRTURE + " DOUBLE," + COLUMN_DAY + " TEXT,"+
            COLUMN_ICON + " TEXT " + ")";

    public DBSchema(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_WEATHER);
        sqLiteDatabase.execSQL(CREATE_TABLE_HOURS_WEATHER);
        sqLiteDatabase.execSQL(CREATE_TABLE_DAYS_WEATHER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
