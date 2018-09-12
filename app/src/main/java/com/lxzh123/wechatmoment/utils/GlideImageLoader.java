package com.lxzh123.wechatmoment.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lxzh123.wechatmoment.R;
import com.lxzh123.wechatmoment.view.ninegridview.INineGridImageLoader;

/**
 * description: Glide图片加载器
 * author:      Created by a1239848066 on 2018/5/20.
 */
public class GlideImageLoader implements INineGridImageLoader
{
    public static int[] IMG_HOLDER_IDS={R.drawable.default_img_1,R.drawable.default_img_2,
            R.drawable.default_img_3,R.drawable.default_img_4,R.drawable.default_img_5,
            R.drawable.default_img_6,R.drawable.default_img_7,R.drawable.default_img_8,
            R.drawable.default_img_9};
    @Override
    public void displayNineGridImage(Context context, String url, ImageView imageView, int position)
    {
        Glide.with(context).load(url)
                .placeholder(IMG_HOLDER_IDS[position])
                .into(imageView);
    }

    @Override
    public void displayNineGridImage(Context context, String url, ImageView imageView,
                                     float scale, int position)
    {
        Glide.with(context).load(url)
                .placeholder(IMG_HOLDER_IDS[position])
                .thumbnail(scale)
                .into(imageView);
    }
}
