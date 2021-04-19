package com.lxzh123.wechatmoment.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.lxzh123.wechatmoment.common.Constants;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * description: Assets资源文件操作
 * author:      Created by lxzh on 2021/4/19.
 */
public class AssetsUtils {

    /**
     * 从指定Assets资源文件中读取文本字符串
     *
     * @param context
     * @param filePath
     * @return
     */
    public static String ReadAssetTxtContent(Context context, String filePath) {
        AssetManager am = context.getAssets();
        InputStream is = null;
        try {
            // 用AssetManager打开一个输入流，读取数据。
            is = am.open(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 读取一个文件完成，加上换行符（主要是为了观察输出结果，无他）。
        String data = readDataFromInputStream(is);

        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * 从输入流读取内容
     *
     * @param is 输入数据流
     * @return 读取到的内容字符串
     */
    public static String readDataFromInputStream(InputStream is) {
        BufferedInputStream bis = new BufferedInputStream(is);

        String str = "", s = "";

        int c = 0;
        byte[] buf = new byte[64];
        while (true) {
            try {
                c = bis.read(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (c == -1)
                break;
            else {
                try {
                    s = new String(buf, 0, c, Constants.APP_DEFAULT_FILE_ENCODING);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                str += s;
            }
        }

        try {
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }
}

