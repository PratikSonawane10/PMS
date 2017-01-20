package com.pms.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pms.TabAttendance;
import com.pms.TabIncentive;
import com.pms.TabShope;


public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabAttendance tab1 = new TabAttendance();
                return tab1;
            case 1:
                TabShope tab2 = new TabShope();
                return tab2;
            case 2:
                TabIncentive tab3 = new TabIncentive();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}