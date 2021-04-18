package com.lxzh123.wechatmoment.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.lxzh123.wechatmoment.common.Constants;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * description: Log日志类
 * author:      Created by lxzh on 2021/4/18.
 */
public class LogUtils {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;

    public static final String V = "V";
    public static final String D = "D";
    public static final String I = "I";
    public static final String W = "W";
    public static final String E = "E";
    public static final String N = "N";

    private static int LEVEL = VERBOSE;
    private static LogUtils.Logger logger = null;
    private static String TAG = Constants.LOG_TAG;

    public static void init(int level, String tag) {
        LEVEL = level;
        TAG = tag;
        Log.i(TAG, "init log level: " + level);
    }

    public static void v(String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(TAG, msg);
        }
        log2sd(TAG, V, msg);
    }

    public static void d(String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(TAG, msg);
        }
        log2sd(TAG, D, msg);
    }

    public static void i(String msg) {
        if (LEVEL <= INFO) {
            Log.i(TAG, msg);
            log2sd(TAG, I, msg);
        } else {
            release(msg);
        }
    }

    public static void w(String msg) {
        if (LEVEL <= WARN) {
            Log.w(TAG, msg);
            log2sd(TAG, W, msg);
        }
    }

    public static void e(String msg) {
        if (LEVEL <= ERROR) {
            Log.e(TAG, msg);
            log2sd(TAG, E, msg);
        } else {
            release(msg);
        }
    }

    public static void fe(String msg) {
        Log.e(TAG, msg);
        log2sd(TAG, E, msg);
    }

    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, msg);
            log2sd(tag, V, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, msg);
            log2sd(tag, D, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
            Log.i(tag, msg);
            log2sd(tag, I, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LEVEL <= WARN) {
            Log.w(tag, msg);
            log2sd(tag, W, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            Log.e(tag, msg);
            log2sd(tag, E, msg);
        }
    }

    public static void fe(String tag, String msg) {
        Log.e(tag, msg);
        log2sd(tag, E, msg);
    }

    public static void release(String msg) {
        if (LEVEL >= NOTHING) {
            log2sd(TAG, I, msg);
        }
    }

    private static void log2sd(String tag, String level, String msg) {
        if (logger == null) {
            logger = new LogUtils.Logger();
            logger.init();
            logger.checkLogFile();
        }
        logger.log(tag, level, msg);
    }

    private static class Logger {
        private HandlerThread thread;
        private Handler handler;
        private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        private String logPath;

        private static final String FILE_NAME = "wechat_moment_log.txt";
        private static final String EXTERNAL_DIR = "lxzh";
        private static final int WHAT_MSG = 0;
        private static final int WHAT_INIT = 1;
        private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10m  10 * 1024 * 1024

        public Logger() {

        }

        public synchronized void init() {
            thread = new HandlerThread("Lxzh_Log_Thread");
            thread.start();
            thread.setUncaughtExceptionHandler(ThreadExceptionHandler.getUncaughtExceptionHandler());
            handler = new Handler(thread.getLooper()) {

                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (Thread.interrupted()) {
                        return;
                    }
                    if (SdUtils.needCheckMounted() && !SdUtils.isMounted()) {
                        return;
                    }
                    if (msg.what == WHAT_MSG) {
                        LogUtils.Logger.Item item = (LogUtils.Logger.Item) msg.obj;
                        write(build(sdf, item.millis, item.tag, item.level, item.message));
                    } else if (msg.what == WHAT_INIT) {
                        deleteCauseExceedMaxSize();
                    }
                }
            };
        }

        private static class Item {
            public long millis;
            public String tag;
            public String level;
            public String message;
        }

        public synchronized void log(String tag, String level, String msg) {
//            if (!SdUtils.isMounted()) {
//                return;
//            }
            Message message = handler.obtainMessage();
            message.what = WHAT_MSG;

            LogUtils.Logger.Item item = new LogUtils.Logger.Item();
            item.millis = System.currentTimeMillis();
            item.tag = tag;
            item.level = level;
            item.message = msg;
            message.obj = item;
            handler.sendMessage(message);
        }

        public synchronized void checkLogFile() {
//            if (!SdUtils.isMounted()) {
//                return;
//            }
            Message message = handler.obtainMessage();
            message.what = WHAT_INIT;
            handler.sendMessage(message);
        }

        public synchronized void destroy() {
            if (thread != null && handler != null) {
                handler.removeMessages(WHAT_MSG);
                handler.removeMessages(WHAT_INIT);

                thread.quit();
                thread = null;
            }
        }

        private static String externalDirPath() {
            return SdUtils.getDirPath() + File.separator + EXTERNAL_DIR;
        }

        private static boolean deleteCauseExceedMaxSize() {
            try {
                File dir = new File(externalDirPath());
                if (!dir.exists()) {
                    return false;
                }
                File f = new File(dir, FILE_NAME);
                if (!f.exists()) {
                    return false;
                }
                if (f.length() < MAX_FILE_SIZE) {
                    return false;
                }
                return f.delete();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        private void write(String content) {
            BufferedOutputStream out = null;
            try {
                if (TextUtils.isEmpty(logPath)) {
                    logPath = externalDirPath();
                }
                if (TextUtils.isEmpty(logPath)) {
                    return;
                }
                File dir = new File(logPath);
                if (!dir.exists()) {
                    WarnHandler.ignoreResult(dir.mkdirs());
                }
                File file = new File(dir, FILE_NAME);
                if (!file.exists()) {
                    WarnHandler.ignoreResult(file.createNewFile());
                }
                out = new BufferedOutputStream(new FileOutputStream(file, true));
                out.write(content.getBytes("utf-8"));
            } catch (Exception e) {
                // 日志记录异常，忽略
                WarnHandler.ignoreException(e);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        WarnHandler.ignoreException(e);
                    }
                }
            }
        }

        private static String build(SimpleDateFormat sdf, long millis, String tag, String level, String msg) {
            StringBuilder sb = new StringBuilder();
            sb.append(sdf.format(new Date(millis)));
            sb.append('\t');
            sb.append(tag);
            sb.append(" [");
            sb.append(level);
            sb.append("] ");
            sb.append(msg);
            sb.append('\n');

            return sb.toString();
        }
    }
}
