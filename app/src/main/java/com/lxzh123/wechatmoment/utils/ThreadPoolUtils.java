package com.lxzh123.wechatmoment.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description: 线程工具类
 * author:      Created by lxzh on 2021/4/19.
 */
public class ThreadPoolUtils {

    /**
     * android自带封装线程池
     **/
    private ThreadPoolExecutor mExecutor;
    /**
     * 线程数
     */
    private static final int GTM_THREAD_LENGTH = 15;
    /**
     * 定时任务线程数
     */
    private static final int THREAD_POOL_LENGTH = 5;

    /**
     * 线程池
     */
    private ScheduledExecutorService scheduledThreadPool;

    private static class ThreadPoolUtilsInstance {
        private static final ThreadPoolUtils INSTANCE = new ThreadPoolUtils();
    }

    /**
     * 初始化线程池
     *
     * @return 当前单利
     */
    public static ThreadPoolUtils get() {
        return ThreadPoolUtilsInstance.INSTANCE;
    }

    private ThreadPoolUtils() {
        // 使用 SDK 内部线程工厂，统一处理子线程异常
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();
        mExecutor = new ThreadPoolExecutor(GTM_THREAD_LENGTH, GTM_THREAD_LENGTH,
                0L, TimeUnit.MILLISECONDS, blockingQueue,
                ThreadFactoryUtils.get());
        scheduledThreadPool = Executors.newScheduledThreadPool(THREAD_POOL_LENGTH,
                ThreadFactoryUtils.get());
    }

    /**
     * 开启runnable
     *
     * @param runnable runnable对象
     */
    public void execute(Runnable runnable) {
        try {
            if (runnable != null) {
//                DebugLog.d("execute ActiveCount:" + mExecutor.getActiveCount() +
//                        " PoolSize:" + mExecutor.getPoolSize() +
//                        " CompletedTaskCount:" + mExecutor.getCompletedTaskCount() +
//                        " TaskCount:" + mExecutor.getTaskCount() +
//                        " CorePoolSize:" + mExecutor.getCorePoolSize());
                mExecutor.execute(runnable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行计时任务
     */
    public void executeScheduled(Runnable runnable, long initialDelay, long period, TimeUnit unit) {
        try {
            if (runnable != null) {
                scheduledThreadPool.scheduleWithFixedDelay(runnable, initialDelay, period, unit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
