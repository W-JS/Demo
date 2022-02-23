package com.wjs.demo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.wjs.demo.base.BaseActivity;
import com.wjs.demo.data.DemoRepository;
import com.wjs.demo.presenter.ListPresenter;
import com.wjs.demo.presenter.StaticPresenter;
import com.wjs.demo.schedulers.SchedulerProvider;
import com.wjs.demo.ui.fragment.ListFragment;
import com.wjs.demo.ui.fragment.StaticFragment;
import com.wjs.demo.utils.AndroidUtil;
import com.wjs.demo.utils.LogUtil;

import static com.wjs.demo.utils.ConstantUtil.lastSelectedMainPage;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Context mContext;
    private FrameLayout content;
    private Button leftBtn, rightBtn;
    private StaticFragment staticFragment;
    private ListFragment listFragment;

    private final String strLeft = "left", strRight = "right";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();

        AndroidUtil.upgradeRootPermission(getPackageCodePath());
        AndroidUtil.isInternetConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!"".equals(lastSelectedMainPage)) {
            setSelectedBtn(false, lastSelectedMainPage);
        } else {
            setSelectedBtn(false, strLeft);
        }
    }

    public void initView() {
        staticFragment = StaticFragment.getInstance(mContext);
        listFragment = ListFragment.getInstance(mContext);

        new StaticPresenter(mContext, StaticFragment.getInstance(mContext), DemoRepository.getInstance(mContext), SchedulerProvider.getInstance());
        new ListPresenter(mContext, ListFragment.getInstance(mContext), DemoRepository.getInstance(mContext), SchedulerProvider.getInstance());

        content = findViewById(R.id.fl_content);
        leftBtn = findViewById(R.id.btn_left);
        rightBtn = findViewById(R.id.btn_right);
    }

    public void initListener() {
        leftBtn.setOnClickListener(this);
        rightBtn.setOnClickListener(this);
    }

    public void initData() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                setSelectedBtn(true, strLeft);
                break;
            case R.id.btn_right:
                setSelectedBtn(true, strRight);
                break;
            default:
                break;
        }
    }

    /**
     * @param flag 只针对点击操作验证该模式是否已选中
     * @param str
     */
    private void setSelectedBtn(boolean flag, String str) {
        if (flag && lastSelectedMainPage.equals(str)) {
            LogUtil.w("该模式已选中: " + str);
            return;
        }

        // 取消上次选中的模式
        switch (lastSelectedMainPage) {
            case strLeft:
                if (leftBtn.isSelected()) {
                    leftBtn.setSelected(false);
                }
                break;
            case strRight:
                if (rightBtn.isSelected()) {
                    rightBtn.setSelected(false);
                }
                break;
            default:
                break;
        }

        String text = "";
        // 选中当前点击的模式
        switch (str) {
            case strLeft:
                leftBtn.setSelected(true);
                replaceFragment(staticFragment);
                text = "选中按钮 左";
                break;
            case strRight:
                rightBtn.setSelected(true);
                replaceFragment(listFragment);
                text = "选中按钮 右";
                break;
            default:
                return;
        }
        LogUtil.i(text);

        lastSelectedMainPage = str;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content, fragment);
        transaction.commit();
    }
}