package com.mycompany.pureweather1.pureweather1.Service;

import android.app.IntentService;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mycompany.pureweather1.pureweather1.base.BaseApplication;
import com.mycompany.pureweather1.pureweather1.model.WeatherTitle;
import com.mycompany.pureweather1.pureweather1.util.Setting;
import com.mycompany.pureweather1.pureweather1.util.Utility;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lenovo on 2016/5/1.
 */
public class BackGroundUpdateService extends IntentService{
    private Setting mSetting;
    private static final String TAG = "BackGroundUpdateService";
    private WeatherTitle mWeatherTitle;
    private String mCountyName;
    private Long mLastRefreshTime;

    public BackGroundUpdateService(){
        super(TAG);
        mSetting = Setting.getInstance();
    }

    public void onHandleIntent(Intent intent){
        Log.d("后台任务正在执行", "确认执行");
        String weatherCode = mSetting.getString("weather_code", "101010100");
        String address = "https://api.heweather.com/x3/weather?cityid=CN"+ weatherCode +"&key=15fe94dbab3543b19e577cf8e5b0f944";
        JsonObjectRequest request = new JsonObjectRequest(address, null, new Response.Listener<JSONObject>(){
            public void onResponse(JSONObject response){
                Log.d(TAG, response.toString());
                mWeatherTitle = new Gson().fromJson(response.toString(), WeatherTitle.class);
                Log.d(TAG, "" + mWeatherTitle.weatherDetails.get(0).suggestion.sport.txt);
                boolean IsInfoSaved = Utility.saveWeatherInfoToFile(getApplicationContext(), mWeatherTitle, "weather_information");
                Log.d("天气信息是否保存成功:", " "+IsInfoSaved);
                //mCountyName = Utility.getCountyName(getApplicationContext());
                //mCountyName = mSetting.getString("county_name", "北京");
                mLastRefreshTime = System.currentTimeMillis();
                Log.d("刷新时间保存为", new Date(mLastRefreshTime).toString());
                //Utility.saveRefreshTime(mLastRefreshTime, getApplicationContext());

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                //如果H是大写表示24小时制
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
                String updateTimeDate = sdf1.format(calendar.getTime())+" "+sdf2.format(calendar.getTime());

                Log.d("刷新的时间保存为调整后的格式是：", updateTimeDate);

                mSetting.putLong("refresh_time", mLastRefreshTime);

                mSetting.putString("update_time_date", updateTimeDate);

            }
        }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){
                Log.e("TAG", error.getMessage(), error);

            }
        });
        BaseApplication.mQueue.add(request);
    }
}
