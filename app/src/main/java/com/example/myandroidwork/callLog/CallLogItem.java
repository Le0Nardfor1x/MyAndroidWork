package com.example.myandroidwork.callLog;

public class CallLogItem {
    private String name;
    private String number;
    private int type;
    private long date;
    private String address;

    public CallLogItem(String name, String number, int type, long date) {
        this.name = name;
        this.number = number;
        this.type = type;
        this.date = date;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CallLogItem{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", type=" + type +
                ", date=" + date +
                ", address='" + address + '\'' +
                '}';
    }
}
