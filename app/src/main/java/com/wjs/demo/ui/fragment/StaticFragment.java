package com.wjs.demo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wjs.demo.R;
import com.wjs.demo.base.BaseFragment;
import com.wjs.demo.interfaces.StaticContract;
import com.wjs.demo.ui.dialog.FullScreenPopup;
import com.wjs.demo.utils.LogUtil;

public class StaticFragment extends BaseFragment implements StaticContract.View, View.OnClickListener {

    private Context mContext;

    private static StaticContract.Presenter presenter;

    private static StaticFragment instance = null;
    private FullScreenPopup popup;

    private Button upBtn, centerBtn, downBtn;

    private final String strUp = "up", strCenter = "center", strDown = "down", strNull = "null";
    private String lastSelected = "";

    private boolean firstClickFlag = false;

    private StaticFragment(Context context) {
        mContext = context;
    }

    public static StaticFragment getInstance(Context context) {
        if (instance == null) {
            instance = new StaticFragment(context);
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_static, container, false);
        initView(view);
        initListener();
        initData();
        return view;
    }

    public void initView(View view) {
        popup = FullScreenPopup.getInstance(mContext);

        upBtn = view.findViewById(R.id.btn_up);
        centerBtn = view.findViewById(R.id.btn_center);
        downBtn = view.findViewById(R.id.btn_down);
    }

    public void initListener() {
        upBtn.setOnClickListener(this);
        centerBtn.setOnClickListener(this);
        downBtn.setOnClickListener(this);
    }

    public void initData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.subscribe();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destroy();
        }
    }

    @Override
    public void restoreStaticMode(String staticMode) {
        if (firstClickFlag) {
            lastSelected = strNull;
            setSelectedBtn(staticMode);
        }
    }

    @Override
    public void hidePopup() {
        popup.dismiss();
    }

    @Override
    public void setPresenter(StaticContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_up:
                setSelectedBtn(strUp);
                break;
            case R.id.btn_center:
                setSelectedBtn(strCenter);
                break;
            case R.id.btn_down:
                setSelectedBtn(strDown);
                break;
            default:
                break;
        }
    }

    private void setSelectedBtn(String str) {
        if (presenter == null) {
            LogUtil.e("presenter == null");
            return;
        }

        if (lastSelected.equals(str)) {
            LogUtil.w("??????????????????: " + str);
            return;
        }

        // ????????????????????????????????????
        switch (lastSelected) {
            case strUp:
                if (upBtn.isSelected()) {
                    upBtn.setSelected(false);
                }
                break;
            case strCenter:
                if (centerBtn.isSelected()) {
                    centerBtn.setSelected(false);
                }
                break;
            case strDown:
                if (downBtn.isSelected()) {
                    downBtn.setSelected(false);
                }
                break;
            default:
                break;
        }

        boolean flag = false;
        int showImageId = 0;
        int wallpaperId = 0;
        String text = "";
        // ???????????????????????????
        switch (str) {
            case strUp:
                upBtn.setSelected(true);
                text = "???????????? ???";
                if (!strNull.equals(lastSelected)) {
                    presenter.setStaticMode(strUp);
                    showImageId = R.mipmap.icon01;
                    wallpaperId = R.mipmap.wallpaper_1080x2280_01;
                    firstClickFlag = true;
                    flag = true;
                }
                break;
            case strCenter:
                centerBtn.setSelected(true);
                text = "???????????? ???";
                if (!strNull.equals(lastSelected)) {
                    presenter.setStaticMode(strCenter);
                    showImageId = R.mipmap.icon02;
                    wallpaperId = R.mipmap.wallpaper_1080x2280_02;
                    firstClickFlag = true;
                    flag = true;
                }
                break;
            case strDown:
                downBtn.setSelected(true);
                text = "???????????? ???";
                if (!strNull.equals(lastSelected)) {
                    presenter.setStaticMode(strDown);
                    showImageId = R.mipmap.icon03;
                    wallpaperId = R.mipmap.wallpaper_1080x2280_03;
                    firstClickFlag = true;
                    flag = true;
                }
                break;
            default:
                return;
        }
        LogUtil.i(text);

        lastSelected = str;
        if (flag && wallpaperId != 0 && showImageId != 0) {
            popup.show();
            popup.setTvDesc(getResources().getString(R.string.string_applying_wallpapers_please_wait));
            popup.timeOutDismissDialog();

            presenter.setWallpaper(wallpaperId);
        }
    }
}