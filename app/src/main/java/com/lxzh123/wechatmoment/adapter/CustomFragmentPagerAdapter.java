package com.lxzh123.wechatmoment.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * description: 基于 Fragment 切换页面的 ViewPager 页面适配器
 * author:      Created by lxzh on 2021/4/19.
 */
public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    public List<Fragment> fragments;

    public CustomFragmentPagerAdapter(FragmentManager fm, List<Fragment> fms) {
        super(fm);
        this.fragments = fms;
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
