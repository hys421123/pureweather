package com.mycompany.pureweather1.pureweather1.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/4/4.
 */
public class WeatherTitle implements Serializable{
    @SerializedName("HeWeather data service 3.0")
    public List<WeatherDetails> weatherDetails = new ArrayList<WeatherDetails>();
}
