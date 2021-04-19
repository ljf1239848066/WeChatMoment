package com.lxzh123.wechatmoment.utils;

import com.lxzh123.wechatmoment.common.Constants;

/**
 * description: 线程异常处理器
 * author:      Created by lxzh on 2021/4/19.
 */
public class ThreadExceptionHandler {
    private volatile static Thread.UncaughtExceptionHandler exceptionHandler;
    private static Thread.UncaughtExceptionHandler originHandler;
    private volatile static int exceptionCount = 0;

    public static Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        if (exceptionHandler == null) {
            synchronized (ThreadExceptionHandler.class) {
                if (exceptionHandler == null) {
                    exceptionHandler = new Thread.UncaughtExceptionHandler() {
                        @Override
                        public void uncaughtException(Thread t, Throwable e) {
                            handleSubThreadException(t, e);
                        }
                    };
                }
            }
        }
        return exceptionHandler;
    }

    /**
     * 处理子线程异常
     * @param t
     * @param e
     */
    private static void handleSubThreadException(Thread t, Throwable e) {
        try {
            exceptionCount++;
            String msg = Constants.LOG_TAG + ": Thread:" + t.getName() + " cnt:" +
                    exceptionCount + " thrown uncaughtException:" + e.toString();
            LogUtils.release(msg);
            if (exceptionCount > 5) {
                return;
            }
            // 统一处理未捕获的异常
            e.printStackTrace();
        } catch (Throwable t1) {

        }
    }
}
