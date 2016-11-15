package com.mycompany.pureweather1.pureweather1.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Lenovo on 2016/4/4.
 */
public class DailyForecastEntity implements Serializable{
    public AstroEntity astro;
    public CondEntity cond;
    public String date;
    public String hum;
    public String pcpn;
    public String pop;
    public String pres;
    public TmpEntity tmp;
    public String vis;
    public WindEntity wind;

    public static class AstroEntity implements Serializable{
        public String sr;
        public String ss;
    }

    public static class CondEntity implements Serializable{
        @SerializedName("code_d") public String codeD;
        @SerializedName("code_n") public String codeN;
        @SerializedName("txt_d") public String txtD;
        @SerializedName("txt_n") public String txtN;
    }

    public static class TmpEntity implements Serializable{
        public String max;
        public String min;
    }

    public static class WindEntity implements Serializable{
        public String deg;
        public String dir;
        public String sc;
        public String spd;
    }
}
