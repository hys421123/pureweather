package com.mycompany.pureweather1.pureweather1.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Lenovo on 2016/4/4.
 */
public class HourlyForecastEntity implements Serializable{
    public String date;
    public String hum;
    public String pop;
    public String pres;
    public String tmp;
    public WindEntity wind;

    public static class WindEntity implements Serializable{
        public String deg;
        public String dir;
        public String sc;
        public String spd;
    }
}
