package com.wjs.demo.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;

import com.wjs.demo.R;
import com.wjs.demo.utils.LogUtil;

public class BaseActivity extends FragmentActivity {

    private FrameLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("onCreate");
        super.setContentView(R.layout.activity_base);
        parent = findViewById(R.id.content);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.i("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.i("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.i("onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.i("onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i("onDestroy");
    }

    @Override
    public void setContentView(int layoutResID) {
        parent.addView(LayoutInflater.from(this).inflate(layoutResID, parent, false));
        LogUtil.i("setContentView");
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.content) instanceof BaseFragment) {
            LogUtil.i("onBackPressed");
            BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.content);
            if (null != baseFragment && !baseFragment.onBackPressed()) {
                return;
            }
        }
        onHomeAsUpClick();
    }

    public void onPopBackStack() {
        LogUtil.i("onPopBackStack");
    }

    /**
     * 直接返回上一页
     */
    public void onHomeAsUpClick() {
        LogUtil.i("onHomeAsUpClick");
        if (getSupportFragmentManager().popBackStackImmediate()) {
            onPopBackStack();
        } else {
            finish();
        }
    }

    public int setPosition(int position) {
        return position;
    }
}