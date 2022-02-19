package com.wjs.demo;

import android.app.Application;
import android.content.Context;

import com.wjs.demo.utils.LogUtil;

public class DemoApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        LogUtil.i("onCreate");
    }

    // 获取 DemoApplication 的上下文对象
    public static Context getContext() {
        return mContext;
    }
}
