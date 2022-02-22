package com.wjs.demo.presenter;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.wjs.demo.data.DemoRepository;
import com.wjs.demo.interfaces.StaticContract;
import com.wjs.demo.schedulers.BaseSchedulerProvider;
import com.wjs.demo.utils.LogUtil;

import java.io.IOException;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

public class StaticPresenter implements StaticContract.Presenter {

    private Context mContext;
    @NonNull
    private final StaticContract.View mStaticView;
    @NonNull
    private DemoRepository mDemoRepository;
    @NonNull
    private BaseSchedulerProvider mBaseSchedulerProvider;
    @NonNull
    private CompositeDisposable mCompositeDisposable;

    private WallpaperManager wallpaperManager;
    private String staticMode = "";

    public StaticPresenter(Context context, @NonNull StaticContract.View mStaticView, DemoRepository mDemoRepository, BaseSchedulerProvider mBaseSchedulerProvider) {
        mContext = context;
        this.mStaticView = mStaticView;
        this.mStaticView.setPresenter(this);
        this.mDemoRepository = mDemoRepository;
        this.mBaseSchedulerProvider = mBaseSchedulerProvider;
        mCompositeDisposable = new CompositeDisposable();

        wallpaperManager = WallpaperManager.getInstance(mContext);
    }

    @Override
    public void setStaticMode(String staticMode) {
        LogUtil.i("设置模式: " + staticMode);
        this.staticMode = staticMode;
    }

    @Override
    public void setWallpaper(int wallpaperId) {
        Disposable disposable = Flowable.fromCallable(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                boolean flag = false;
                try {
                    if (wallpaperManager != null) {
                        Bitmap wallpaper = BitmapFactory.decodeResource(mContext.getResources(), wallpaperId);
                        wallpaperManager.setBitmap(wallpaper);
                        flag = true;
                    }
                } catch (IOException e) {
                    LogUtil.e("IOException: " + e);
                    flag = false;
                } finally {
                    return flag;
                }
            }
        })
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Throwable {
                        if (aBoolean) {
                            LogUtil.i("壁纸设置成功 wallpaperId = " + wallpaperId);
                        } else {
                            LogUtil.e("壁纸设置失败 wallpaperId = " + wallpaperId);
                        }
                        mStaticView.hidePopup();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        LogUtil.e("Throwable: " + throwable);
                    }
                });

        mCompositeDisposable.add(disposable);
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
        mCompositeDisposable.clear();
        LogUtil.i("destroy");
    }
}
