package com.example.schoolshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.UserAddressContract;
import com.example.schoolshop.gson.AddressGson;
import com.example.schoolshop.presenter.UserAddressPresenter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UserAddressEditorActivity extends BaseActivity implements UserAddressContract.View {


    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.et_telphone)
    EditText etTelphone;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.et_num)
    EditText etNum;
    @InjectView(R.id.et_submit)
    TextView etSubmit;
    private UserAddressPresenter userAddressPresenter = new UserAddressPresenter(this);

    @Override
    public int intiLayout() {
        return R.layout.activity_address_editor;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        initToolBar().setToolbarBackIco().setToolbarSubtitle("编辑");
    }

    @Override
    protected int setStatusBarColor() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick({R.id.tv_address, R.id.et_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                startActivityForResult(new Intent(UserAddressEditorActivity.this, MapPointsActivity.class), 10);
                break;
            case R.id.et_submit:
                userAddressPresenter.addUserReceiveAddress(etUsername.getText().toString(), etTelphone.getText().toString(), tvAddress.getText().toString(), "1", etNum.getText().toString(), "1");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (data != null) {
                tvAddress.setText(data.getStringExtra("location"));
            }

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
        Toast.makeText(this, "保存出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadSuccess() {
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UserAddressEditorActivity.this, UserAddressListActivity.class);
        setResult(9, intent);
        finish();
    }

    @Override
    public void loadAddressList(List<AddressGson> addressGsonList) {

    }
}
