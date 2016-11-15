package com.mycompany.pureweather1.pureweather1.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lenovo on 2016/4/4.
 */
public class WeatherDetails implements Serializable{
    @SerializedName("aqi")
    public AqiEntity aqi;

    @SerializedName("basic")
    public BasicEntity basic;

    @SerializedName("daily_forecast")
    public List<DailyForecastEntity> dailyForecast;

    @SerializedName("hourly_forecast")
    public List<HourlyForecastEntity> hourlyForecast;

    @SerializedName("now")
    public NowEntity now;

    public String status;

    @SerializedName("suggestion")
    public SuggestionEntity suggestion;



}
