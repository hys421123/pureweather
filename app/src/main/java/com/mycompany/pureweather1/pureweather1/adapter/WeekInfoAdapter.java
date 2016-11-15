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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mycompany.pureweather1.R;
import com.mycompany.pureweather1.pureweather1.model.WeatherTitle;
import com.mycompany.pureweather1.pureweather1.util.Setting;
import com.mycompany.pureweather1.pureweather1.util.Utility;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Lenovo on 2016/4/8.
 */
public class WeekInfoAdapter extends RecyclerView.Adapter<WeekInfoAdapter.ViewHolder>{
    private List<String> mDates;
    private List<String> mMaxTemps;
    private List<String> mMinTemps;
    private List<String> mTxts;
    private List<String> mTypes;
    private Context mContext;
    private Setting mSetting;

    public WeekInfoAdapter(Context context, List<String> dates, List<String> maxtemps, List<String> mintemps
            , List<String> txts, List<String> types){
        mContext = context;
        mDates = dates;
        mMaxTemps = maxtemps;
        mMinTemps = mintemps;
        mTxts = txts;
        mTypes = types;
        mSetting = Setting.getInstance();
    }

    public ViewHolder onCreateViewHolder(ViewGroup group, int viewType){
        View view = LayoutInflater.from(group.getContext()).inflate(R.layout.weekinfo_item, group, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //每一个Item的初始化
    public void onBindViewHolder(ViewHolder viewHolder, final int position){
        viewHolder.mForecastDate.setText(Utility.dayOfWeek(mDates.get(position)));
        viewHolder.mForecastMaxTemp.setText(mMaxTemps.get(position));
        viewHolder.mForecastMinTemp.setText(mMinTemps.get(position));
        viewHolder.mForecastTxt.setText(mTxts.get(position));
        /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        Glide.with(mContext)
                .load(prefs.getInt(mTypes.get(position), R.mipmap.unknow))
                .crossFade()//淡入淡出效果
                .into(viewHolder.mForecastPic);*/
        Glide.with(mContext)
                .load(mSetting.getInt(mTypes.get(position), R.mipmap.unknow))
                .crossFade()//淡入淡出效果
                .into(viewHolder.mForecastPic);

    }

    public int getItemCount(){
        return mDates.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mForecastDate;
        public TextView mForecastMaxTemp;
        public TextView mForecastMinTemp;
        public TextView mForecastTxt;
        public ImageView mForecastPic;

        public ViewHolder(View view){
            super(view);
            mForecastDate = (TextView)view.findViewById(R.id.forecast_date);
            mForecastMaxTemp = (TextView)view.findViewById(R.id.forecast_temp_max);
            mForecastMinTemp = (TextView)view.findViewById(R.id.forecast_temp_min);
            mForecastTxt = (TextView)view.findViewById(R.id.forecast_txt);
            mForecastPic = (ImageView)view.findViewById(R.id.forecast_pic);
        }
    }
}
