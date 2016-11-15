package com.mycompany.pureweather1.pureweather1.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Lenovo on 2016/4/4.
 */
public class BasicEntity implements Serializable{
    public String city;
    public String cnty;
    public String id;
    public String lat;
    public String lon;
    public UpdateEntity update;
    public static class UpdateEntity implements Serializable{
        public String loc;
        public String utc;
    }
}
