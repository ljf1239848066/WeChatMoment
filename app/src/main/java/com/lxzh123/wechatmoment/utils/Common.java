package com.lxzh123.wechatmoment.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * description: 公告配置数据
 * author:      Created by a1239848066 on 2018/5/20.
 */
public class Common {
    /**
     * 文件读写默认编码类型
     */
    public final static String APP_DEFAULT_FILE_ENCODING = "UTF-8";
    /**
     * 朋友圈状态图片默认加载缩放系数
     */
    public final static float IMG_THUMB_SCALRE = 0.2f;
    /**
     * 朋友圈状态图片最大显示列数
     */
    public final static int TWEET_IMG_GRIDE_MAX_COLUMN=3;

    /**
     * 判断字符串是否是空(null 或者 空字符串)
     * @param str   待判定的字符串对象
     * @return      true: 空 false: 非空
     */
    public static boolean isNullOrEmpty(String str){
        return str==null||str.isEmpty();
    }

    /**
     * 获取size以dp为单位的像素大小
     * @param dpValue dp大小
     * @param context
     * @return 像素大小
     */
    public static int getDisplayDipSize(int dpValue, Context context){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue
                , context.getResources().getDisplayMetrics());
    }
}
