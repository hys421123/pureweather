package com.mycompany.pureweather1.pureweather1.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.mycompany.pureweather1.pureweather1.Service.BackGroundUpdateService;
import com.mycompany.pureweather1.pureweather1.db.DBManager;
import com.mycompany.pureweather1.pureweather1.model.City;
import com.mycompany.pureweather1.pureweather1.model.County;
import com.mycompany.pureweather1.pureweather1.model.Province;
import com.mycompany.pureweather1.pureweather1.model.WeatherTitle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Lenovo on 2016/3/29.
 */
public class Utility {
    public synchronized static boolean handleProvincesResponse(DBManager manager, String response){
        String[] provincesAndCodes = response.split(",");
        for(String single : provincesAndCodes){
            String[] pair = single.split("\\|");
            Province province = new Province();
            province.setProvinceName(pair[1]);
            province.setProvinceCode(pair[0]);
            manager.saveProvince(province);
        }
        return true;
    }
    public static boolean handleCitiesResponse(DBManager manager, String response, Province province){
        String[] citiesAndCodes = response.split(",");
        for(String single : citiesAndCodes){
            String[] pair = single.split("\\|");
            City city = new City();
            city.setCityName(pair[1]);
            city.setCityCode(pair[0]);
            city.setProvinceId(province.getId());
            manager.saveCity(city);
        }
        return true;
    }

    public static boolean handleCountiesResponse(DBManager manager, String response, City city){
        String[] countiesAndCodes = response.split(",");
        for(String single : countiesAndCodes){
            String[] pair = single.split("\\|");
            County county = new County();
            county.setCountyName(pair[1]);
            county.setCountyCode(pair[0]);
            county.setCityId(city.getId());
            manager.saveCounty(county);
        }
        return true;
    }
    public static void saveWeatherInfo(WeatherTitle weatherTitle, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("today_weather", weatherTitle.weatherDetails.get(0).now.cond.txt);
        editor.commit();
    }

    /*public static Bitmap decodeRevisedResource(Resources res, int resId
            , int reqWidth, int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options
            , int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
    */

    public static boolean saveWeatherInfoToFile(Context context, Object object, String fileName){
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try{
            fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            return true;
        }catch (Exception e){
            Log.d("文件保存出现错误", "文件保存出错");
            return false;
        }finally {
            if (oos != null){
                try{
                    oos.close();
                }catch (IOException e){

                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e2) {

                }
            }
        }
    }

    public static Serializable getWeatherInfoFromFile(Context context, String fileName){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = context.openFileInput(fileName);
            ois = new ObjectInputStream(fis);
            return (Serializable)ois.readObject();
        }catch (Exception e){
            Log.d("天气信息读取失败", "请检查错误");
        }finally{
            if (fis!=null){
                try{
                    ois.close();
                }catch(Exception e){

                }
            }
            if (fis != null){
                try{
                    fis.close();
                }catch (Exception e){

                }
            }
        }
        return null;
    }
    /*
    public static void saveCountyCode(String countyCode, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("county_code", countyCode);
        editor.commit();
    }
    public static String getCountyCode(Context context){
        String countyCode;
        //String iniCountyCode = "010101";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        countyCode = preferences.getString("county_code", "010101");
        return countyCode;
    }
    public static void saveCountyName(String countyName, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("county_name", countyName);
        editor.commit();
    }
    public static String getCountyName(Context context){
        String countyName;
        //String iniCountyCode = "010101";
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        countyName = preferences.getString("county_name", "北京");
        return countyName;
    }
    public static void saveRefreshTime(long refreshTime, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putLong("refresh_time", refreshTime);
        editor.commit();
    }
    public static long getRefreshTime(Context context){
        long refreshTime;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        refreshTime = preferences.getLong("refresh_time", System.currentTimeMillis());
        return refreshTime;
    }

    public static void savePicStatus(boolean isDayStatus, Context context){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("picture_status", isDayStatus);
        editor.commit();
    }
    public static boolean getPicStatus(Context context){
        boolean status;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        status = preferences.getBoolean("picture_status", true);
        return status;
    }*/


    public static String dayOfWeek(String pTime)  {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();

        try{
            c.setTime(format.parse(pTime));
            int dayForWeek = 0;
            String week = "";
            dayForWeek = c.get(Calendar.DAY_OF_WEEK);
            switch (dayForWeek) {
                case 1:
                    week = "星期日";
                    break;
                case 2:
                    week = "星期一";
                    break;
                case 3:
                    week = "星期二";
                    break;
                case 4:
                    week = "星期三";
                    break;
                case 5:
                    week = "星期四";
                    break;
                case 6:
                    week = "星期五";
                    break;
                case 7:
                    week = "星期六";
                    break;
            }
            return week;
        }catch(Exception e){
            Log.d("日期转换成星期出现错误", "请检查");
        }
        return null;
    }

    public static int makeComparison(String a, String b){
        if(a.charAt(0)<b.charAt(0)){
            return -1;
        } else if (a.charAt(0)>b.charAt(0)){
            return 1;
        }else {
            if (a.charAt(1)<b.charAt(1)){
                return -1;
            }else if (a.charAt(1)>b.charAt(1)){
                return 1;
            }else{
                if (a.charAt(3)<b.charAt(3)){
                    return -1;
                }  else if (a.charAt(3)>b.charAt(3)){
                    return 1;
                }else{
                    if (a.charAt(4)<b.charAt(4)){
                        return -1;
                    }else if (a.charAt(4)>b.charAt(4)){
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    public static void setUpdateService(Context context, boolean setAlarmOn, int intervalTime){
        Intent startServiceIntent = new Intent(context , BackGroundUpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, startServiceIntent, 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);


        if (setAlarmOn){
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), intervalTime, pendingIntent);

            Log.d("开启了后台更新，更新间隔为：",""+intervalTime/1000+"秒");
        }else{
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            Log.d("取消了后台更新", "确认取消");
        }
    }

    public static boolean isUpdateServiceOn(Context context){
        Intent i = new Intent(context, BackGroundUpdateService.class);
        //一个Pending只能登记一个定时器，可检查PendingIntent是否存在，确认定时器激活与否。
        // 传入PendingIntent.FLAG_NO_CREATE，如果PendingIntent不存在，则返回null，而不是创建它。
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pendingIntent != null;
    }
}
