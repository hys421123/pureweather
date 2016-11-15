package com.mycompany.pureweather1.pureweather1.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mycompany.pureweather1.R;
import com.mycompany.pureweather1.pureweather1.util.Setting;

import java.util.List;

/**
 * Created by Lenovo on 2016/4/11.
 */
public class TodayInfoAdapter extends RecyclerView.Adapter<TodayInfoAdapter.ViewHolder> {
    private List<String> mDates;
    private List<String> mTemps;
    private List<String> mPops;
    private Context mContext;
    private Setting mSetting;

    public TodayInfoAdapter(Context context, List<String> dates, List<String> temps
            , List<String> pops){
        mContext = context;
        mDates = dates;
        mTemps = temps;
        mPops = pops;
        mSetting = Setting.getInstance();
    }

    public ViewHolder onCreateViewHolder(ViewGroup group, int viewType){
        View view = LayoutInflater.from(group.getContext()).inflate(R.layout.todayinfo_item, group, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //每一个Item的初始化
    public void onBindViewHolder(ViewHolder viewHolder, final int position){
        viewHolder.mHourlyTime.setText(mDates.get(position));
        viewHolder.mHourlyTemp.setText(mTemps.get(position));
        viewHolder.mHourlyPop.setText(mPops.get(position));
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        /*Glide.with(mContext)
                .load(prefs.getInt(mTypes.get(position), R.mipmap.unknow))
                .crossFade()//淡入淡出效果
                .into(viewHolder.mForecastPic);*/

    }

    public int getItemCount(){
        return mDates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mHourlyTime;
        public TextView mHourlyTemp;
        public TextView mHourlyPop;
        public ImageView mTimeIcon;
        //public ImageView mForecastPic;

        public ViewHolder(View view){
            super(view);
            mTimeIcon = (ImageView)view.findViewById(R.id.today_icon);
            //mTimeIcon.setColorFilter(R.color.colorDay);
            mHourlyTime = (TextView)view.findViewById(R.id.today_time);
            mHourlyTemp = (TextView)view.findViewById(R.id.today_temp);
            mHourlyPop = (TextView)view.findViewById(R.id.today_pop);
            //mForecastPic = (ImageView)view.findViewById(R.id.forecast_pic);
        }
    }
}
