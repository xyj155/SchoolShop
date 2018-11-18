package com.example.schoolshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {


    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    RelativeLayout toolbar;
    @InjectView(R.id.tv_password_reset)
    TextView tvPasswordReset;
    @InjectView(R.id.tv_verify)
    TextView tvVerify;
    @InjectView(R.id.tv_runhelper)
    TextView tvRunhelper;
    @InjectView(R.id.tv_feedback)
    TextView tvFeedback;
    @InjectView(R.id.tv_about)
    TextView tvAbout;
    @InjectView(R.id.btn_login_out)
    Button btnLoginOut;

    @Override
    public int intiLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        initToolBar().setToolbarBackIco().setToolbarSubtitle("设置");
    }

    @Override
    public void initData() {

    }

    @Override
    protected int setStatusBarColor() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R.id.tv_password_reset, R.id.tv_verify, R.id.tv_runhelper, R.id.tv_feedback, R.id.tv_about, R.id.btn_login_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_password_reset:
                break;
            case R.id.tv_verify:
                break;
            case R.id.tv_runhelper:
                break;
            case R.id.tv_feedback:
                startActivity(new Intent(SettingActivity.this, FeedbackMessageActivity.class));
                break;
            case R.id.tv_about:
                startActivity(new Intent(SettingActivity.this, AppAboutActivity.class));
                break;
            case R.id.btn_login_out:
                break;
        }
    }
}
