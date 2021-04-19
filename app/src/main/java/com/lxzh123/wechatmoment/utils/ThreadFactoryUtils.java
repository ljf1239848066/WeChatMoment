package com.lxzh123.wechatmoment.utils;

import androidx.annotation.NonNull;

import java.util.UUID;
import java.util.concurrent.ThreadFactory;

/**
 * description: 线程工厂类，生成以 Lxzh 开头+3位随机UUID的线程名称的线程
 * author:      Created by lxzh on 2021/4/19.
 */
public class ThreadFactoryUtils {

    private volatile static ThreadFactory threadFactory;

    public static ThreadFactory get() {
        if (threadFactory == null) {
            synchronized (ThreadFactoryUtils.class) {
                if (threadFactory == null) {
                    threadFactory = new ThreadFactory() {
                        /**
                         * 设置线程名字
                         */
                        @Override
                        public Thread newThread(@NonNull Runnable r) {
                            Thread thread = new Thread(r);
                            thread.setName("Lxzh" + UUID.randomUUID().toString().substring(0, 3));
                            // 为 SDK 所以子线程统一绑定异常处理器，忽略子线程异常
                            thread.setUncaughtExceptionHandler(ThreadExceptionHandler.getUncaughtExceptionHandler());
                            return thread;
                        }
                    };
                }
            }
        }
        return threadFactory;
    }
}
