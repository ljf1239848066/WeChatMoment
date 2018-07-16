package com.lxzh123.wechatmoment.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


/**
 * Created by a1239848066 on 2018/5/18.
 */

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    public List<Fragment> fragments;

    public CustomFragmentPagerAdapter(FragmentManager fm, List<Fragment> fms) {
        super(fm);
        this.fragments=fms;
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }
}
