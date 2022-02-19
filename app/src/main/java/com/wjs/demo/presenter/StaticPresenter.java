package com.wjs.demo.presenter;

import androidx.annotation.NonNull;

import com.wjs.demo.interfaces.StaticContract;
import com.wjs.demo.utils.LogUtil;

public class StaticPresenter implements StaticContract.Presenter {

    @NonNull
    private final StaticContract.View mStaticView;
    private String staticMode = "";

    public StaticPresenter(@NonNull StaticContract.View mStaticView) {
        this.mStaticView = mStaticView;
        this.mStaticView.setPresenter(this);
    }

    @Override
    public void setStaticMode(String staticMode) {
        LogUtil.i("设置模式: " + staticMode);
        this.staticMode = staticMode;
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
