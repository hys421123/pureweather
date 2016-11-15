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
import com.mycompany.pureweather1.pureweather1.util.Setting;

/**
 * A simple {@link Fragment} subclass.
 */
public class BackGroundPickerFragment extends DialogFragment {

    private String[] mChoiceList;
    private int[] mChoiceType;
    private Setting mSetting;
    private CoordinatorLayout mCoordinatorLayout;

    public BackGroundPickerFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mChoiceList = new String[]{"白天","夜间"};
        mChoiceType = new int[]{0, 1};

        mSetting = Setting.getInstance();
        mCoordinatorLayout = (CoordinatorLayout)getActivity().findViewById(R.id.setting_coord);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new AlertDialog.Builder(getActivity())
                .setTitle("选择背景图片")
                .setSingleChoiceItems(mChoiceList, mChoiceType[mSetting.getInt("background_set", 0)]
                        , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Snackbar.make(getView(), "设置更新频率为 " + mFrequencies[which], Snackbar.LENGTH_SHORT);
                        Log.d("背景选择为：", "" + mChoiceList[which]);
                        mSetting.putInt("background_set", mChoiceType[which]);
                        Log.d("成功保存背景为：", "" + mChoiceType[which] + "->" + mChoiceList[which]);
                        sendResult(Activity.RESULT_OK, which);
                        //setUpdate(which);
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
        data.putExtra("background", mChoiceList[positon]);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, data);
    }

    private void showSnackBar(){
        Snackbar.make(mCoordinatorLayout
                , "设置背景图片为 " + mChoiceList[mSetting.getInt("background_set", 0)]
                , Snackbar.LENGTH_SHORT)
                .show();
    }
}
