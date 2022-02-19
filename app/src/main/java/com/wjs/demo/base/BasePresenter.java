package com.wjs.demo.base;

public interface BasePresenter {

    /**
     * 订阅
     */
    void subscribe();

    /**
     * 退订
     */
    void unsubscribe();

    /**
     * 销毁
     */
    void destroy();
}
