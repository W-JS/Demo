package com.wjs.demo.utils;

import android.util.Log;

public class LogUtil {

    private final static String TAG = "WJS";

    public static void v(String msg) {
        Log.v(TAG, getFileLineMethod() + msg);
    }

    public static void v(String TAG, String msg) {
        Log.v(LogUtil.TAG + ":" + TAG, getFileLineMethod() + msg);
    }

    public static void d(String msg) {
        Log.d(TAG, getFileLineMethod() + msg);
    }

    public static void d(String TAG, String msg) {
        Log.d(LogUtil.TAG + ":" + TAG, getFileLineMethod() + msg);
    }

    public static void i(String msg) {
        Log.i(TAG, getFileLineMethod() + msg);
    }

    public static void i(String TAG, String msg) {
        Log.i(LogUtil.TAG + ":" + TAG, getFileLineMethod() + msg);
    }

    public static void w(String msg) {
        Log.w(TAG, getFileLineMethod() + msg);
    }

    public static void w(String TAG, String msg) {
        Log.w(LogUtil.TAG + ":" + TAG, getFileLineMethod() + msg);
    }

    public static void e(String msg) {
        Log.e(TAG, getFileLineMethod() + msg);
    }

    public static void e(String TAG, String msg) {
        Log.e(LogUtil.TAG + ":" + TAG, getFileLineMethod() + msg);
    }

    public static String getFileLineMethod() {
        StackTraceElement stackTraceElement = (new Exception()).getStackTrace()[2];
        StringBuffer toStringBuffer = new StringBuffer("[")
                .append(stackTraceElement.getFileName())
                .append(":")
                .append(stackTraceElement.getLineNumber())
                .append("] ");
        return toStringBuffer.toString();
    }
}
