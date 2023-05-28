package com.example.myandroidwork.contacts;

public class WeatherInfo {
    public String weather;
    public String temperature;

    public WeatherInfo() {
        this.weather = "";
        this.temperature = "";
    }

    public WeatherInfo(String weather, String temperature) {
        this.weather = weather;
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
