package com.chat.dyh.presenter;

/**
 * Created by dengyonghong on 2017/12/12.
 */
public interface BaseView {
    void showLoading(String msg);
    void hideLoading();
    void showError(String errorMsg);


}
