package com.lxzh123.wechatmoment.view.ninegridview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.lxzh123.wechatmoment.R;
import com.lxzh123.wechatmoment.utils.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 九宫格GridView
 * author:      Created by a1239848066 on 2018/5/20.
 */
public class NineGridView extends ViewGroup
{
    private final static String TAG="NineGridView";
    //Size of imageview while there has only one image
    private int mSingleImageSize = 100;
    //Aspect ratio of only one imageview
    private float mSingleImageRatio = 1.0f;
    //Size of space
    private int mSpaceSize = 3;
    //Width and height of every imageview(more than one image)
    private int mImageWidth, mImageHeight;
    //column count
    private int mColumnCount = 3;
    //raw count,depends on column count
    private int mRawCount;
    //interface of imageloader
    private INineGridImageLoader mImageLoader;
    //image datas
    private List<NineGridBean> mDataList = new ArrayList<>();
    //child view click listener
    private onItemClickListener mListener;
    //Maximum of image
    private int mMaxNum = 9;
    //layout max width
    private int mLayoutMaxWidth;

    public NineGridView(Context context)
    {
        super(context);
        initParams(context, null);
    }

    public NineGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initParams(context, attrs);
    }

    private void initParams(Context context, AttributeSet attrs)
    {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mSingleImageSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mSingleImageSize, dm);
        mSpaceSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mSpaceSize, dm);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NineGridView);
        if (ta != null)
        {
            int count = ta.getIndexCount();
            for (int i = 0; i < count; i++)
            {
                int index = ta.getIndex(i);
                if (index == R.styleable.NineGridView_sapce_size)
                    mSpaceSize = ta.getDimensionPixelSize(index, mSpaceSize);
                else if (index == R.styleable.NineGridView_single_image_ratio)
                    mSingleImageRatio = ta.getFloat(index, mSingleImageRatio);
                else if (index == R.styleable.NineGridView_single_image_size)
                    mSingleImageSize = ta.getDimensionPixelSize(index, mSingleImageSize);
                else if (index == R.styleable.NineGridView_column_count)
                    mColumnCount = ta.getInteger(index, mColumnCount);
                else if (index == R.styleable.NineGridView_max_num)
                    mMaxNum = ta.getInteger(index, mMaxNum);
            }
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int resWidth = 0, resHeight = 0;

        //Measure width
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        if(measureWidth>mLayoutMaxWidth&&mLayoutMaxWidth>0){
            measureWidth=mLayoutMaxWidth;
        }

        //get available width
        int totalWidth = measureWidth - getPaddingLeft() - getPaddingRight();

        //If is non-edit mode,the size of childview depends on data size
        int dataCount = mDataList.size();
        if (mDataList != null && dataCount > 0)
        {
            if (dataCount == 1)
            {
                totalWidth = (totalWidth-mSpaceSize*2)/3*2+mSpaceSize;
                mImageWidth = totalWidth;
                mImageHeight = (int) (mImageWidth / mSingleImageRatio);

//                mImageWidth = mSingleImageSize > totalWidth ? totalWidth : mSingleImageSize;
//                mImageHeight = (int) (mImageWidth / mSingleImageRatio);
//                //Resize single imageview area size,not allowed to exceed the maximum display range
//                if (mImageHeight > mSingleImageSize)
//                {
//                    float ratio = mSingleImageSize * 1.0f / mImageHeight;
//                    mImageWidth = (int) (mImageWidth * ratio);
//                    mImageHeight = mSingleImageSize;
//                }
                resWidth = mImageWidth + getPaddingLeft() + getPaddingRight();
                resHeight = mImageHeight + getPaddingTop() + getPaddingBottom();
            } else
            {
                mImageWidth = mImageHeight = (totalWidth - (mColumnCount - 1) * mSpaceSize) / mColumnCount;
                if (dataCount < mColumnCount)
                    resWidth = mImageWidth * dataCount + (dataCount - 1) * mSpaceSize + getPaddingLeft() + getPaddingRight();
                else
                    resWidth = mImageWidth * mColumnCount + (mColumnCount - 1) * mSpaceSize + getPaddingLeft() + getPaddingRight();
                resHeight = mImageHeight * mRawCount + (mRawCount - 1) * mSpaceSize + getPaddingTop() + getPaddingBottom();
            }
        }

        setMeasuredDimension(resWidth, resHeight);

        //Measure child view size
        int childrenCount = getChildCount();
        for (int index = 0; index < childrenCount; index++)
        {
            View childView = getChildAt(index);
            int childWidth = mImageWidth;
            int childHeight = mImageHeight;
            int childMode = MeasureSpec.EXACTLY;
            int childWidthSpec = MeasureSpec.makeMeasureSpec(childWidth, childMode);
            int childHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, childMode);
            childView.measure(childWidthSpec, childHeightSpec);
        }
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3)
    {
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        if(childCount==1){
            int left = paddingLeft;
            int top = paddingTop;
            int right = left + mImageWidth;
            int bottom = top + mImageHeight;
            Log.i(TAG,String.format("i=%d,(%d,%d,%d,%d)",0,left,top,right,bottom));
            getChildAt(0).layout(left, top, right, bottom);
        }
        else if(childCount==4){
            for (int index = 0; index < childCount; index++)
            {
                View childrenView = getChildAt(index);
                int tmpIdx=index;
                if(index>=2){
                    tmpIdx++;
                }
                int rowNum = tmpIdx / mColumnCount;
                int columnNum = tmpIdx % mColumnCount;
                int left = (mImageWidth + mSpaceSize) * columnNum + paddingLeft;
                int top = (mImageHeight + mSpaceSize) * rowNum + paddingTop;
                int right = left + mImageWidth;
                int bottom = top + mImageHeight;
                Log.i(TAG,String.format("i=%d,(%d,%d,%d,%d)",index,left,top,right,bottom));
                childrenView.layout(left, top, right, bottom);
            }
        }
        else{
            for (int index = 0; index < childCount; index++)
            {
                View childrenView = getChildAt(index);
                int rowNum = index / mColumnCount;
                int columnNum = index % mColumnCount;
                int left = (mImageWidth + mSpaceSize) * columnNum + paddingLeft;
                int top = (mImageHeight + mSpaceSize) * rowNum + paddingTop;
                int right = left + mImageWidth;
                int bottom = top + mImageHeight;
                childrenView.layout(left, top, right, bottom);
            }
        }
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        setDataList(null);
    }

    /**
     * Set data source
     */
    public void setDataList(List<NineGridBean> dataList)
    {
        mDataList.clear();
        //Not allowed to exceed the maximum number
        if (dataList != null && dataList.size() > 0)
        {
            if (dataList.size() <= mMaxNum)
                mDataList.addAll(dataList);
            else
                mDataList.addAll(dataList.subList(0, mMaxNum - 1));
        }
        clearAllViews();
        calRawAndColumn();
        initChildViews();
        requestLayout();
    }

    /**
     * Add data source
     */
    public void addDataList(List<NineGridBean> dataList)
    {
        if (mDataList.size() >= mMaxNum)
            return;
        //Not allowed to exceed the maximum number
        int cha = mMaxNum - mDataList.size();
        if (dataList.size() <= cha)
            mDataList.addAll(dataList);
        else
            mDataList.addAll(dataList.subList(0, cha - 1));

        clearAllViews();
        calRawAndColumn();
        initChildViews();
        requestLayout();
    }

    //calculate the count of raw and column
    private void calRawAndColumn()
    {
        int childSize = mDataList.size();
        //Increase the data size to display plus button in edit mode

        //calculate the raw count
        if (childSize == 0)
        {
            mRawCount = 0;
        } else if (childSize <= mColumnCount)
        {
            mRawCount = 1;
        } else
        {
            if (childSize % mColumnCount == 0)
                mRawCount = childSize / mColumnCount;
            else
                mRawCount = childSize / mColumnCount + 1;
        }
    }

    //Initialize child view
    private void initChildViews() {
        //add image container
        final int dataSize = mDataList.size();
        for (int i = 0; i < dataSize; i++) {
            final NineGridBean gridBean = mDataList.get(i);
            final NineGirdImageContainer imageContainer = new NineGirdImageContainer(getContext());
            final int position = i;
            imageContainer.getImageView().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null)
                        mListener.onNineGirdItemClick(position, gridBean, imageContainer);
                }
            });
            addView(imageContainer, position);

            // fix the problem of View.post() method which will not excute
            // when the view has not attached to the window
            // ref: https://blog.csdn.net/xavier__s/article/details/50260981
            AttachImageRunnable runnable=new AttachImageRunnable(
                    imageContainer,gridBean,position,getContext());
            if(imageContainer.getAttached()){
                imageContainer.post(runnable);
            }else{
                Handler handler=new Handler();
                handler.post(runnable);
            }
        }
        calRawAndColumn();
        requestLayout();
    }

    private class AttachImageRunnable implements Runnable{

        private NineGirdImageContainer imageContainer;
        private NineGridBean gridBean;
        private int position;
        private Context context;

        public AttachImageRunnable(NineGirdImageContainer imageContainer, NineGridBean gridBean,
                                   int position, Context context) {
            this.imageContainer = imageContainer;
            this.gridBean = gridBean;
            this.position = position;
            this.context = context;
        }

        @Override
        public void run() {
            if (mImageLoader != null) {
                if (imageContainer.getImageWidth() != 0 && imageContainer.getImageWidth() != 0)
                    mImageLoader.displayNineGridImage(getContext(), gridBean.getThumbUrl(),
                            imageContainer.getImageView(), Common.IMG_THUMB_SCALRE, position);
                else
                    mImageLoader.displayNineGridImage(getContext(), gridBean.getThumbUrl(),
                            imageContainer.getImageView(), position);

            } else {
                Log.w("NineGridView", "Can not display the image of NineGridView, you'd better set a imageloader!!!!");
            }
        }
    }

    /**
     * Set up imageloader
     */
    public void setImageLoader(INineGridImageLoader loader)
    {
        this.mImageLoader = loader;
    }

    /**
     * Whether has set imageloader
     * @return
     */
    public boolean hasImageLoader(){
        return this.mImageLoader!=null;
    }

    /**
     * Set column count
     */
    public void setColumnCount(int columnCount)
    {
        this.mColumnCount = columnCount;
        calRawAndColumn();
        requestLayout();
    }

    /**
     * Set the maximum number
     */
    public void setMaxNum(int maxNum)
    {
        this.mMaxNum = maxNum;
    }

    /**
     * Set the space size, dip unit
     */
    public void setSpcaeSize(int dpValue)
    {
        this.mSpaceSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue
                , getContext().getResources().getDisplayMetrics());
    }

    /**
     * Set the size of imageview while there has only one image, dip unit
     */
    public void setSingleImageSize(int dpValue)
    {
        this.mSingleImageSize = Common.getDisplayDipSize(dpValue,getContext());
    }

    /**
     * set the layout max width, dip unit
     * @param maxWidth
     */
    public void setLayoutMaxWidth(int maxWidth){
        this.mLayoutMaxWidth=(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, maxWidth
                , getContext().getResources().getDisplayMetrics());
    }

    /**
     * Set the aspect ratio of only one imageview
     */
    public void setSingleImageRatio(float ratio)
    {
        this.mSingleImageRatio = ratio;
    }

    //clear all views
    private void clearAllViews()
    {
        removeAllViews();
    }

    /**
     * Get data source
     */
    public List<NineGridBean> getDataList()
    {
        return mDataList;
    }

    /**
     * Get data source size
     * @return
     */
    public int getDataSize(){return mDataList.size();}

    /**
     * Set up child view click listener
     */
    public void setOnItemClickListener(onItemClickListener l)
    {
        this.mListener = l;
    }

    public interface onItemClickListener
    {
        /**
         * Callback when image be clicked
         *
         * @param position       position,started with 0
         * @param gridBean       data of image be clicked
         * @param imageContainer image container of image be clicked
         */
        void onNineGirdItemClick(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer);

        /**
         * Callback when one image be deleted
         *
         * @param position       position,started with 0
         * @param gridBean       data of image be clicked
         * @param imageContainer image container of image be clicked
         */
        void onNineGirdItemDeleted(int position, NineGridBean gridBean, NineGirdImageContainer imageContainer);
    }

    /*****************************************************
     * State cache
     ****************************************************************/

    @Override
    protected Parcelable onSaveInstanceState()
    {
        SavedViewState ss = new SavedViewState(super.onSaveInstanceState());
        ss.singleImageSize = mSingleImageSize;
        ss.singleImageRatio = mSingleImageRatio;
        ss.spaceSize = mSpaceSize;
        ss.columnCount = mColumnCount;
        ss.rawCount = mRawCount;
        ss.maxNum = mMaxNum;
        ss.layoutMaxWidth=mLayoutMaxWidth;
        ss.dataList = mDataList;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state)
    {
        if (!(state instanceof SavedViewState))
        {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedViewState ss = (SavedViewState) state;
        super.onRestoreInstanceState(ss);
        mSingleImageSize = ss.singleImageSize;
        mSingleImageRatio = ss.singleImageRatio;
        mSpaceSize = ss.spaceSize;
        mColumnCount = ss.columnCount;
        mRawCount = ss.rawCount;
        mMaxNum = ss.maxNum;
        mLayoutMaxWidth=ss.layoutMaxWidth;
        setDataList(ss.dataList);
    }

    static class SavedViewState extends BaseSavedState
    {
        int singleImageSize;
        float singleImageRatio;
        int spaceSize;
        int columnCount;
        int rawCount;
        int maxNum;
        int layoutMaxWidth;
        boolean isEditMode;
        int icAddMoreResId;
        List<NineGridBean> dataList;

        SavedViewState(Parcelable superState)
        {
            super(superState);
        }

        private SavedViewState(Parcel source)
        {
            super(source);
            singleImageSize = source.readInt();
            singleImageRatio = source.readFloat();
            spaceSize = source.readInt();
            columnCount = source.readInt();
            rawCount = source.readInt();
            maxNum = source.readInt();
            layoutMaxWidth=source.readInt();
            isEditMode = source.readByte() == (byte) 1;
            icAddMoreResId = source.readInt();
            dataList = source.readArrayList(NineGridBean.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel out, int flags)
        {
            super.writeToParcel(out, flags);
            out.writeInt(singleImageSize);
            out.writeFloat(singleImageRatio);
            out.writeInt(spaceSize);
            out.writeInt(columnCount);
            out.writeInt(rawCount);
            out.writeInt(maxNum);
            out.writeInt(layoutMaxWidth);
            out.writeByte(isEditMode ? (byte) 1 : (byte) 0);
            out.writeInt(icAddMoreResId);
            out.writeList(dataList);
        }

        public static final Parcelable.Creator<SavedViewState> CREATOR = new Creator<SavedViewState>()
        {
            @Override
            public SavedViewState createFromParcel(Parcel source)
            {
                return new SavedViewState(source);
            }

            @Override
            public SavedViewState[] newArray(int size)
            {
                return new SavedViewState[size];
            }
        };
    }
}
