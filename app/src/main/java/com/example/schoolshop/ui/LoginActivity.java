package com.example.schoolshop.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.LoginContract;
import com.example.schoolshop.gson.UserGson;
import com.example.schoolshop.presenter.LoginPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.View {


    @InjectView(R.id.imageView2)
    ImageView imageView2;
    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.tv_register)
    TextView tvRegister;
    @InjectView(R.id.tv_forget)
    TextView tvForget;

    private LoginPresenter loginPresenter = new LoginPresenter(this);

    @Override
    public int intiLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
    }

    @Override
    public void initData() {

    }

    @Override
    protected int setStatusBarColor() {
        return getColor(R.color.white);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                loginPresenter.userLogin(etUsername.getText().toString(), etPassword.getText().toString());
                break;
            case R.id.tv_register:
                break;
            case R.id.tv_forget:
                break;
        }
    }

    @Override
    public void showLoading() {
        showDialog();
    }

    @Override
    public void hideLoading() {
        dialogClose();
    }

    @Override
    public void loadFailed(String msg) {
        dialogClose();
        Toast.makeText(this, "登录失败:" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void login(UserGson userGson) {
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString("username", userGson.getUsername());
        editor.putString("uid", String.valueOf(userGson.getId()));
        editor.putString("token", String.valueOf(userGson.getToken()));
        editor.apply();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("username", userGson.getUsername());
        setResult(0, intent);
        finish();
    }
}
