package com.lxzh123.wechatmoment.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * description: 基于 ViewPager 的图片浏览器适配器
 * author:      Created by lxzh on 2021/4/19.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private List<View> viewLists;

    public ViewPagerAdapter(List<View> viewLists) {
        this.viewLists = viewLists;
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = viewLists.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
