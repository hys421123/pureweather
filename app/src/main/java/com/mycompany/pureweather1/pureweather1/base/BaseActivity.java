package com.mycompany.pureweather1.pureweather1.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mycompany.pureweather1.R;
import com.mycompany.pureweather1.pureweather1.util.Setting;

/**
 * Created by Lenovo on 2016/4/29.
 */
public class BaseActivity extends AppCompatActivity {
    public Setting mSetting = null;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mSetting = Setting.getInstance();
        mSetting.putInt("晴", R.mipmap.sunny);
        mSetting.putInt("阴", R.mipmap.overcast);
        mSetting.putInt("多云", R.mipmap.cloudy);
        mSetting.putInt("少云", R.mipmap.fewclouds);
        mSetting.putInt("晴间多云", R.mipmap.partlycloudy);
        mSetting.putInt("小雨", R.mipmap.lightrain);
        mSetting.putInt("中雨", R.mipmap.moderaterain);
        mSetting.putInt("大雨", R.mipmap.heavyrain);
        mSetting.putInt("阵雨", R.mipmap.showerrain);
        mSetting.putInt("雷阵雨", R.mipmap.thundershower);
        mSetting.putInt("霾", R.mipmap.haze);
        mSetting.putInt("雾", R.mipmap.foggy);
        mSetting.putInt("毛毛雨/细雨", R.mipmap.drizzlerain);
        mSetting.putInt("雨夹雪", R.mipmap.sleet);
        mSetting.putInt("强阵雨", R.mipmap.heavyshowerrain);
        mSetting.putInt("雷阵雨伴有冰雹", R.mipmap.hail);
        mSetting.putInt("暴雨", R.mipmap.storm);
        mSetting.putInt("大暴雨", R.mipmap.heavystorm);
        mSetting.putInt("特大暴雨", R.mipmap.severestorm);
        mSetting.putInt("冻雨", R.mipmap.freezingrain);
        mSetting.putInt("小雪", R.mipmap.lightsnow);
        mSetting.putInt("中雪", R.mipmap.moderatesnow);
        mSetting.putInt("大雪", R.mipmap.heavysnow);
        mSetting.putInt("暴雪", R.mipmap.snowstorm);
        mSetting.putInt("薄雾", R.mipmap.mist);
        mSetting.putInt("扬沙", R.mipmap.sand);
        mSetting.putInt("浮尘", R.mipmap.dust);
        mSetting.putInt("沙尘暴", R.mipmap.duststorm);
        mSetting.putInt("强沙尘暴", R.mipmap.sandstorm);

        mSetting.putInt("drsg", R.mipmap.cloth2);
        mSetting.putInt("uv", R.mipmap.ic_beach_access_black_24dp);
        mSetting.putInt("cw", R.mipmap.ic_local_car_wash_black_24dp);
        mSetting.putInt("trav", R.mipmap.travel2);
        mSetting.putInt("flu", R.mipmap.pill2);
        mSetting.putInt("sport", R.mipmap.sport2);

    }
}
