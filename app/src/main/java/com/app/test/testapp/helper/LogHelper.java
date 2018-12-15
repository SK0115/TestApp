package com.app.test.testapp.helper;

import android.util.Log;

public class LogHelper {

    private static final String LOGTAG = "MYAPP";

    public static void releaseLog(String log) {
        Log.i(LOGTAG, log);
    }

    public static void releaseLog(String tag, String log) {
        Log.i(LOGTAG, tag + " " + log);
    }

    public static void errorLog(String log) {
        Log.e(LOGTAG, log);
    }

    public static void errorLog(String tag, String log) {
        Log.e(LOGTAG, tag + " " + log);
    }
}
