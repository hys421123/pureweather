package com.mycompany.pureweather1.pureweather1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo on 2016/3/27.
 */
public class WeatherOpenHelper extends SQLiteOpenHelper {

    //表名大写，id integer,其余text
    public static final String CREATE_PROVINCE_DATA = "create table Province("
            + "id integer primary key autoincrement,"
            + "province_name text,"
            + "province_code text)";
    public static final String CREATE_CITY_DATA = "create table City("
            + "id integer primary key autoincrement,"
            + "city_name text,"
            + "city_code text,"
            + "province_id integer)";
    public static final String CREATE_COUNTY_DATA = "create table County("
            + "id integer primary key autoincrement,"
            + "county_name text,"
            + "county_code text,"
            + "city_id integer)";

    public WeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_PROVINCE_DATA);
        db.execSQL(CREATE_CITY_DATA);
        db.execSQL(CREATE_COUNTY_DATA);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
