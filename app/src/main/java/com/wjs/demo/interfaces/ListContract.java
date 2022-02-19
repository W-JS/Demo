package com.wjs.demo.interfaces;

import com.wjs.demo.base.BasePresenter;
import com.wjs.demo.base.BaseView;

public interface ListContract {

    interface Presenter extends BasePresenter {
        /**
         * 设置位置
         *
         * @param position 位置
         */
        void setListMode(int position);
    }

    interface View extends BaseView<Presenter> {
        /**
         * 恢复位置
         *
         * @param position 位置
         */
        void restoreListMode(int position);
    }
}
