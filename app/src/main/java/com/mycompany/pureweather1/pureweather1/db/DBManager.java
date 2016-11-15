package com.mycompany.pureweather1.pureweather1.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mycompany.pureweather1.pureweather1.model.City;
import com.mycompany.pureweather1.pureweather1.model.County;
import com.mycompany.pureweather1.pureweather1.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2016/3/29.
 */
public class DBManager {
    public static final int VERSION = 1;
    public static final String DB_NAME = "pure_weather";

    private static DBManager mDBManager;
    private SQLiteDatabase mDatabase;

    //确保只存在一个mDBManager,一个mDatabase
    private DBManager(Context context){
        WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME, null, VERSION);
        mDatabase = dbHelper.getWritableDatabase();
    }

    public synchronized static DBManager get(Context context){
        if (mDBManager == null){
            mDBManager = new DBManager(context);
        }
        return mDBManager;
    }
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = mDatabase.query("Province", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while(cursor.moveToNext());
        }
        if (cursor != null){
            cursor.close();
        }
        return list;
    }
    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = mDatabase.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)}, null, null, null);
        if(cursor.moveToFirst()){
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
                list.add(city);
            }while(cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }

    public List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = mDatabase.query("County", null, "city_id = ?", new String[]{String.valueOf(cityId)}, null,null,null);
        if(cursor.moveToFirst()){
            do {
                County county = new County();
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(county);
            }while (cursor.moveToNext());
        }
        if(cursor != null){
            cursor.close();
        }
        return list;
    }
    public void saveProvince(Province province){
        ContentValues values = new ContentValues();
        values.put("province_name", province.getProvinceName());
        values.put("province_code", province.getProvinceCode());
        mDatabase.insert("Province", null, values);
    }
    public void saveCity(City city){
        ContentValues values = new ContentValues();
        values.put("city_name", city.getCityName());
        values.put("city_code", city.getCityCode());
        values.put("province_id", city.getProvinceId());
        mDatabase.insert("City", null, values);
    }
    public void saveCounty(County county){
        ContentValues values = new ContentValues();
        values.put("county_name", county.getCountyName());
        values.put("county_code", county.getCountyCode());
        values.put("city_id", county.getCityId());
        mDatabase.insert("County", null, values);
    }
}
