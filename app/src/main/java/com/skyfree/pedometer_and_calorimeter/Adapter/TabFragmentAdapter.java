package com.skyfree.pedometer_and_calorimeter.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.skyfree.pedometer_and_calorimeter.Fragment.ReportFragment;
import com.skyfree.pedometer_and_calorimeter.Fragment.SettingsFragment;
import com.skyfree.pedometer_and_calorimeter.Fragment.TodayFragment;

public class TabFragmentAdapter extends FragmentPagerAdapter {

    int tabCount;

    public TabFragmentAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.tabCount = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                TodayFragment today = new TodayFragment();
                return today;
            case 1:
                ReportFragment report = new ReportFragment();
                return report;
            case 2:
                SettingsFragment settings = new SettingsFragment();
                return settings;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
