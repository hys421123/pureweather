package com.mycompany.pureweather1.pureweather1.fragment;

import android.app.Activity;
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
import android.widget.TextView;

import com.mycompany.pureweather1.R;
import com.mycompany.pureweather1.pureweather1.activity.MainActivity;
import com.mycompany.pureweather1.pureweather1.adapter.WeekInfoAdapter;
import com.mycompany.pureweather1.pureweather1.model.WeatherTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeekFragment extends Fragment implements MainActivity.PassInfoListener{

    private List<String> mDates;
    private List<String> mMaxTemps;
    private List<String> mMinTemps;
    private List<String> mTxts;
    private List<String> mTypes;

    private WeatherTitle mWeatherTitle;
    private RecyclerView mRecyclerView;
    private WeekInfoAdapter mWeekInfoAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public void passInfo(WeatherTitle weatherTitle){
        mWeatherTitle = weatherTitle;

        if (!mDates.isEmpty()){
            mDates.clear();
            mTxts.clear();
            mMaxTemps.clear();
            mMinTemps.clear();
            mTypes.clear();
        }
        for(int i = 0; i<mWeatherTitle.weatherDetails.get(0).dailyForecast.size(); i++) {
            mDates.add(mWeatherTitle.weatherDetails.get(0).dailyForecast.get(i).date);
            mMaxTemps.add(mWeatherTitle.weatherDetails.get(0).dailyForecast.get(i).tmp.max + "℃ ");
            mMinTemps.add(mWeatherTitle.weatherDetails.get(0).dailyForecast.get(i).tmp.min + "℃ ");
            mTxts.add("白天：" + mWeatherTitle.weatherDetails.get(0).dailyForecast.get(i).cond.txtD +
                    " / 夜晚：" + mWeatherTitle.weatherDetails.get(0).dailyForecast.get(i).cond.txtN);
            mTypes.add(mWeatherTitle.weatherDetails.get(0).dailyForecast.get(i).cond.txtD);
        }
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mWeekInfoAdapter.notifyDataSetChanged();
    }

    public WeekFragment() {
        // Required empty public constructor
    }

    /*public void onAttach(Activity activity) {
        super.onAttach(activity);
        mDates = new ArrayList<String>();
        mTemps = new ArrayList<String>();
        mTxts = new ArrayList<String>();
    }*/

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mDates = new ArrayList<String>();
        mMaxTemps = new ArrayList<String>();
        mMinTemps = new ArrayList<String>();
        mTxts = new ArrayList<String>();
        mTypes = new ArrayList<String>();
        Log.d("Week", "初始化了");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.week_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mWeekInfoAdapter = new WeekInfoAdapter(getActivity(), mDates, mMaxTemps, mMinTemps, mTxts, mTypes);
        mRecyclerView.setAdapter(mWeekInfoAdapter);
        return view;
    }

}
