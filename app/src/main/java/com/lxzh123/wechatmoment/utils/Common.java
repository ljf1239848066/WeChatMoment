package com.lxzh123.wechatmoment.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * description: 公共配置数据
 * author:      Created by lxzh on 2021/4/18.
 */
public class Common {

    /**
     * 判断字符串是否是空(null 或者 空字符串)
     *
     * @param str 待判定的字符串对象
     * @return true: 空 false: 非空
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 获取size以dp为单位的像素大小
     *
     * @param dpValue dp大小
     * @param context
     * @return 像素大小
     */
    public static int getDisplayDipSize(int dpValue, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue
                , context.getResources().getDisplayMetrics());
    }
}
