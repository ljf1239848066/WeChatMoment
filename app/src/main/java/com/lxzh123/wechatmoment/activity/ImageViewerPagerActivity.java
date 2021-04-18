package com.lxzh123.wechatmoment.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.lxzh123.wechatmoment.R;
import com.lxzh123.wechatmoment.utils.Common;
import com.lxzh123.wechatmoment.view.AlbumViewPager;
import com.lxzh123.wechatmoment.view.MatrixImageView;
import com.lxzh123.wechatmoment.adapter.ViewPagerAdapter;
import com.lxzh123.wechatmoment.view.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 微信朋友圈图片查看器
 * author:      Created by lxzh on 2021/4/18.
 */
public class ImageViewerPagerActivity extends AppCompatActivity implements
        MatrixImageView.OnSingleTapListener, MatrixImageView.OnMovingListener {

    private final static String TAG = "ImageViewerPager";
    public final static String EXTRA_IMAGES_IDS_TAG = "EXTRA_IMAGES_IDS_TAG";
    public final static String EXTRA_SELECTED_IMAGE_IDX_TAG = "EXTRA_SELECTED_IMAGE_IDX_TAG";

    private AlbumViewPager viewPager;
    private List<View> viewLists;
    private int sourceId;
    private Bitmap curImgMap;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_viewer_pager);
        viewPager = (AlbumViewPager) findViewById(R.id.vp_img_viewer);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_indicator);

        int idx = getIntent().getIntExtra(EXTRA_SELECTED_IMAGE_IDX_TAG, 0);
        int[] imgIds = getIntent().getIntArrayExtra(EXTRA_IMAGES_IDS_TAG);
        int count = imgIds.length;
        Log.i(TAG, "onCreate,count=" + count + ",idx=" + idx);
        if (idx >= 0 && idx < count) {
            sourceId = imgIds[idx];

            WindowManager wm = getWindowManager();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;

            viewLists = new ArrayList<>(count);
//            LayoutInflater layoutInflater=getLayoutInflater();
            for (int i = 0; i < count; i++) {
//                View view=layoutInflater.inflate(R.layout.image_viewer_item,null);
                View view = View.inflate(viewPager.getContext(), R.layout.image_viewer_item, null);
                MatrixImageView imageView = view.findViewById(R.id.iv_tweet_image);
//                imageView.setImageResource(imgIds[i]);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgIds[i]);
                imageView.setImageBitmap(bitmap);
//                imageView.setOnTouchListener(new MulitPointTouchListener());
                imageView.setOnMovingListener(this);
                imageView.setOnSingleTapListener(this);
                viewLists.add(view);
            }
            ViewPagerAdapter adapter = new ViewPagerAdapter(viewLists);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(idx, true);
            if (count > 1) {
                ViewPagerIndicator indicator = new ViewPagerIndicator(this, viewPager, linearLayout, count,
                        Common.getDisplayDipSize(20, this));
                viewPager.addOnPageChangeListener(indicator);
                indicator.setCurrentItem(idx);
            }

        } else {
            this.finish();
        }
    }

    @Override
    public void startDrag() {
        viewPager.setChildIsBeingDragged(true);
    }


    @Override
    public void stopDrag() {
        viewPager.setChildIsBeingDragged(false);
    }

    @Override
    public void onSingleTap() {
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        curImgMap = BitmapFactory.decodeResource(getResources(), sourceId);
        int picWight = curImgMap.getWidth();
        int picHeight = curImgMap.getWidth();
        int imgShowHeight;
        int imgShowWidth;
        if (picHeight / picWight > screenHeight / screenWidth) {
            imgShowHeight = screenHeight;
            imgShowWidth = (int) (imgShowHeight * picWight * 1.0 / picHeight);

        }
    }
}
