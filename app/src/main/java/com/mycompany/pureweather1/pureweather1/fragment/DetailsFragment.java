package com.mycompany.pureweather1.pureweather1.fragment;


import android.app.Activity;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycompany.pureweather1.R;
import com.mycompany.pureweather1.pureweather1.activity.MainActivity;
import com.mycompany.pureweather1.pureweather1.adapter.DetailsInfoAdapter;
import com.mycompany.pureweather1.pureweather1.adapter.WeekInfoAdapter;
import com.mycompany.pureweather1.pureweather1.model.WeatherTitle;
import com.mycompany.pureweather1.pureweather1.util.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment implements MainActivity.PassInfoListener{

    private List<String> mBriefs;
    private List<String> mTxts;
    private List<String> mTypes;
    private WeatherTitle mWeatherTitle;
    private RecyclerView mRecyclerView;
    private DetailsInfoAdapter mDetailsInfoAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Setting mSetting;

    public void passInfo(WeatherTitle weatherTitle){
        mWeatherTitle = weatherTitle;

        if (!mBriefs.isEmpty()){
            mBriefs.clear();
            mTxts.clear();
        }
        if (mWeatherTitle.weatherDetails.get(0).suggestion != null){
            mBriefs.add(mWeatherTitle.weatherDetails.get(0).suggestion.drsg.brf);
            mBriefs.add(mWeatherTitle.weatherDetails.get(0).suggestion.uv.brf);
            mBriefs.add(mWeatherTitle.weatherDetails.get(0).suggestion.cw.brf);
            mBriefs.add(mWeatherTitle.weatherDetails.get(0).suggestion.trav.brf);
            mBriefs.add(mWeatherTitle.weatherDetails.get(0).suggestion.flu.brf);
            mBriefs.add(mWeatherTitle.weatherDetails.get(0).suggestion.sport.brf);

            mTxts.add(mWeatherTitle.weatherDetails.get(0).suggestion.drsg.txt);
            mTxts.add(mWeatherTitle.weatherDetails.get(0).suggestion.uv.txt);
            mTxts.add(mWeatherTitle.weatherDetails.get(0).suggestion.cw.txt);
            mTxts.add(mWeatherTitle.weatherDetails.get(0).suggestion.trav.txt);
            mTxts.add(mWeatherTitle.weatherDetails.get(0).suggestion.flu.txt);
            mTxts.add(mWeatherTitle.weatherDetails.get(0).suggestion.sport.txt);

        }else{
            for (int i = 0; i<6; i++){
                mBriefs.add(i, "null");
                mTxts.add(i, "暂不支持");
            }
        }
        mTypes.add("drsg");
        mTypes.add("uv");
        mTypes.add("cw");
        mTypes.add("trav");
        mTypes.add("flu");
        mTypes.add("sport");

        mDetailsInfoAdapter.notifyDataSetChanged();


    }

    public DetailsFragment() {

    }


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mBriefs = new ArrayList<String>();
        mTxts = new ArrayList<String>();
        mTypes = new ArrayList<String>();
        Log.d("Details","初始化了");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.details_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mDetailsInfoAdapter = new DetailsInfoAdapter(getActivity(), mBriefs, mTxts, mTypes);
        mRecyclerView.setAdapter(mDetailsInfoAdapter);
        return view;
    }

    /*public void onAttach(Activity activity) {
        super.onAttach(activity);
        mBriefs = new ArrayList<String>();
        mTxts = new ArrayList<String>();
    }*/

}
