package com.lxzh123.wechatmoment.view.ninegridview;

import android.content.Context;
import android.widget.ImageView;

/**
 * description: 九宫格单元格图片加载器
 * author:      Created by a1239848066 on 2018/5/20.
 */
public interface INineGridImageLoader
{
    void displayNineGridImage(Context context, String url, ImageView imageView, int position);

    void displayNineGridImage(Context context, String url, ImageView imageView,
                              float scale, int position);
}
