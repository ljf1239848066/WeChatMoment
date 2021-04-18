package com.lxzh123.wechatmoment.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * description: 自定义viewpager  优化了事件拦截
 * author:      Created by lxzh on 2021/4/18.
 */
public class AlbumViewPager extends ViewPager {
    public final static String TAG = "AlbumViewPager";

    /**
     * 当前子控件是否处理拖动状态
     */
    private boolean mChildIsBeingDragged = false;

    public void setChildIsBeingDragged(boolean mChildIsBeingDragged) {
        this.mChildIsBeingDragged = mChildIsBeingDragged;
    }

    public AlbumViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (mChildIsBeingDragged)
            return false;
        return super.onInterceptTouchEvent(arg0);
    }
}
