package cn.com.ichile.bigbug;

import android.util.Log;

/**
 * FBI WARNING ! MAGIC ! DO NOT TOUGH !
 * Created by WangZQ on 2017/4/25 - 9:51.
 */

public class Logger {
    private static final int V = 1;
    private static final int D = 2;
    private static final int I = 3;
    private static final int W = 4;
    private static final int E = 5;
    private static final String TAG_PREFIX = "TTT_";
    private static boolean showLog = true;
    private static int level = 6;

    public static void setShowLog(boolean show) {
        showLog = show;
    }

    public static void setLogLevel(int i) {
        level = i;
    }

    public static void v(String tag, String msg) {
        if (showLog && level >= 1)
            Log.v(TAG_PREFIX + tag, attachMsgToThread(msg));
    }

    public static void d(String tag, String msg) {
        if (showLog && level >= 2)
            Log.d(TAG_PREFIX + tag, attachMsgToThread(msg));
    }

    public static void i(String tag, String msg) {
        if (showLog && level >= 3)
            Log.i(TAG_PREFIX + tag, attachMsgToThread(msg));
    }

    public static void w(String tag, String msg) {
        if (showLog && level >= 4)
            Log.w(TAG_PREFIX + tag, attachMsgToThread(msg));
    }

    public static void e(String tag, String msg) {
        if (showLog && level >= 5)
            Log.e(TAG_PREFIX + tag, attachMsgToThread(msg));
    }


    private static String attachMsgToThread(String msg) {
        return "" + Thread.currentThread().getId() + ":" + Thread.currentThread().getName() + "-"
                + msg;
    }

}
