package com.mycompany.pureweather1.pureweather1.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycompany.pureweather1.R;
import com.mycompany.pureweather1.pureweather1.base.BaseApplication;
import com.mycompany.pureweather1.pureweather1.util.Setting;
import com.mycompany.pureweather1.pureweather1.util.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrequencyPickerFragment extends DialogFragment {

    private String[] mFrequenciesList;
    private int[] mFrequenciesType;
    private int[] mUpdateTimes;
    private Setting mSetting;
    private boolean mIsOnUpdate;

    private CoordinatorLayout mCoordinatorLayout;


    public FrequencyPickerFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mFrequenciesList = new String[]{"不进行后台更新", "每1小时", "每3小时", "每6小时", "每12小时", "每24小时"};
        mFrequenciesType = new int[]{0, 1, 2, 3, 4, 5};
        mUpdateTimes = new int[]{
                0,
                60*60*1000,
                //10*1000,
                3*60*60*1000,
                6*60*60*1000,
                12*60*60*1000,
                24*60*60*1000,
        };

        mSetting = Setting.getInstance();
        mCoordinatorLayout = (CoordinatorLayout)getActivity().findViewById(R.id.setting_coord);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new AlertDialog.Builder(getActivity())
                .setTitle("选择频率")
                .setSingleChoiceItems(mFrequenciesList, mFrequenciesType[mSetting.getInt("refresh_frequncy",0)]
                        , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Snackbar.make(getView(), "设置更新频率为 " + mFrequencies[which], Snackbar.LENGTH_SHORT);
                        Log.d("频率选择为：", ""+ mFrequenciesType[which]);
                        mSetting.putInt("refresh_frequncy", mFrequenciesType[which]);
                        Log.d("成功保存更新频率为：", "" + mFrequenciesType[which] + "->" + mFrequenciesList[which]);
                        sendResult(Activity.RESULT_OK, which);
                        setUpdate(which);
                        showSnackBar();
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //showSnackBar();
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, int positon){
        if (getTargetFragment() == null){
            return ;
        }
        Intent data = new Intent();
        data.putExtra("frequency", mFrequenciesList[positon]);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, data);
    }

    private void setUpdate(int position){
        mIsOnUpdate = Utility.isUpdateServiceOn(BaseApplication.mAppContext);
        boolean setAlarm = ! mIsOnUpdate;
        switch(position){
            case 0:
                Utility.setUpdateService(getActivity(), false, 0);
                break;
            case 1:
                Utility.setUpdateService(getActivity(), false, 0);
                Utility.setUpdateService(getActivity(), true, mUpdateTimes[1]);
                break;
            case 2:
                Utility.setUpdateService(getActivity(), false, 0);
                Utility.setUpdateService(getActivity(), true, mUpdateTimes[2]);
                break;
            case 3:
                Utility.setUpdateService(getActivity(), false, 0);
                Utility.setUpdateService(getActivity(), true, mUpdateTimes[3]);
                break;
            case 4:
                Utility.setUpdateService(getActivity(), false, 0);
                Utility.setUpdateService(getActivity(), true, mUpdateTimes[4]);
                break;
            case 5:
                Utility.setUpdateService(getActivity(), false, 0);
                Utility.setUpdateService(getActivity(), true, mUpdateTimes[5]);
                break;
        }
    }

    private void showSnackBar(){
        Snackbar.make(mCoordinatorLayout
                , "更新频率设置为 "+ mFrequenciesList[mSetting.getInt("refresh_frequncy",0)]
                ,Snackbar.LENGTH_SHORT)
        .show();
    }

}
