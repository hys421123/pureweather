package com.mycompany.pureweather1.pureweather1.activity;



import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequestUTF8;
import com.android.volley.toolbox.Volley;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import com.mycompany.pureweather1.R;
import com.mycompany.pureweather1.pureweather1.adapter.DepthPageTransformer;
import com.mycompany.pureweather1.pureweather1.adapter.InfoViewPagerAdapter;
import com.mycompany.pureweather1.pureweather1.adapter.SolutionSwipeRefreshLayout;
import com.mycompany.pureweather1.pureweather1.base.BaseActivity;
import com.mycompany.pureweather1.pureweather1.base.BaseApplication;
import com.mycompany.pureweather1.pureweather1.fragment.DetailsFragment;
import com.mycompany.pureweather1.pureweather1.fragment.TodayFragment;
import com.mycompany.pureweather1.pureweather1.fragment.WeekFragment;
import com.mycompany.pureweather1.pureweather1.model.WeatherTitle;
import com.mycompany.pureweather1.pureweather1.util.Setting;
import com.mycompany.pureweather1.pureweather1.util.Utility;

import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Handler;

import cn.like.nightmodel.NightModelManager;

public class MainActivity extends BaseActivity implements View.OnClickListener
        , ViewPager.OnPageChangeListener,SwipeRefreshLayout.OnRefreshListener{
// 285行有注释
//  主函数注释提交  这次修改好几个  云端多次修改多处

    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mMainImage;
    private TabLayout mTabLayout;
    private NavigationView mNavigationView;
    private ImageView mDrawerHeader;
    private SolutionSwipeRefreshLayout mSwipeRefreshLayout;
    //private RequestQueue mQueue;
    private TodayFragment mTodayFragment;
    private WeekFragment mWeekFragment;
    private DetailsFragment mDetailsFragment;
    private List<Fragment> mWeatherFragments;
    private List<String> mFragmentTitle;
    private ViewPager mViewPager;
    private InfoViewPagerAdapter mInfoViewPagerAdapter;
    private WeatherTitle mWeatherTitle;
    private PassInfoListener mListener1;
    private PassInfoListener mListener2;

    private PassInfoListener mListener3;

    private long mLastRefreshTime;
    private long mNowTime;

    private String mCountyCode;
    private String mCountyName;

    private boolean mDayFlag;


    private static final String TAG = "MainActivity";
    private static int REQUEST_FOR_PLACE = 0;
    private static int REQUEST_FOR_BACKGROUNDUPDATE = 1;
    public static final String WEATHER_INFO = "today_info";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        NightModelManager.getInstance().attach(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDrawer();

    }

    private void initView(){
        mCoordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorlayout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.mCollapsingToolbarLayout);
        mCollapsingToolbarLayout.setTitle("  ");
        mMainImage = (ImageView)findViewById(R.id.main_image);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //显示出home图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //使能home图标
        getSupportActionBar().setHomeButtonEnabled(true);

        /*
        *
        * 初始化Tab
        *
        * */
        mTabLayout = (TabLayout)findViewById(R.id.tablayout);
        mTabLayout.addTab(mTabLayout.newTab().setText("今日"));
        mTabLayout.addTab(mTabLayout.newTab().setText("七天"));
        mTabLayout.addTab(mTabLayout.newTab().setText("建议"));

        //SwipeRefreshLayout
        mSwipeRefreshLayout = (SolutionSwipeRefreshLayout)findViewById(R.id.swiperefreshlayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        /*
        *
        *
        * 初始化ViewPager的Fragment
        *
        */
        mWeatherFragments = new ArrayList<Fragment>();
        mTodayFragment = new TodayFragment();
        mWeekFragment = new WeekFragment();
        mDetailsFragment = new DetailsFragment();
        mWeatherFragments.add(mTodayFragment);
        mWeatherFragments.add(mWeekFragment);
        mWeatherFragments.add(mDetailsFragment);
        mListener1 = mTodayFragment;
        mListener2 = mWeekFragment;
        mListener3 = mDetailsFragment;
        mFragmentTitle = new ArrayList<>();
        mFragmentTitle.add("今日");
        mFragmentTitle.add("七天");
        mFragmentTitle.add("建议");
        mInfoViewPagerAdapter = new InfoViewPagerAdapter(getSupportFragmentManager(), mWeatherFragments, mFragmentTitle);

        /*
        *
        *
        * 初始化ViewPager
        *
        */
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mInfoViewPagerAdapter);
        //动画效果
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        //这个方法必须在setAdapter方法之后调用,让TabLayout与ViewPager匹配
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
    }

    private void initDrawer(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //点击home后能展开侧滑栏
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drwer_open, R.string.drwer_close){
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        //让home图标改变并且响应开关动作。
        mToggle.syncState();
        mDrawerLayout.setDrawerListener(mToggle);



        mNavigationView = (NavigationView)findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                //item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.drawer_choose:
                        Intent intentChoose = new Intent(MainActivity.this, ChooseActivity.class);
                        startActivityForResult(intentChoose, REQUEST_FOR_PLACE);
                        //mDrawerLayout.closeDrawers();
                        break;
                    case R.id.drawer_switch:
                        if (mDayFlag) {
                            Glide.with(getApplicationContext()).load(R.mipmap.night).into(mMainImage);
                            Snackbar.make(mCoordinatorLayout
                                    , "                                    自动切换为夜间背景", Snackbar.LENGTH_SHORT).show();
                            mDayFlag = false;
                            //Utility.savePicStatus(mDayFlag, getApplicationContext());
                            mSetting.putBoolean("picture_status", mDayFlag);
                        } else if (!mDayFlag) {
                            Glide.with(getApplicationContext()).load(R.mipmap.day).into(mMainImage);
                            Snackbar.make(mCoordinatorLayout
                                    , "                                    自动切换为日间背景", Snackbar.LENGTH_SHORT).show();
                            mDayFlag = true;
                            //Utility.savePicStatus(mDayFlag, getApplicationContext());
                            mSetting.putBoolean("picture_status", mDayFlag);
                        }
                        break;
                    case R.id.drawer_setting:
                        Intent intentSetting = new Intent(MainActivity.this, SettingActivity.class);
                        startActivityForResult(intentSetting, REQUEST_FOR_BACKGROUNDUPDATE);
                        break;
                    case R.id.drawer_about:
                        Intent intentAbout = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intentAbout);
                        break;
                }
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });


        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024 / 1024);
        Log.d(TAG, "最大内存是 " + maxMemory + "M");
    }

    public void onResume(){
        super.onResume();

        /*
        * 获取保存在文件中的天气信息，保存在SP中的county_code，county_name，日夜Flag
        */
        mWeatherTitle = (WeatherTitle)Utility.getWeatherInfoFromFile(getApplicationContext(), "weather_information");
        mCountyCode = mSetting.getString("county_code", "010101");
        mCountyName = mSetting.getString("county_name", "选择城市或刷新");
        mDayFlag = mSetting.getBoolean("picture_status", true);


        /*
        * 利用信息更新UI
        */
        mCollapsingToolbarLayout.setTitle(mCountyName);
        if(mDayFlag){
            Glide.with(this).load(R.mipmap.day).into(mMainImage);
        }else {
            Glide.with(this).load(R.mipmap.night).into(mMainImage);
        }

        if(mWeatherTitle != null){
            mViewPager.post(new Runnable() {
                @Override
                public void run() {
                    /*Log.d(TAG, "  ViewPager的 高 是： " + mViewPager.getMeasuredHeight() +
                            "单个Fragment的 宽 是： " + mViewPager.getMeasuredWidth());*/
//                    mListener1.passInfo(mWeatherTitle);
//                    mListener2.passInfo(mWeatherTitle);
//                    mListener3.passInfo(mWeatherTitle);
                }
            });
            String sunriseTime;
            String sunsetTime;
            String currentTime;
            SimpleDateFormat sdf=new SimpleDateFormat("HH:mm", Locale.getDefault());
            currentTime = sdf.format(new Date());
            sunriseTime = mWeatherTitle.weatherDetails.get(0).dailyForecast.get(0).astro.sr;
            sunsetTime = mWeatherTitle.weatherDetails.get(0).dailyForecast.get(0).astro.ss;

            Log.d("当前时间为：", currentTime);
            Log.d("日出时间为：", sunriseTime);
            Log.d("日落时间为：", sunsetTime);

            Log.d("判断结果为：", ""+(((Utility.makeComparison(currentTime, "00:00")>0 && Utility.makeComparison(currentTime, sunriseTime)<0)
                    || (Utility.makeComparison(currentTime, sunsetTime)>0 && Utility.makeComparison(currentTime, "24:00")<0))
                    && mDayFlag == true));

            if ((Utility.makeComparison(currentTime, sunriseTime)>0 && Utility.makeComparison(currentTime,sunsetTime)<0)
                    && mDayFlag == false){
                Log.d("现在应该是日间", "");
                mDayFlag = !mDayFlag;
                Glide.with(this).load(R.mipmap.day).into(mMainImage);
                Snackbar.make(mCoordinatorLayout
                        , "                                    自动切换为日间背景", Snackbar.LENGTH_SHORT).show();
                //Utility.savePicStatus(mDayFlag, getApplicationContext());
                mSetting.putBoolean("picture_status", mDayFlag);
            }else if((((Utility.makeComparison(currentTime, "00:00")>0 && Utility.makeComparison(currentTime, sunriseTime)<0)
                    || (Utility.makeComparison(currentTime, sunsetTime)>0 && Utility.makeComparison(currentTime, "24:00")<0))
                    && mDayFlag == true)){
                Log.d("现在应该是夜间", "");
                mDayFlag = !mDayFlag;
                Glide.with(this).load(R.mipmap.night).into(mMainImage);
                Snackbar.make(mCoordinatorLayout
                        , "                                    自动切换为夜间背景", Snackbar.LENGTH_SHORT).show();
                mSetting.putBoolean("picture_status", mDayFlag);
            }

        }
        mNowTime = System.currentTimeMillis();
        mLastRefreshTime = mSetting.getLong("refresh_time", System.currentTimeMillis());
        Log.d("距离上次刷新", String.valueOf((mNowTime - mLastRefreshTime) / 1000) + "秒");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(resultCode != RESULT_OK)return;
        if (requestCode == REQUEST_FOR_PLACE){
            mCountyCode = data.getStringExtra("countyCode");
            mCountyName = data.getStringExtra("countyName");

            mSetting.putString("county_code", mCountyCode);
            mSetting.putString("county_name", mCountyName);

            Log.d(TAG, mCountyCode);

            mSwipeRefreshLayout.setRefreshing(true);

            updateInfo();
            downloadWeatherCode(mCountyCode);
        }else if (requestCode == REQUEST_FOR_BACKGROUNDUPDATE){

        }

    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    private void downloadWeatherCode(String countyCode){
        if(countyCode == null){
            countyCode = "010101";
        }
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        StringRequestUTF8 request = new StringRequestUTF8(address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] data = response.split("\\|");
                        String weatherCode = data[1];
                        Log.d(TAG, weatherCode);
                        mSetting.putString("weather_code", weatherCode);
                        downloadWeatherInfo(weatherCode);
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error){
                        Log.d(TAG, error.getMessage(), error);
                        mSwipeRefreshLayout.setRefreshing(false);
                        Snackbar.make(mCoordinatorLayout
                                , "                                         请检查网络", Snackbar.LENGTH_SHORT).show();
                    }
                });
        BaseApplication.mQueue.add(request);
    }
    private void downloadWeatherInfo(String weatherCode){
        String address = "https://api.heweather.com/x3/weather?cityid=CN"+ weatherCode +"&key=15fe94dbab3543b19e577cf8e5b0f944";
        JsonObjectRequest request = new JsonObjectRequest(address, null, new Response.Listener<JSONObject>(){
           public void onResponse(JSONObject response){
               Log.d(TAG, response.toString());

               mWeatherTitle = new Gson().fromJson(response.toString(), WeatherTitle.class);

               Log.d(TAG, "" + mWeatherTitle.weatherDetails.get(0).suggestion.sport.txt);

               boolean IsInfoSaved = Utility.saveWeatherInfoToFile(getApplicationContext(), mWeatherTitle, "weather_information");

               Log.d("天气信息是否保存成功:", " "+IsInfoSaved);

               //mCountyName = Utility.getCountyName(getApplicationContext());
               mCountyName = mSetting.getString("county_name", "北京");
               mCollapsingToolbarLayout.setTitle(mCountyName);


               Calendar calendar = Calendar.getInstance();
               SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
               //如果H是大写表示24小时制
               SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
               String updateTimeDate = sdf1.format(calendar.getTime())+" "+sdf2.format(calendar.getTime());

               mSetting.putString("update_time_date", updateTimeDate);

               mListener1.passInfo(mWeatherTitle);
               mListener2.passInfo(mWeatherTitle);
               mListener3.passInfo(mWeatherTitle);

               mLastRefreshTime = System.currentTimeMillis();
               Log.d("刷新时间保存为", new Date(mLastRefreshTime).toString());
               mSetting.putLong("refresh_time", mLastRefreshTime);

               mSwipeRefreshLayout.setRefreshing(false);
           }
        }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){
                Log.e("TAG", error.getMessage(), error);
                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(mCoordinatorLayout, "                                         请检查网络", Snackbar.LENGTH_SHORT).show();
            }
        });
        BaseApplication.mQueue.add(request);
    }

    public interface PassInfoListener{
        void passInfo(WeatherTitle weatherTitle);
    }

    public void onClick(View view){
        switch(view.getId()){
            case R.id.tab0:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tab1:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.tab2:
                mViewPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }
    public void onPageSelected(int position){

    }
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    public void onPageScrollStateChanged(int state) {

    }

    public void onRefresh(){
        mSwipeRefreshLayout.setRefreshing(true);
        downloadWeatherCode(mCountyCode);
    }

    private void updateInfo(){

    }

    @Override
    protected void onDestroy() {
        NightModelManager.getInstance().detach(this);
        super.onDestroy();
    }

    public void clickMode(View v ){
        Log.i("hys","clickMode");
        if (NightModelManager.getInstance().isCurrentNightModel(this)) {
            NightModelManager.getInstance().applyDayModel(this);
        } else {
            NightModelManager.getInstance().applyNightModel(this);
        }
    }
}
