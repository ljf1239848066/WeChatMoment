package com.lxzh123.wechatmoment.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lxzh123.wechatmoment.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a1239848066 on 2018/6/9.
 */

public class ViewPagerIndicator implements ViewPager.OnPageChangeListener {
    private final int IMG_INDICATOR_NORMAL= R.drawable.indicator_normal;
    private final int IMG_INDICATOR_SELECTED=R.drawable.indicator_selected;
    private List<ImageView> dotViewLists;
    private int preIdx=-1;
    private int curIdx=-1;
    private int count;

    public ViewPagerIndicator(Context context, ViewPager viewPager, LinearLayout linearLayout,
                              int count,int size){
        dotViewLists=new ArrayList<>();
        this.count=count;
        for(int i=0;i<count;i++){
            ImageView imageView=new ImageView(context);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            params.leftMargin=5;
            params.rightMargin=5;
            params.width=size;
            params.height=size;
            if(i==0){
                imageView.setBackgroundResource(IMG_INDICATOR_SELECTED);
                curIdx=0;
            }else{
                imageView.setBackgroundResource(IMG_INDICATOR_NORMAL);
            }
            linearLayout.addView(imageView,params);
            dotViewLists.add(imageView);
        }
    }

    public void setCurrentItem(int index){
        if(index<0||index>count){
            return;
        }else {
            preIdx=curIdx;
            curIdx=index%count;
            dotViewLists.get(curIdx).setBackgroundResource(IMG_INDICATOR_SELECTED);
            dotViewLists.get(preIdx).setBackgroundResource(IMG_INDICATOR_NORMAL);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurrentItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

