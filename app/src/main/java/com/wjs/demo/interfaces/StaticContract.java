package com.wjs.demo.interfaces;

import com.wjs.demo.base.BasePresenter;
import com.wjs.demo.base.BaseView;

public interface StaticContract {

    interface Presenter extends BasePresenter {
        /**
         * 设置模式
         *
         * @param staticMode 模式
         */
        void setStaticMode(String staticMode);

        void setWallpaper(int wallpaperId);
    }

    interface View extends BaseView<Presenter> {
        /**
         * 恢复模式
         *
         * @param staticMode 模式
         */
        void restoreStaticMode(String staticMode);

        void hidePopup();
    }
}
