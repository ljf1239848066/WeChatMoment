package com.lxzh123.wechatmoment.view.ninegridview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * description: 九宫格图片视图
 * author:      Created by lxzh on 2021/4/19.
 */
public class NineGridImageView extends AppCompatImageView {
    private final static String TAG = "NineGridImageView";

    public NineGridImageView(Context context) {
        super(context);
        setClickable(true);
    }

    public NineGridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "dispatchTouchEvent:ACTION_DOWN");
                setAlpha(0.7f);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "dispatchTouchEvent:ACTION_UP|ACTION_CANCEL");
                setAlpha(1.0f);
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
