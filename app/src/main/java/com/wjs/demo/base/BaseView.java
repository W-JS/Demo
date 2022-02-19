package com.wjs.demo.base;

public interface BaseView<T> {

    /**
     * 设置发布者，负责逻辑的处理
     *
     * @param presenter 发布者
     */
    void setPresenter(T presenter);
}
