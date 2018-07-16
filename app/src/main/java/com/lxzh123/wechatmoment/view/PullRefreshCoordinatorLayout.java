package com.lxzh123.wechatmoment.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ListView;

import com.lxzh123.wechatmoment.R;
import com.lxzh123.wechatmoment.utils.ViewUtil;

/**
 * description: 自定义上拉加载下拉刷新View
 * TODO:        待完善功能
 * author:      Created by a1239848066 on 2018/5/20.
 */
public class PullRefreshCoordinatorLayout extends ListView {

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    private ImageView circleProgresView;

    /**
     * pull down distance scale ratio
     */
    private final static int RATIO = 3;
    private int startPositionY;
    private int refreshEnableDistance = 0;
    private int actionDownPosition;
    private boolean isRefreshEnable;

    public PullRefreshCoordinatorLayout(Context context) {
        super(context);
        init(context,null, 0);
    }

    public PullRefreshCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs, 0);
    }

    public PullRefreshCoordinatorLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {


        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();

        ViewUtil vu=new ViewUtil(this);
        startPositionY= vu.getTop();
        isRefreshEnable=false;

        circleProgresView=new ImageView(context);
        circleProgresView.setImageResource(R.drawable.find_friends);
        CoordinatorLayout.LayoutParams lp=new CoordinatorLayout.LayoutParams(50,50);
        lp.topMargin=50;
        lp.leftMargin=50;
        this.addView(circleProgresView,lp);
    }

    private void invalidateTextPaintAndMeasurements() {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(isRefreshEnable){
            switch(ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    actionDownPosition=(int)ev.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    actionDownPosition=-1;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int tmpY=(int)ev.getY();
                    int deltaY=(tmpY-actionDownPosition)/RATIO;
                    if(deltaY>refreshEnableDistance){

                    }
                    break;
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;
    }

    /**
     * Sets the minimum distance to enable refresh status, default value is 0
     *
     * @param distance the minimum distance to use.
     */
    public void setRefreshEnableDistance(int distance){
        if(distance<0){
            return;
        }
        this.refreshEnableDistance=distance;
    }
}
