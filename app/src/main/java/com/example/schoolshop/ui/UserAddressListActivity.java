package com.example.schoolshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.UserAddressContract;
import com.example.schoolshop.gson.AddressGson;
import com.example.schoolshop.presenter.UserAddressPresenter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UserAddressListActivity extends BaseActivity implements UserAddressContract.View {
    @InjectView(R.id.ry_address)
    RecyclerView ryAddress;
    @InjectView(R.id.tv_add)
    TextView tvAdd;
    @InjectView(R.id.tv_empty)
    TextView tvEmpty;
    private UserAddressAdapter userAdapter = new UserAddressAdapter(null);
    private UserAddressPresenter userAddressPresenter = new UserAddressPresenter(this);

    @Override
    public int intiLayout() {
        return R.layout.activity_user_addresslist;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        initToolBar().setToolbarSubtitle("收货地址")
                .setToolbarBackIco();
        ryAddress.setLayoutManager(new LinearLayoutManager(UserAddressListActivity.this));

    }

    @Override
    public void initData() {

        userAddressPresenter.getUserAddressList("1");

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

    }

    @Override
    public void loadSuccess() {

    }

    @Override
    public void loadAddressList(List<AddressGson> addressGsonList) {
        Log.i(TAG, "loadAddressList: " + addressGsonList.size());
        if (addressGsonList.size() > 0) {
            userAdapter.replaceData(addressGsonList);
            ryAddress.setVisibility(View.VISIBLE);
            ryAddress.setAdapter(userAdapter);
            tvEmpty.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9) {
            userAddressPresenter.getUserAddressList("1");
        }
    }

    @OnClick(R.id.tv_add)
    public void onViewClicked() {
        startActivityForResult(new Intent(UserAddressListActivity.this, UserAddressEditorActivity.class), 9);
    }

    private class UserAddressAdapter extends BaseQuickAdapter<AddressGson, BaseViewHolder> {

        public UserAddressAdapter(@Nullable List<AddressGson> data) {
            super(R.layout.ry_address_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final AddressGson item) {
            Log.i(TAG, "convert: " + item.getDetail().isEmpty());
            helper.setOnClickListener(R.id.rl_address, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserAddressListActivity.this, SubmitGoodsOrderActivity.class);
                    intent.putExtra("username", item.getUsername());
                    intent.putExtra("address", item.getDetail());
                    intent.putExtra("city", item.getAddress());
                    setResult(0xff, intent);
                    finish();
                }
            });
            View view = helper.getView(R.id.tv_address);
            if (item.getDetail().trim().isEmpty()) {
                helper.setText(R.id.tv_location, item.getAddress())
                        .setText(R.id.tv_user, item.getUsername() + "    " + item.getTel());
                view.setVisibility(View.GONE);
            } else {
                helper.setText(R.id.tv_location, item.getAddress())
                        .setText(R.id.tv_address, item.getDetail())
                        .setText(R.id.tv_user, item.getUsername() + "    " + item.getTel());
                view.setVisibility(View.VISIBLE);
            }
        }
    }
}
