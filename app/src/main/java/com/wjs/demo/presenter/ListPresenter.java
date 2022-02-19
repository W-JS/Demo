package com.wjs.demo.presenter;

import androidx.annotation.NonNull;

import com.wjs.demo.interfaces.ListContract;
import com.wjs.demo.utils.LogUtil;

public class ListPresenter implements ListContract.Presenter {

    @NonNull
    private final ListContract.View mListView;
    private int position = -1;

    public ListPresenter(@NonNull ListContract.View mListView) {
        this.mListView = mListView;
        this.mListView.setPresenter(this);
    }

    @Override
    public void setListMode(int position) {
        LogUtil.i("设置位置: " + position);
        this.position = position;
    }

    @Override
    public void subscribe() {
        LogUtil.i("subscribe 恢复位置: " + this.position);
        mListView.restoreListMode(this.position);
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
