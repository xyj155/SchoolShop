package com.example.schoolshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SubmitGoodsOrderActivity extends BaseActivity {


    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    RelativeLayout toolbar;
    @InjectView(R.id.imageView4)
    ImageView imageView4;
    @InjectView(R.id.tv_location)
    TextView tvLocation;
    @InjectView(R.id.tv_tel)
    TextView tvTel;
    @InjectView(R.id.tv_choose)
    TextView tvChoose;
    @InjectView(R.id.imageView5)
    ImageView imageView5;
    @InjectView(R.id.iv_shop_cover)
    ImageView ivShopCover;
    @InjectView(R.id.tv_shop_name)
    TextView tvShopName;
    @InjectView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @InjectView(R.id.tv_attr)
    TextView tvAttr;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.iv_reduce)
    ImageView ivReduce;
    @InjectView(R.id.tv_count)
    TextView tvCount;
    @InjectView(R.id.iv_add)
    ImageView ivAdd;
    @InjectView(R.id.tv_post_free)
    TextView tvPostFree;
    @InjectView(R.id.rl_post_free)
    RelativeLayout rlPostFree;
    @InjectView(R.id.tv_coupon)
    TextView tvCoupon;
    @InjectView(R.id.rl_coupon)
    RelativeLayout rlCoupon;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.tv_pay)
    TextView tvPay;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.rl_address)
    RelativeLayout rlAddress;

    @Override
    public int intiLayout() {
        return R.layout.activity_submit_goods_order;
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
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R.id.rl_address,R.id.tv_choose, R.id.iv_reduce, R.id.iv_add, R.id.rl_post_free, R.id.rl_coupon, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_address:
                startActivityForResult(new Intent(SubmitGoodsOrderActivity.this, UserAddressListActivity.class), 0xff);
                break;
            case R.id.tv_choose:
                startActivityForResult(new Intent(SubmitGoodsOrderActivity.this, UserAddressListActivity.class), 0xff);
                break;
            case R.id.iv_reduce:
                break;
            case R.id.iv_add:
                break;
            case R.id.rl_post_free:
                break;
            case R.id.rl_coupon:
                break;
            case R.id.tv_pay:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0xff) {
            tvTel.setVisibility(View.VISIBLE);
            tvChoose.setVisibility(View.GONE);
            tvLocation.setVisibility(View.VISIBLE);
            tvLocation.setText("姓名："+data.getStringExtra("username"));
            tvAddress.setVisibility(View.VISIBLE);
            tvTel.setText("联系方式："+data.getStringExtra("tel"));
            if (data.getStringExtra("address") == null){
                tvAddress.setText("地址："+data.getStringExtra("city") );
            }else {
                tvAddress.setText("地址："+data.getStringExtra("city")+data.getStringExtra("address") );
            }
        }
    }
}
