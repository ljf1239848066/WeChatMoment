package com.lxzh123.wechatmoment.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import com.lxzh123.wechatmoment.holder.AppHolder;

import java.io.File;

/**
 * 内存路径获取类
 *
 * @author geetest 谷闹年
 * @date 2019/3/13
 */
public class SdUtils {


    /**
     * 在某些机型上，这个方法可能会崩溃 所以加了一个判断
     * 偶现
     *
     * @return 内存是否可用
     */
    public static boolean isMounted() {
        try {
            return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否有 SD 卡读写权限
     *
     * @param context
     * @return 是否有 SD 卡读写权限
     */
    public static boolean hasSDPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 内存路径
     *
     * @return 内存地址
     */
    public static String getDirPath() {
        Context context = AppHolder.getContext();
        File dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT || hasSDPermission(context)) {
            dir = context.getExternalFilesDir("");
            if (dir == null) {
                dir = context.getFilesDir();
            }
        } else {
            dir = context.getFilesDir();
        }
        return dir.getAbsolutePath();
    }

    public static boolean needCheckMounted() {
        Context context = AppHolder.getContext();
        if (context == null) {
            return true;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            return context.getExternalFilesDir("") != null;
        } else {
            return hasSDPermission(context);
        }
    }
}
