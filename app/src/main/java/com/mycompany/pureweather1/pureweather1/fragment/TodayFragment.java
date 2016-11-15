package com.mycompany.pureweather1.pureweather1.fragment;



import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mycompany.pureweather1.R;
import com.mycompany.pureweather1.pureweather1.activity.MainActivity;
import com.mycompany.pureweather1.pureweather1.adapter.TodayInfoAdapter;
import com.mycompany.pureweather1.pureweather1.model.WeatherTitle;
import com.mycompany.pureweather1.pureweather1.util.Setting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment implements MainActivity.PassInfoListener{

    //private WeatherTitle mWeatherTitle;
    private ImageView mNowImage;
    private TextView mWeatherTxt;
    private TextView mUpdateTime;
    private TextView mNowTemp;
    private TextView mTodayMax;
    private TextView mTodayMin;
    private TextView mPm25;
    private TextView mSunriseTime;
    private TextView mSunsetTime;
    private TextView mAirQuality;
    private RecyclerView mHourlyRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private TodayInfoAdapter mTodayInfoAdapter;
    private WeatherTitle mWeatherTitle;
    private List<String> mDates;
    private List<String> mTemps;
    private List<String> mPops;
    private Setting mSetting;

    public void passInfo(WeatherTitle weatherTitle){
        mWeatherTitle = weatherTitle;
        /*Glide.with(getActivity())
                .load(mPres.getInt(mWeatherTitle.weatherDetails.get(0).now.cond.txt, R.mipmap.unknow))
                .crossFade()//淡入淡出效果
                .into(mNowImage);*/
        Glide.with(getActivity())
                .load(mSetting.getInt(mWeatherTitle.weatherDetails.get(0).now.cond.txt, R.mipmap.unknow))
                .crossFade()//淡入淡出效果
                .into(mNowImage);
        mWeatherTxt.setText(mWeatherTitle.weatherDetails.get(0).now.cond.txt);
        mNowTemp.setText(mWeatherTitle.weatherDetails.get(0).now.tmp + "℃");

        //取出保存的更新时间，可能是手动更新的时间，也可能是后台更新的时间
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        //如果H是大写表示24小时制
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        String updateTimeDate = sdf1.format(calendar.getTime())+" "+sdf2.format(calendar.getTime());
        mUpdateTime.setText(""+mSetting.getString("update_time_date", updateTimeDate)+" 更新");

        mTodayMax.setText(mWeatherTitle.weatherDetails.get(0).dailyForecast.get(0).tmp.max + "℃");
        mTodayMin.setText(mWeatherTitle.weatherDetails.get(0).dailyForecast.get(0).tmp.min+"℃");
        mSunriseTime.setText("日出："+mWeatherTitle.weatherDetails.get(0).dailyForecast.get(0).astro.sr);
        mSunsetTime.setText("日落："+mWeatherTitle.weatherDetails.get(0).dailyForecast.get(0).astro.ss);
        if (mWeatherTitle.weatherDetails.get(0).aqi != null){
            mPm25.setVisibility(View.VISIBLE);
            mAirQuality.setVisibility(View.VISIBLE);
            mPm25.setText("PM2.5："+mWeatherTitle.weatherDetails.get(0).aqi.city.aqi);
            mAirQuality.setText("空气质量："+mWeatherTitle.weatherDetails.get(0).aqi.city.qlty);
        }else {
            mPm25.setVisibility(View.INVISIBLE);
            mAirQuality.setVisibility(View.INVISIBLE);
        }



        if (!mPops.isEmpty()){
            mDates.clear();
            mPops.clear();
            mTemps.clear();
        }
        for (int i = 0; i<mWeatherTitle.weatherDetails.get(0).hourlyForecast.size(); i++){
            String time = mWeatherTitle.weatherDetails.get(0).hourlyForecast.get(i).date;
            mDates.add(time.substring(time.length() - 5, time.length()));

            mTemps.add("实时温度： "+mWeatherTitle.weatherDetails.get(0).hourlyForecast.get(i).tmp+"℃");
            mPops.add("降雨概率： "+mWeatherTitle.weatherDetails.get(0).hourlyForecast.get(i).pop+"%");
        }
        mTodayInfoAdapter.notifyDataSetChanged();
    }
    public TodayFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mDates = new ArrayList<>();
        mTemps = new ArrayList<>();
        mPops = new ArrayList<>();
        mSetting = Setting.getInstance();
        //mWeatherTitle = (WeatherTitle)getArguments().getSerializable(MainActivity.WEATHER_INFO);
        Log.d("Today", "初始化了");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        mNowImage = (ImageView)view.findViewById(R.id.now_image);
        mWeatherTxt = (TextView)view.findViewById(R.id.weather_txt);
        mUpdateTime = (TextView)view.findViewById(R.id.update_time);
        mNowTemp = (TextView)view.findViewById(R.id.temp_now);
        mTodayMax = (TextView)view.findViewById(R.id.temp_max);
        mTodayMin = (TextView)view.findViewById(R.id.temp_min);
        mPm25 = (TextView)view.findViewById(R.id.temp_pm);
        mAirQuality = (TextView)view.findViewById(R.id.temp_quality);
        mSunriseTime = (TextView)view.findViewById(R.id.sunrise_time);
        mSunsetTime = (TextView)view.findViewById(R.id.sunset_time);
        mHourlyRecyclerView = (RecyclerView)view.findViewById(R.id.today_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mHourlyRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mHourlyRecyclerView.setHasFixedSize(true);
        mTodayInfoAdapter = new TodayInfoAdapter(getActivity(),mDates, mTemps, mPops);
        mHourlyRecyclerView.setAdapter(mTodayInfoAdapter);
        return view;
    }



}
