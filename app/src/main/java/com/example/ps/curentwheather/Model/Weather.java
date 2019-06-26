package com.example.ps.curentwheather.Model;

import com.example.ps.curentwheather.R;

public class Weather {

    private String weatherName;
    private String weatherDescription;
    private double windSpeed;
    private double windDegree;
    private int himidity;
    private double weatherTemprature;
    private double minTemprature;
    private double maxTemprature;
    private int pressure;
    private String cityName;
    private String country;

    public String getCountry(Weather weather) {
        if (weather.country.endsWith("IR")){
            return "IRAN";
        }else {
            return country;
        }
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

    public int getHimidity() {
        return himidity;
    }

    public void setHimidity(int himidity) {
        this.himidity = himidity;
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
