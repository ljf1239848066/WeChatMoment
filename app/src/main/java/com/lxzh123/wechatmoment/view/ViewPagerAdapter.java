package com.lxzh123.wechatmoment.view;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by a1239848066 on 2018/6/9.
 */

public class ViewPagerAdapter extends PagerAdapter{

    private List<View> viewLists;

    public ViewPagerAdapter(List<View> viewLists){
        this.viewLists=viewLists;
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=viewLists.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
