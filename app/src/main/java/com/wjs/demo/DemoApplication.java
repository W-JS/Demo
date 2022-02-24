package com.wjs.demo;

import android.app.Application;
import android.content.Context;

import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.wjs.demo.utils.LogUtil;

public class DemoApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        LogUtil.i("onCreate");

        // Application中初始化 FileDownloader
        FileDownloader.setupOnApplicationOnCreate(this)
                .connectionCreator(new FileDownloadUrlConnection
                        .Creator(new FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15_000) // set connection timeout.
                        .readTimeout(15_000) // set read timeout.
                ))
                .commit();
    }

    // 获取 DemoApplication 的上下文对象
    public static Context getContext() {
        return mContext;
    }
}
