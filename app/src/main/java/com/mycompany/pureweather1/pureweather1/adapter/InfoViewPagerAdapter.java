package com.mycompany.pureweather1.pureweather1.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Lenovo on 2016/4/4.
 */
public class InfoViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;
    private List<String> mFragmentTitle;

    public InfoViewPagerAdapter(FragmentManager fm, List<Fragment> list, List<String> fragmentTitle){
        super(fm);
        mList = list;
        mFragmentTitle = fragmentTitle;
    }

    public Fragment getItem(int pos){
        return mList.get(pos);
    }

    public int getCount(){
        return mList.size();
    }

    public CharSequence getPageTitle(int position) {
        return mFragmentTitle.get(position);
    }
}
