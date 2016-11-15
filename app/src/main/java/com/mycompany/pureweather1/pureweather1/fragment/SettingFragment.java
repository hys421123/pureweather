package com.mycompany.pureweather1.pureweather1.fragment;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycompany.pureweather1.R;
import com.mycompany.pureweather1.pureweather1.util.Setting;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener{

    //
    // private Preference mChangeBackgroud;
    private Preference mChangeFrequency;
    private String[] mFrequenciesList;
    private String[] mChoiceList;
    private Setting mSetting;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        mSetting = Setting.getInstance();

        mFrequenciesList = new String[]{"不进行后台更新", "每1小时", "每3小时", "每6小时", "每12小时", "每24小时"};
        //mChoiceList = new String[]{"白天", "夜间"};
        //mChangeBackgroud = findPreference("change_background");
        mChangeFrequency = findPreference("change_frequency");
        mChangeFrequency.setSummary(mFrequenciesList[mSetting.getInt("refresh_frequncy",0)]);
        //mChangeBackgroud.setSummary(mChoiceList[mSetting.getInt("background_set", 0)]);

        mChangeFrequency.setOnPreferenceClickListener(this);
        //mChangeBackgroud.setOnPreferenceClickListener(this);
    }

    public boolean onPreferenceClick(Preference preference){
        if (mChangeFrequency == preference){
            FragmentManager fm = getActivity().getFragmentManager();
            FrequencyPickerFragment dialog = new FrequencyPickerFragment();
            dialog.setTargetFragment(SettingFragment.this, 0);
            //识别FragmentManager中唯一的DialogFragment
            dialog.show(fm, "frequency_dialog");
        }/*else if (mChangeBackgroud == preference){
            FragmentManager fm = getActivity().getFragmentManager();
            BackGroundPickerFragment bg = new BackGroundPickerFragment();
            bg.setTargetFragment(SettingFragment.this, 1);
            //识别FragmentManager中唯一的DialogFragment
            bg.show(fm, "background_dialog");
        }*/
        return false;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode!= Activity.RESULT_OK){
            return;
        }
        if (requestCode == 0){
            String frequency = data.getStringExtra("frequency");
            mChangeFrequency.setSummary(frequency);
        }/*else if (requestCode == 1){
            String background = data.getStringExtra("background");
            mChangeBackgroud.setSummary(background);
        }*/
    }
}
