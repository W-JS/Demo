package com.wjs.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {

    public static void putStringObject(Context context, String xmlName, String key, String value) {
        if (context == null) {
            LogUtil.e("context 为空");
            return;
        }
        if (xmlName == null || "".equals(xmlName)) {
            LogUtil.e("xmlName 为空");
            return;
        }
        if (key == null || "".equals(key)) {
            LogUtil.e("key 为空");
            return;
        }
        if (value == null || "".equals(value)) {
            LogUtil.e("value 为空");
            return;
        }

        SharedPreferences.Editor editor = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
        LogUtil.i("保存 " + xmlName + " 到本地 SharedPreferences");
    }

    public static String getStringObject(Context context, String xmlName, String key) {
        if (context == null) {
            LogUtil.e("context 为空");
            return null;
        }
        if (xmlName == null || "".equals(xmlName)) {
            LogUtil.e("xmlName 为空");
            return null;
        }
        if (key == null || "".equals(key)) {
            LogUtil.e("key 为空");
            return null;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        if (!"".equals(value)) {
            LogUtil.i("获取本地保存的 " + xmlName + " 字符串: " + value);
        } else {
            LogUtil.e("本地未保存 " + xmlName);
        }
        return value;
    }

}
