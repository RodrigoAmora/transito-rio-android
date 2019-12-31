package br.com.rodrigoamora.transitorio.util;

import android.util.Log;

import br.com.rodrigoamora.transitorio.BuildConfig;

public class LogUtil {

    public static void log(String tag, String msg, LogEnum logEnum) {
        if (BuildConfig.DEBUG) {
            switch (logEnum) {
                case DEBUG:
                    logDebug(tag, msg);
                    break;

                case ERROR:
                    logDError(tag, msg);
                    break;

                case INFO:
                    logInfo(tag, msg);
                    break;

                case VERBOSE:
                    logVerbose(tag, msg);
                    break;

                case WARN:
                    logWarn(tag, msg);
                    break;
            }
        }
    }

    private static void logDebug(String tag, String msg) {
        Log.d(tag, msg);
    }

    private static void logInfo(String tag, String msg) {
        Log.i(tag, msg);
    }

    private static void logVerbose(String tag, String msg) {
        Log.v(tag, msg);
    }

    private static void logDError(String tag, String msg) {
        Log.e(tag, msg);
    }

    private static void logWarn(String tag, String msg) {
        Log.d(tag, msg);
    }
}
