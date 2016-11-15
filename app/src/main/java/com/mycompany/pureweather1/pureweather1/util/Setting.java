package com.mycompany.pureweather1.pureweather1.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mycompany.pureweather1.pureweather1.base.BaseApplication;

/**
 * Created by Lenovo on 2016/4/29.
 */
public class Setting {
    private static Setting sSetting;
    private SharedPreferences mPrefs;

    public static Setting getInstance(){
        if(sSetting == null){
            sSetting = new Setting(BaseApplication.mAppContext);
        }
        return sSetting;
    }

    private Setting(Context context){
        mPrefs = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
    }

    public Setting putBoolean(String key, boolean value) {
        mPrefs.edit().putBoolean(key, value).apply();
        return this;
    }
    public boolean getBoolean(String key, boolean def) {
        return mPrefs.getBoolean(key, def);
    }


    public Setting putInt(String key, int value) {
        mPrefs.edit().putInt(key, value).apply();
        return this;
    }
    public int getInt(String key, int defValue) {

        return mPrefs.getInt(key, defValue);
    }


    public Setting putString(String key, String value) {
        mPrefs.edit().putString(key, value).apply();
        return this;
    }
    public String getString(String key, String defValue) {

        return mPrefs.getString(key, defValue);
    }

    public Setting putLong(String key, long value) {
        mPrefs.edit().putLong(key, value).apply();
        return this;
    }
    public Long getLong(String key, Long defValue) {
        return mPrefs.getLong(key, defValue);
    }
}
