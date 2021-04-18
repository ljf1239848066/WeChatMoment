package com.lxzh123.wechatmoment.utils;

import android.os.Build;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * description: 网络请求类
 * author:      Created by lxzh on 2021/4/18.
 */
public class HttpUtils {

    /**
     * 同步get请求指定url内容
     *
     * @param url    请求的url
     * @return 指定url的内容
     * @throws IOException
     */
    public static String SyncRequestGet(String url, int timeout) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient.Builder().callTimeout(timeout, TimeUnit.MILLISECONDS).build();
        if (Build.VERSION.SDK_INT >= 19) {
            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        } else {
            try {
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException ioe) {
                throw ioe;
            }
        }
    }


}
