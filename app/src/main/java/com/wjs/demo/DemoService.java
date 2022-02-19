package com.wjs.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.wjs.demo.utils.LogUtil;

public class DemoService extends Service {
    public DemoService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i("onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i("onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }
}