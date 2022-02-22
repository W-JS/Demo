package com.wjs.demo.presenter;

import android.content.Context;

import androidx.annotation.NonNull;

import com.wjs.demo.data.DemoRepository;
import com.wjs.demo.interfaces.ListContract;
import com.wjs.demo.schedulers.BaseSchedulerProvider;
import com.wjs.demo.utils.LogUtil;

public class ListPresenter implements ListContract.Presenter {

    private Context mContext;
    @NonNull
    private final ListContract.View mListView;
    @NonNull
    private DemoRepository mDemoRepository;
    @NonNull
    private BaseSchedulerProvider mBaseSchedulerProvider;

    private int position = -1;

    public ListPresenter(Context context, @NonNull ListContract.View mListView, DemoRepository mDemoRepository, BaseSchedulerProvider mBaseSchedulerProvider) {
        mContext = context;
        this.mListView = mListView;
        this.mListView.setPresenter(this);
        this.mDemoRepository = mDemoRepository;
        this.mBaseSchedulerProvider = mBaseSchedulerProvider;
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
