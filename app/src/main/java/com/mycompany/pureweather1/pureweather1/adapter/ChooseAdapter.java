package com.mycompany.pureweather1.pureweather1.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycompany.pureweather1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/3/28.
 */
public class ChooseAdapter extends RecyclerView.Adapter<ChooseAdapter.ViewHolder> {
    public ArrayList<String> mDatas;
    private OnRecyclerViewItemListener mListener;

    public static interface OnRecyclerViewItemListener{
        void onRecyclerViewItemClick(View view, int pos);
    }
    public void setOnRecyclerViewItemClick(OnRecyclerViewItemListener listener){
        mListener = listener;
    }

    public ChooseAdapter(ArrayList<String> datas){
        mDatas = datas;
    }


    public ViewHolder onCreateViewHolder(ViewGroup group, int viewType){
        View view = LayoutInflater.from(group.getContext()).inflate(R.layout.choose_recyclerview_item, group, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    public void onBindViewHolder(ViewHolder viewHolder, final int position){
        viewHolder.mTextView.setText(mDatas.get(position));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRecyclerViewItemClick(v, position);
            }
        });
    }
    public int getItemCount(){
        return mDatas.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public ViewHolder(View view){
            super(view);
            mTextView = (TextView)view.findViewById(R.id.choose_item_text);
        }
    }
}
