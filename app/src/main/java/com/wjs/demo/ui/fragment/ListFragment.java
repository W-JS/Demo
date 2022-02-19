package com.wjs.demo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wjs.demo.R;
import com.wjs.demo.base.BaseFragment;
import com.wjs.demo.interfaces.ListContract;
import com.wjs.demo.presenter.ListPresenter;
import com.wjs.demo.utils.LogUtil;

public class ListFragment extends BaseFragment implements ListContract.View {


    private static ListContract.Presenter presenter;

    private static ListFragment instance = null;

    private int lastSelected = -1;

    private ListFragment() {
    }

    public static ListFragment getInstance() {
        if (instance == null) {
            instance = new ListFragment();
            presenter = new ListPresenter(ListFragment.getInstance());
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.i("onCreateView");
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initView(view);
        initListener();
        initData();
        return view;
    }

    public void initView(View view) {
    }

    public void initListener() {
    }

    public void initData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.subscribe();
        }
        LogUtil.i("onResume");
    }

    @Override
    public void restoreListMode(int position) {
        lastSelected = -2;
        setSelectedBtn(position);
    }

    @Override
    public void setPresenter(ListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void setSelectedBtn(int position) {
        if (presenter == null) {
            LogUtil.e("presenter == null");
            return;
        }

        if (lastSelected == position) {
            LogUtil.w("该位置已选中: " + position);
            return;
        }

        // 取消上次选中的模式

        // 选中当前点击的模式
        presenter.setListMode(position);

        LogUtil.i("选中位置 position = " + position);

        lastSelected = position;
    }
}