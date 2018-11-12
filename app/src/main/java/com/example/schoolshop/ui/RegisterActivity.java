package com.example.schoolshop.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.RegisterContract;
import com.example.schoolshop.contract.UserContract;
import com.example.schoolshop.gson.UserGson;
import com.example.schoolshop.presenter.RegisterPresenter;
import com.example.schoolshop.presenter.UserPresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegisterContract.View, UserContract.View {

    @InjectView(R.id.et_telphone)
    EditText etTelphone;
    @InjectView(R.id.tv_send)
    TextView tvSend;
    @InjectView(R.id.et_password)
    EditText etPassword;
    @InjectView(R.id.tv_second_password)
    EditText tvSecondPassword;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.et_smscode)
    EditText etSmscode;
    @InjectView(R.id.tv_school)
    EditText tvSchool;
    private RegisterPresenter smsCodePresent = new RegisterPresenter(this);
    private UserPresenter userRegister = new UserPresenter(this);

    @Override
    public int intiLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        initToolBar().setToolbarBackIco().setTitle("注册");
    }

    public static boolean isChinaPhoneLegal(String str)
            throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    @Override
    public void initData() {
        ButterKnife.inject(this);notSetStatusBarColor();
        etTelphone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean chinaPhoneLegal = isChinaPhoneLegal(s.toString());
                if (!chinaPhoneLegal) {
                    etTelphone.setTextColor(0xffff0000);
                } else {
                    etTelphone.setTextColor(0xff000000);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int setStatusBarColor() {
        return 0;
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
        Toast.makeText(this, "验证码发送错误：" + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sendSuccess() {
        Toast.makeText(this, "验证码发送成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void verify() {
        userRegister.userRegister(etTelphone.getText().toString(), etPassword.getText().toString(), tvSchool.getText().toString());
    }

    @Override
    public void register(UserGson userGson) {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
        editor.putString("username", userGson.getUsername());
        editor.putString("id", String .valueOf(userGson.getId()));
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }


    @OnClick({R.id.tv_send, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                if (etTelphone.getText().toString().isEmpty()) {
                    Toast.makeText(this, "手机号不可为空", Toast.LENGTH_SHORT).show();
                } else if (!isChinaPhoneLegal(etTelphone.getText().toString())) {
                    Toast.makeText(this, "手机号码输入错误", Toast.LENGTH_SHORT).show();
                } else {
                    smsCodePresent.requestSms(etTelphone.getText().toString());
                }
                break;
            case R.id.btn_login:
                if (tvSecondPassword.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty()) {
                    Toast.makeText(this, "密码不可输入为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (!tvSecondPassword.getText().toString().equals(etPassword.getText().toString())) {
                        Toast.makeText(this, "密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    } else if (tvSchool.getText().toString().isEmpty()) {
                        Toast.makeText(this, "校园不可输入为空", Toast.LENGTH_SHORT).show();
                    } else if (!isChinaPhoneLegal(etTelphone.getText().toString())) {
                        Toast.makeText(this, "手机号码输入不正确", Toast.LENGTH_SHORT).show();
                    } else {
                        smsCodePresent.verifySmsCode(etTelphone.getText().toString(), etSmscode.getText().toString());
                    }
                }
                break;
        }
    }
}
