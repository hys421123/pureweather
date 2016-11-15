package com.mycompany.pureweather1.pureweather1.base;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import cn.like.nightmodel.NightModelManager;

/**
 * Created by Lenovo on 2016/4/29.
 */
public class BaseApplication extends Application {
    public static Context mAppContext = null;
    public static RequestQueue mQueue;
    public void onCreate(){
        super.onCreate();
        NightModelManager.getInstance().init(this);
        mAppContext = getApplicationContext();
        mQueue = Volley.newRequestQueue(mAppContext);
    }
}
