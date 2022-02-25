package com.wjs.demo.presenter;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.wjs.demo.data.DemoRepository;
import com.wjs.demo.interfaces.StaticContract;
import com.wjs.demo.schedulers.BaseSchedulerProvider;
import com.wjs.demo.utils.Config;
import com.wjs.demo.utils.DateUtil;
import com.wjs.demo.utils.FileUtil;
import com.wjs.demo.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;

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

    FileDownloadListener wallpaperFileDownloadListener = new FileDownloadListener() {
        @Override
        protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
            LogUtil.i("DownloadListener", "" + soFarBytes);
        }

        @Override
        protected void completed(BaseDownloadTask task) {
            LogUtil.i("DownloadListener", "task.getTag(): " + task.getTag());
            LogUtil.i("DownloadListener", "task.getTag(1): " + task.getTag(1));
            LogUtil.i("DownloadListener", "task.getPath(): " + task.getPath());
        }

        @Override
        protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

        }

        @Override
        protected void error(BaseDownloadTask task, Throwable e) {
            LogUtil.e("DownloadListener", "Throwable: " + e);
        }

        @Override
        protected void warn(BaseDownloadTask task) {

        }
    };

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
        DateUtil.getCurrentTimeFullDateFormat();

        String path = Config.RootPath + Config.WallpaperPath;
        LogUtil.e(path + " 路径下文件夹大小为: " + FileUtil.byteToKBorMBorGB(FileUtil.getFolderSize(new File(path))));

        String url = "";
        String id = String.valueOf(wallpaperId);
        id = id.substring(id.length() - 1, id.length());
        switch (id) {
            case "0":
                url = "https://wallpapercave.com/wp/wp4258424.jpg";
                break;
            case "8":
                url = "https://images.hdqwalls.com/download/nebula-abstract-rs-1080x2280.jpg";
                break;
            case "9":
                url = "https://images.wallpapersden.com/image/download/nebula-4k_a2xoaWyUmZqaraWkpJRmZW1lrWdnbWU.jpg";
                break;
        }
        LogUtil.d(url);

        String finalUrl = url;
        Disposable disposable = Flowable.fromCallable(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                /*FileDownloader.getImpl().create(finalUrl)
                        .setTag("tag")
                        .setTag(1, "ket tag")
                        .setPath(Config.RootPath + Config.WallpaperPath + RandomUtil.getRandomStringByUUID() + AndroidUtil.getImageExtension(finalUrl, "."))
                        .setListener(wallpaperFileDownloadListener)
                        .start();*/

                boolean flag = false;
                try {
                    if (wallpaperManager != null) {
                        Bitmap wallpaper = BitmapFactory.decodeResource(mContext.getResources(), wallpaperId);
                        LogUtil.i("开始设置壁纸");
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
                .observeOn(mBaseSchedulerProvider.io())
                .map(new Function<Boolean, Boolean>() {
                    @Override
                    public Boolean apply(Boolean flag) throws Throwable {
                        if (flag) {
                            LogUtil.i("壁纸设置成功 wallpaperId = " + wallpaperId);
                            LogUtil.i("将壁纸信息保存到数据库中");
                            mDemoRepository.updateCurrent(Config.WallpaperSetting, String.valueOf(wallpaperId), Config.RootPath + Config.WallpaperPath);
                        } else {
                            LogUtil.e("壁纸设置失败 wallpaperId = " + wallpaperId);
                        }
                        mStaticView.hidePopup();
                        return flag;
                    }
                })
                .subscribeOn(mBaseSchedulerProvider.io())
                .observeOn(mBaseSchedulerProvider.ui())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Throwable {
                        if (aBoolean) {
                            LogUtil.i("获取数据库保存的壁纸信息");
                            mDemoRepository.getCurrent();
                            mDemoRepository.getCurrentWallpaper();
                        }
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
