package com.example.ps.curentwheather;

import android.text.format.DateFormat;

import com.example.ps.curentwheather.Api.ApiService;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class DaysWeahterTest {


    @Test
    public void getDays() {


//    assertEquals(29,show());
    assertEquals(Calendar.JUNE,getDay());
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        assertEquals("Jun",getCurrentMonth());
    }

    public int getDay() {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.MONTH);

        return day;
    }

    public int show(){

        Calendar calendar = Calendar.getInstance();
        Date date =calendar.getTime();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);

        return month;
    }

    public String getCurrentMonth(){

        String monthArray[] = {"Jan","Feb","Mar", "Apr","May","Jun", "Jul",
        "Aug","Oct","Sep","Nov","Dec"};
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);

        return monthArray[month];

    }


}
