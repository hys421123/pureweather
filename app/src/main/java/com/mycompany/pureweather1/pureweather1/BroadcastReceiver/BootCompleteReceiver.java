package com.mycompany.pureweather1.pureweather1.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mycompany.pureweather1.pureweather1.base.BaseApplication;
import com.mycompany.pureweather1.pureweather1.util.Setting;
import com.mycompany.pureweather1.pureweather1.util.Utility;

/**
 * Created by Lenovo on 2016/5/2.
 */
public class BootCompleteReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent){


        int[] type = new int[]{0, 1, 2, 3, 4, 5};
        int[] time = new int[]{
                0,
                60*60*1000,
                3*60*60*1000,
                //10*1000,
                6*60*60*1000,
                12*60*60*1000,
                24*60*60*1000,
        };
        Setting setting = Setting.getInstance();
        int choice = setting.getInt("refresh_frequncy",0);
        Utility.setUpdateService(BaseApplication.mAppContext, false, 0);
        Utility.setUpdateService(BaseApplication.mAppContext, true, time[choice]);

        Log.d("后台服务开机启动成功", "yeah");
        Log.d("后台服务开机启动成功","选择的是："+choice);
    }
}
