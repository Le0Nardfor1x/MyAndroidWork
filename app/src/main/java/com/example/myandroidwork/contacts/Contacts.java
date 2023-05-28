package com.example.myandroidwork.contacts;

public class Contacts {
    private String name;
    private String number;
    private String address;
    private String weather;
    private String temperature;

    public Contacts(String name, String number, String address, String weather, String temperature) {
        this.name = name;
        this.number = number;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
