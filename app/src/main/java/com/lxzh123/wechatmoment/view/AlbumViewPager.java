package com.lxzh123.wechatmoment.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * description: 自定义viewpager  优化了事件拦截
 * author:      Created by a1239848066 on 2018/5/20.
 */
public class AlbumViewPager extends ViewPager{
	public final static String TAG="AlbumViewPager";

	/**  当前子控件是否处理拖动状态  */
	private boolean mChildIsBeingDragged=false;

	public void setmChildIsBeingDragged(boolean mChildIsBeingDragged) {
		this.mChildIsBeingDragged = mChildIsBeingDragged;
	}

	public AlbumViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if(mChildIsBeingDragged)
			return false;
		return super.onInterceptTouchEvent(arg0);
	}
}
