package com.wjs.demo.presenter;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.wjs.demo.interfaces.StaticContract;
import com.wjs.demo.utils.LogUtil;

import java.io.IOException;

public class StaticPresenter implements StaticContract.Presenter {

    private Context mContext;
    private WallpaperManager wallpaperManager;
    @NonNull
    private final StaticContract.View mStaticView;
    private String staticMode = "";

    public StaticPresenter(Context context, @NonNull StaticContract.View mStaticView) {
        mContext = context;
        this.mStaticView = mStaticView;
        this.mStaticView.setPresenter(this);
        wallpaperManager = WallpaperManager.getInstance(mContext);
    }

    @Override
    public void setStaticMode(String staticMode) {
        LogUtil.i("设置模式: " + staticMode);
        this.staticMode = staticMode;
    }

    @Override
    public void setWallpaper(int wallpaperId) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (wallpaperManager != null) {
                        Bitmap wallpaper = BitmapFactory.decodeResource(mContext.getResources(), wallpaperId);
                        wallpaperManager.setBitmap(wallpaper);
                        LogUtil.i("壁纸设置成功 wallpaperId = " + wallpaperId);
                        mStaticView.hidePopup();
                    }
                } catch (IOException e) {
                    LogUtil.e("IOException: " + e);
                }
            }
        }, 1_000);

    }

    @Override
    public void subscribe() {
        LogUtil.i("subscribe 恢复模式: " + this.staticMode);
        mStaticView.restoreStaticMode(this.staticMode);
    }

    @Override
    public void unsubscribe() {
        LogUtil.i("unsubscribe");
    }

    @Override
    public void destroy() {
        LogUtil.i("destroy");
    }
}
