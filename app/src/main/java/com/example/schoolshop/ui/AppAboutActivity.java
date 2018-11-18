package com.example.schoolshop.ui;

import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;

public class AppAboutActivity extends BaseActivity {


    @Override
    public int intiLayout() {
        return R.layout.activity_app_about;
    }

    @Override
    public void initView() {
        initToolBar().setToolbarBackIco().setToolbarSubtitle("关于APP");
    }

    @Override
    public void initData() {

    }

    @Override
    protected int setStatusBarColor() {
        return 0;
    }
}
