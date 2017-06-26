package explorer.ding.com.explorer.utils;

import android.text.TextUtils;
import android.util.Log;


import explorer.ding.com.explorer.BuildConfig;

public class MLog {
    public static final boolean DEBUG = BuildConfig.DEBUG;

    public static void e(String tag, Exception e) {
        if (e == null) {
            return;
        }
        String message = e.getMessage();
        if (TextUtils.isEmpty(message)) {
            return;
        }
        e(tag, message);
    }

    public static void d(String tag, String message) {
        if (DEBUG)
            Log.d(tag, message);
    }

    public static void e(String tag, String message) {
        if (DEBUG)
            Log.e(tag, message);
    }

    public static void i(String tag, String message) {
        if (DEBUG)
            Log.i(tag, message);
    }

    public static void v(String tag, String message) {
        if (DEBUG)
            Log.v(tag, message);
    }

    public static void w(String tag, String message) {
        if (DEBUG)
            Log.w(tag, message);
    }

    public static void w(String tag, String message, Throwable tr) {
        if (DEBUG)
            Log.wtf(tag, message, tr);
    }
}
