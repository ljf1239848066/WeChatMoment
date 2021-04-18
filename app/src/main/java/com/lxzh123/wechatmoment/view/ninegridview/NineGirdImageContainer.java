package com.lxzh123.wechatmoment.view.ninegridview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lxzh123.wechatmoment.R;

/**
 * description: 九宫格单元格图片视图容器
 * author:      Created by lxzh on 2021/4/18.
 */
public class NineGirdImageContainer extends FrameLayout {
    private NineGridImageView mImageView;
    private int mImageWidth, mImageHeight;
    private boolean mAttached;

    public NineGirdImageContainer(Context context) {
        super(context);
        init(context, null);
    }

    public NineGirdImageContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setWillNotDraw(false);
        inflate(context, R.layout.layout_ninegrid_image, this);
        mImageView = (NineGridImageView) findViewById(R.id.img_ninegrid_imagecontainer_content);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mImageWidth = MeasureSpec.getSize(widthMeasureSpec);
        mImageHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mImageWidth, mImageHeight);

        //Measure the size of imageView
        int imgMode = MeasureSpec.EXACTLY;
        int imgWidthSpec = 0;
        int imgHeightSpec = 0;
        imgWidthSpec = MeasureSpec.makeMeasureSpec(mImageWidth, imgMode);
        imgHeightSpec = MeasureSpec.makeMeasureSpec(mImageHeight, imgMode);
        mImageView.measure(imgWidthSpec, imgHeightSpec);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mAttached = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAttached = false;
    }

    public boolean getAttached() {
        return mAttached;
    }

    public int getImageWidth() {
        return mImageWidth;
    }

    public int getImageHeight() {
        return mImageHeight;
    }

    /**
     * Set scantype of imageView
     */
    private void setScanType(ImageView.ScaleType scanType) {
        if (mImageView != null)
            mImageView.setScaleType(scanType);
    }

    /**
     * Get imageView object
     */
    public ImageView getImageView() {
        return mImageView;
    }


}
