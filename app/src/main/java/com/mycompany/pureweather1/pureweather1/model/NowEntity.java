package com.mycompany.pureweather1.pureweather1.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Lenovo on 2016/4/4.
 */
public class NowEntity implements Serializable{
    public CondEntity cond;
    public String fl;
    public String hum;
    public String pcpn;
    public String pres;
    public String tmp;
    public String vis;
    public WindEntity wind;

    public static class CondEntity implements Serializable {
        public String code;
        public String txt;
    }

    public static class WindEntity implements Serializable {
        public String deg;
        public String dir;
        public String sc;
        public String spd;
    }
}
