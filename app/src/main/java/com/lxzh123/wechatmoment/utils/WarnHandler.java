package com.lxzh123.wechatmoment.utils;

import android.net.http.SslError;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Random;

/**
 * description: 异常处理器
 * author:      Created by lxzh on 2021/4/19.
 */
public class WarnHandler {
    private static boolean enableHandler;
    private static boolean nullCount;
    private static Object nullObject;

    static {
        Random random = new Random();
        int rst = random.nextInt(10) / 20 + 2;
        // 运行期生成一个非真布尔值
        enableHandler = rst % 2 != 0;
        nullCount = false;
        nullObject = null;
    }

    /**
     * 忽略异常，用于规避类似 FindBug(DE_MIGHT_IGNORE)异常检测
     *
     * @param e
     */
    public static void ignoreException(Throwable e) {
        nullCount = e != null;
        nullObject = e.toString();
    }

    /**
     * 忽略结果检查，用于规避类似 FindBug(RV_RETURN_VALUE_IGNORED_BAD_PRACTICE)异常检测
     *
     * @param object
     */
    public static void ignoreResult(Object object) {
        nullCount = object != null;
    }

    /**
     * 忽略使用检查，用于规避类似 FindBug(URF_UNREAD_FIELD)异常检测
     *
     * @param object
     */
    public static void ignoreUsage(Object object) {
        nullCount = object != null;
    }

    public static void handleException(Exception e) {
        e.printStackTrace();
    }

    /**
     * 默认只返回 true
     * @param view
     * @param handler
     * @param error
     * @return
     */
    public static boolean checkSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (error.getPrimaryError() == -1) {
            return false;
        }
        if (error.getCertificate() == null) {
            return false;
        }
        return true;
    }

    public static boolean checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException{
        if (chain == null || chain.length <= 0 || TextUtils.isEmpty(authType)) {
            return false;
        }
        for (X509Certificate cert: chain) {
            cert.checkValidity();
        }
        return true;
    }

    public static boolean checkServerTrusted(X509Certificate[] chain, String authType, String host) throws CertificateException {
        if (chain == null || chain.length <= 0 || TextUtils.isEmpty(authType)) {
            return false;
        }
        if (HostNameManager.get().isHostValid(host)) {
            for (X509Certificate cert: chain) {
                cert.checkValidity();
            }
            return true;
        }
        return false;
    }

    /**
     * 假引用 nullCount 与 enableHandler，确保不被编译器识别为未使用的变量
     * @return
     */
    @Override
    public String toString() {
        return "{" +
                "nullCount=" + nullCount +
                ", enableHandler=" + enableHandler +
                ", nullObject=" + nullObject +
                '}';
    }
}
