package com.mycompany.pureweather1.pureweather1.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Lenovo on 2016/4/4.
 */
public class SuggestionEntity implements Serializable{
    public ComfEntity comf;
    public CwEntity cw;
    public DrsgEntity drsg;
    public FluEntity flu;
    public SportEntity sport;
    public TravEntity trav;
    public UvEntity uv;

    public static class ComfEntity implements Serializable {
        public String brf;
        public String txt;
    }

    public static class CwEntity implements Serializable {
        public String brf;
        public String txt;
    }

    public static class DrsgEntity implements Serializable {
        public String brf;
        public String txt;
    }

    public static class FluEntity implements Serializable {
        public String brf;
        public String txt;
    }

    public static class SportEntity implements Serializable {
        public String brf;
        public String txt;
    }

    public static class TravEntity implements Serializable {
        public String brf;
        public String txt;
    }

    public static class UvEntity implements Serializable {
        public String brf;
        public String txt;
    }
}
