package com.example.ps.curentwheather.Model;

import java.util.Date;

public class Weather {

    private int id;
    private String weatherName;
    private String weatherDescription;
    private double windSpeed;
    private double windDegree;
    private int humidity;
    private double weatherTemprature;
    private double minTemprature;
    private double maxTemprature;
    private int pressure;
    private String cityName;
    private String country;
    private String time;
    private String day;
    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {

        return this.icon;

    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCountry(Weather weather) {
        if (weather.country.endsWith("IR")){
            return "IRAN";
        }else {
            return country;
        }
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWeatherName() {
        return weatherName;
    }

    public void setWeatherName(String weatherName) {
        this.weatherName = weatherName;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWeatherTemprature() {
        return weatherTemprature;
    }

    public void setWeatherTemprature(double weatherTemprature) {
        this.weatherTemprature = weatherTemprature;
    }

    public double getMinTemprature() {
        return minTemprature;
    }

    public void setMinTemprature(double minTemprature) {
        this.minTemprature = minTemprature;
    }

    public double getMaxTemprature() {
        return maxTemprature;
    }

    public void setMaxTemprature(double maxTemprature) {
        this.maxTemprature = maxTemprature;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
