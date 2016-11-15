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
 * Created by Lenovo on 2016/4/8.
 */
public class DetailsInfoAdapter extends RecyclerView.Adapter<DetailsInfoAdapter.ViewHolder> {
    private List<String> mBriefs;
    private List<String> mTxts;
    private List<String> mTypes;
    private Context mContext;
    private Setting mSetting;


    public DetailsInfoAdapter(Context context, List<String> briefs, List<String> txts, List<String> types){
        mContext = context;
        mBriefs = briefs;
        mTxts = txts;
        mTypes = types;
        mSetting = Setting.getInstance();
    }

    public ViewHolder onCreateViewHolder(ViewGroup group, int viewType){
        View view = LayoutInflater.from(group.getContext()).inflate(R.layout.detailsinfo_item, group, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int position){
        viewHolder.mSuggestionBrief.setText(mBriefs.get(position));
        viewHolder.mSuggestionTxt.setText(mTxts.get(position));
        /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        Glide.with(mContext)
                .load(prefs.getInt(mTypes.get(position), R.mipmap.unknow))
                .crossFade()//淡入淡出效果
                .into(viewHolder.mSuggestionIcon);*/
        Glide.with(mContext)
                .load(mSetting.getInt(mTypes.get(position), R.mipmap.unknow))
                .crossFade()//淡入淡出效果
                .into(viewHolder.mSuggestionIcon);
        //如果加上图片的话，传入一个Map，根据position选出图片，应该是这样。
    }

    //这里需要注意
    public int getItemCount(){
        return mBriefs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mSuggestionBrief;
        public TextView mSuggestionTxt;
        public ImageView mSuggestionIcon;

        public ViewHolder(View view){
            super(view);
            mSuggestionIcon = (ImageView)view.findViewById(R.id.details_icon);
            mSuggestionBrief = (TextView)view.findViewById(R.id.details_brief);
            mSuggestionTxt = (TextView)view.findViewById(R.id.details_txt);

        }
    }
}
