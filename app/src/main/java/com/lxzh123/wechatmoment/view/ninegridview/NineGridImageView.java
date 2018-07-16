package com.lxzh123.wechatmoment.view.ninegridview;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * description: 九宫格图片视图
 * author:      Created by a1239848066 on 2018/5/20.
 */
public class NineGridImageView extends AppCompatImageView
{
    private final static String TAG="NineGridImageView";
    public NineGridImageView(Context context)
    {
        super(context);
        setClickable(true);
    }

    public NineGridImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setClickable(true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG,"dispatchTouchEvent:ACTION_DOWN");
                setAlpha(0.7f);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.i(TAG,"dispatchTouchEvent:ACTION_UP|ACTION_CANCEL");
                setAlpha(1.0f);
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
