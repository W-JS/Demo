package com.wjs.demo.data;

import android.content.Context;

import com.wjs.demo.interfaces.DemoDataSource;

public class DemoRepository implements DemoDataSource {

    private static volatile DemoRepository instance = null;
    private Context mContext;

    private DemoRepository(Context context) {
        mContext = context;
    }

    public static DemoRepository getInstance(Context context) {
        if (instance == null) {
            instance = new DemoRepository(context);
        }
        return instance;
    }

    @Override
    public String getWallpaperList(boolean isAgain) {
        return null;
    }
}
