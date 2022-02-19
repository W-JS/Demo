package com.wjs.demo.base;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.wjs.demo.utils.LogUtil;

public class BaseFragment extends Fragment {

    private BaseActivity baseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.i("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("onCreate");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.i("onActivityCreated");
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity instanceof BaseActivity) {
            baseActivity = (BaseActivity) fragmentActivity;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.i("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.i("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.i("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.i("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.i("onDetach");
    }

    /**
     * 点击返回键，是否返回上一页
     *
     * @return false-不返回，true-返回
     */
    public boolean onBackPressed() {
        LogUtil.i("onBackPressed");
        return true;
    }

    /**
     * 直接返回上一页
     */
    public void onHomeAsUpClick() {
        LogUtil.i("onHomeAsUpClick");
        if (null != baseActivity) {
            baseActivity.onHomeAsUpClick();
        }
    }
}
