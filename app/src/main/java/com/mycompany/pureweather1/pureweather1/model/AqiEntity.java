package com.mycompany.pureweather1.pureweather1.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Lenovo on 2016/4/4.
 */
public class AqiEntity implements Serializable{
    public CityEntity city;
    public static class CityEntity implements Serializable{
        public String aqi;
        public String co;
        public String no2;
        public String o3;
        public String pm10;
        public String pm25;
        public String qlty;
        public String so2;
    }
}
