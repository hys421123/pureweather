package com.mycompany.pureweather1.pureweather1.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.mycompany.pureweather1.R;

/**
 * Created by Lenovo on 2016/4/22.
 */
public class AboutFragment extends PreferenceFragment {
    private Preference mIntroduction;
    private Preference mVersion;
    private Preference mCheckVersion;
    private Preference mShare;
    private Preference mStar;
    private Preference mEncounrage;
    private Preference mBolg;
    private Preference mGithub;
    private Preference mEmail;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);

        //mIntroduction = findPreference(INTRODUCTION);

    }
}
