package com.example.schoolshop.base;

/**
 * Created by 徐易杰 on 2018/11/1.
 */

public interface BaseView {
    void showLoading();

    void hideLoading();

    void loadFailed(String msg);

}
