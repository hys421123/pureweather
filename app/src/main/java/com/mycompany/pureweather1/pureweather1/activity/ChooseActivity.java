package com.mycompany.pureweather1.pureweather1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.StringRequestUTF8;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.mycompany.pureweather1.R;
import com.mycompany.pureweather1.pureweather1.adapter.ChooseAdapter;
import com.mycompany.pureweather1.pureweather1.base.BaseActivity;
import com.mycompany.pureweather1.pureweather1.db.DBManager;
import com.mycompany.pureweather1.pureweather1.model.City;
import com.mycompany.pureweather1.pureweather1.model.County;
import com.mycompany.pureweather1.pureweather1.model.Province;
import com.mycompany.pureweather1.pureweather1.util.Utility;

import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

public class ChooseActivity extends BaseActivity {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ChooseAdapter mAdapter;
    private DBManager mDBManager;
    private ImageView mImageView;
    private ContentLoadingProgressBar mProgressBar;
    private ArrayList<String> mNameList;
    private List<Province> mProvinces;
    private Province mSelectedProvince;
    private List<City> mCities;
    private City mSelectedCity;
    private List<County> mCounties;
    private County mSelectedCounty;

    private int mCurrentLevel;
    private RequestQueue mQueue;

    private static final String TAG = "ChooseActivity";
    private static final int PROVINCE_LEVEL = 0;
    private static final int CITY_LEVEL = 1;
    private static final int COUNTY_LEVEL = 2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.choose_collapsing);
        mCollapsingToolbarLayout.setTitle("  ");

        mImageView = (ImageView)findViewById(R.id.bannner);
        Glide.with(this).load(R.mipmap.dribbble_city2).into(mImageView);
        /*mNameList = new ArrayList<String>();
        for(int i = 0; i< 20; i++){
            mNameList.add(i, "position" + i);
        }*/

        mNameList = new ArrayList<String>();

        mDBManager = DBManager.get(this);

        mProgressBar = (ContentLoadingProgressBar)findViewById(R.id.progressbar);


        mRecyclerView = (RecyclerView)findViewById(R.id.choose_recyclerview);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new ChooseAdapter(mNameList);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);



        mAdapter.setOnRecyclerViewItemClick(new ChooseAdapter.OnRecyclerViewItemListener() {
            @Override
            public void onRecyclerViewItemClick(View view, int pos) {
                if (mCurrentLevel == PROVINCE_LEVEL) {
                    Log.d(TAG, "执行了点击1");
                    mSelectedProvince = mProvinces.get(pos);
                    mProgressBar.show();
                    queryCities(mSelectedProvince);

                } else if (mCurrentLevel == CITY_LEVEL) {
                    Log.d(TAG, "执行了点击2");
                    mSelectedCity = mCities.get(pos);
                    mProgressBar.show();
                    queryCounties(mSelectedCity);

                } else if (mCurrentLevel == COUNTY_LEVEL) {
                    Log.d(TAG, "执行了点击3");
                    mSelectedCounty = mCounties.get(pos);
                    Intent intent = new Intent();
                    intent.putExtra("countyCode", mSelectedCounty.getCountyCode());
                    intent.putExtra("countyName", mSelectedCounty.getCountyName());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        mQueue = Volley.newRequestQueue(this);

        queryProvinces();

    }

    private void queryProvinces(){
        mProvinces = mDBManager.loadProvinces();
        if(mProvinces.size()>0){
            mNameList.clear();
            for(Province province:mProvinces){
                mNameList.add(province.getProvinceName());
            }
            mAdapter.notifyDataSetChanged();

            mCollapsingToolbarLayout.setTitle("请选择省级行政区");
            mCurrentLevel = PROVINCE_LEVEL;
            mProgressBar.hide();
        }
        else downloadLists(-1, null);
    }

    private void queryCities(Province province){
        mCities = mDBManager.loadCities(province.getId());
        if(mCities.size() > 0){

            mNameList.clear();
            for(City city : mCities){
                mNameList.add(city.getCityName());
            }

            mAdapter.notifyDataSetChanged();
            //mProgressBar.hide();
            /*for (int i = mProvinces.size() - 1; i >= 0; i--){
                mNameList.remove(i);
                mAdapter.notifyItemRemoved(i);
            }
            //mNameList.clear();
            for (int j = 0; j < mCities.size(); j++){
                mNameList.add(mCities.get(j).getCityName());
                mAdapter.notifyItemInserted(j);
            }*/
            //mAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(0);
            mCollapsingToolbarLayout.setTitle(mSelectedProvince.getProvinceName());
            mCurrentLevel = CITY_LEVEL;
            mProgressBar.hide();
        }
        else downloadLists(mCurrentLevel, mSelectedProvince.getProvinceCode());
    }

    private void queryCounties(City city){
        mCounties = mDBManager.loadCounties(city.getId());
        if(mCounties.size()>0){
            //mNameList.clear();
            setTitle(mSelectedCity.getCityName());
            mNameList.clear();
            for(County county : mCounties){
                mNameList.add(county.getCountyName());
            }
            mAdapter.notifyDataSetChanged();
            //mProgressBar.hide();
            /*for (int i = mCities.size() - 1; i >= 0; i--){
                mNameList.remove(i);
                mAdapter.notifyItemRemoved(i);
            }
            //mNameList.clear();
            for (int j = 0; j < mCounties.size(); j++){
                mNameList.add(mCounties.get(j).getCountyName());
                mAdapter.notifyItemInserted(j);
            }*/
            mRecyclerView.smoothScrollToPosition(0);
            mCollapsingToolbarLayout.setTitle(mSelectedCity.getCityName());
            mCurrentLevel = COUNTY_LEVEL;
            mProgressBar.hide();
        }
        else downloadLists(mCurrentLevel, mSelectedCity.getCityCode());
    }

    private void downloadLists(int level, String code){
        if(code == null){
            String address = "http://www.weather.com.cn/data/list3/city.xml";
            StringRequestUTF8 request = new StringRequestUTF8(address,
                    new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    boolean result = false;
                    result = Utility.handleProvincesResponse(mDBManager, response);
                    if (result){
                        queryProvinces();
                    }
                }
            },
                    new Response.ErrorListener(){
                public void onErrorResponse(VolleyError error){
                    Log.d(TAG, error.getMessage(), error);
                }
            });
            mQueue.add(request);
        } else if(level == PROVINCE_LEVEL && code != null){
            String address = "http://www.weather.com.cn/data/list3/city" + code +".xml";
            StringRequestUTF8 request = new StringRequestUTF8(address,
                    new Response.Listener<String>(){
                public void onResponse(String response){
                    boolean result = false;
                    result = Utility.handleCitiesResponse(mDBManager, response, mSelectedProvince);
                    if (result){
                        queryCities(mSelectedProvince);
                    }
                }
            },
                    new Response.ErrorListener(){
                public void onErrorResponse(VolleyError error){
                    Log.d(TAG, error.getMessage(), error);
                }
            });
            mQueue.add(request);
        }else if(mCurrentLevel == CITY_LEVEL && code != null){
            String address = "http://www.weather.com.cn/data/list3/city" + code +".xml";
            StringRequestUTF8 request = new StringRequestUTF8(address,
                    new Response.Listener<String>(){
                        public void onResponse(String response){
                            boolean result = false;
                            result = Utility.handleCountiesResponse(mDBManager, response, mSelectedCity);
                            if (result){
                                queryCounties(mSelectedCity);
                            }
                        }
                    },
                    new Response.ErrorListener(){
                        public void onErrorResponse(VolleyError error){
                            Log.d(TAG, error.getMessage(), error);
                        }
                    });
            mQueue.add(request);
        }

    }
    public void onBackPressed(){
        if(mCurrentLevel == CITY_LEVEL){
            queryProvinces();
        }else if(mCurrentLevel == COUNTY_LEVEL){
            queryCities(mSelectedProvince);
        }else if(mCurrentLevel == PROVINCE_LEVEL){
            super.onBackPressed();
        }
    }
}
