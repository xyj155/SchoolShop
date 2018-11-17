package com.example.schoolshop.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.UserSubmitOrderContract;
import com.example.schoolshop.gson.SubmitOrderGson;
import com.example.schoolshop.presenter.UserSubmitOrderPresenter;
import com.payelves.sdk.EPay;
import com.payelves.sdk.enums.EPayResult;
import com.payelves.sdk.listener.PayResultListener;

import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SubmitGoodsOrderActivity extends BaseActivity implements UserSubmitOrderContract.View {


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
    @InjectView(R.id.iv_good_cover)
    ImageView ivGoodCover;
    private UserSubmitOrderPresenter userSubmitOrderPresenter = new UserSubmitOrderPresenter(this);
    DecimalFormat df = new DecimalFormat("#.00");

    @Override
    public int intiLayout() {
        return R.layout.activity_submit_goods_order;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        tvAttr.setText(getIntent().getStringExtra("attr"));
        tvPrice.setText(getIntent().getStringExtra("price"));
        initToolBar().setToolbarBackIco().setToolbarSubtitle("付款");
    }

    @Override
    public void initData() {
        userSubmitOrderPresenter.getOrderInformation(getIntent().getStringExtra("id"), "1");
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

    private int num = 1;

    @OnClick({R.id.rl_address, R.id.tv_choose, R.id.iv_reduce, R.id.iv_add, R.id.rl_post_free, R.id.rl_coupon, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_address:
                startActivityForResult(new Intent(SubmitGoodsOrderActivity.this, UserAddressListActivity.class), 0xff);
                break;
            case R.id.tv_choose:
                startActivityForResult(new Intent(SubmitGoodsOrderActivity.this, UserAddressListActivity.class), 0xff);
                break;
            case R.id.iv_reduce:
                if (num > 1) {
                    tvCount.setText(String.valueOf(num--));
                } else {
                    tvCount.setText("1");
                }
                break;
            case R.id.iv_add:
                tvCount.setText(String.valueOf(num++));
                break;
            case R.id.rl_post_free:
                break;
            case R.id.rl_coupon:
                break;
            case R.id.tv_pay:
                double money = Double.valueOf(tvMoney.getText().toString().replace("￥", "")) * 100;//(new Double(money)).intValue()
                Log.i(TAG, "onViewClicked: ");
                if (tvAddress.getText().toString().isEmpty()) {
                    Toast.makeText(this, "亲，你还没有选择地址哦！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i(TAG, "onViewClicked: getInten" + getIntent().getStringExtra("oid"));
                    Log.i(TAG, "onViewClicked: tvLocati" + tvLocation.getText().toString());
                    Log.i(TAG, "onViewClicked: tvAddres" + tvAddress.getText().toString());
                    Log.i(TAG, "onViewClicked: tvTel.ge" + tvTel.getText().toString());
                    Log.i(TAG, "onViewClicked: tvCount." + tvCount.getText().toString());
                    Log.i(TAG, "onViewClicked: tvMoney." + tvMoney.getText().toString().replace("￥", ""));

                    EPay.getInstance(SubmitGoodsOrderActivity.this).pay(tvGoodsName.getText().toString(), "商品购买", (new Double(money)).intValue(),
                            "", "", "", new PayResultListener() {
                                @Override
                                public void onFinish(Context context, Long payId, String orderId, String payUserId,
                                                     EPayResult payResult, int payType, Integer amount) {
                                    EPay.getInstance(context).closePayView();//关闭快捷支付页面
                                    if (payResult.getCode() == EPayResult.SUCCESS_CODE.getCode()) {
                                        userSubmitOrderPresenter.userPayGoodsOrder(getIntent().getStringExtra("oid")
                                                , tvLocation.getText().toString() + tvAddress.getText().toString(), tvTel.getText().toString(),
                                                tvCount.getText().toString(),
                                                tvMoney.getText().toString().replace("￥", "")
                                        );
                                    } else if (payResult.getCode() == EPayResult.FAIL_CODE.getCode()) {
                                        //支付失败逻辑处理
                                        Toast.makeText(SubmitGoodsOrderActivity.this, payResult.getMsg(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 0xff) {
                tvTel.setVisibility(View.VISIBLE);
                tvChoose.setVisibility(View.GONE);
                tvLocation.setVisibility(View.VISIBLE);
                tvLocation.setText("姓名：" + data.getStringExtra("username"));
                tvAddress.setVisibility(View.VISIBLE);
                tvTel.setText("联系方式：" + data.getStringExtra("tel"));
                if (data.getStringExtra("address") == null) {
                    tvAddress.setText("地址：" + data.getStringExtra("city"));
                } else {
                    tvAddress.setText("地址：" + data.getStringExtra("city") + data.getStringExtra("address"));
                }
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
        Toast.makeText(this, "数据加载错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getOrderDetail(final SubmitOrderGson orderGson) {
        Log.i(TAG, "getOrderDetail: " + orderGson);
        Glide.with(SubmitGoodsOrderActivity.this).asBitmap().load(orderGson.getGoods().getGoods_pic()).into(ivGoodCover);
        tvGoodsName.setText(orderGson.getGoods().getGoods_name());
        Glide.with(SubmitGoodsOrderActivity.this).asBitmap().load(orderGson.getShop().getShop_cover()).into(ivShopCover);
        tvShopName.setText(orderGson.getShop().getShop_name());
        tvPostFree.setText("快递费：" + orderGson.getGoods().getPost_free());
        if (orderGson.getCoupon() == null) {
            tvCoupon.setText("无优惠券");
        }
        Double price = Double.valueOf(getIntent().getStringExtra("price").replace("￥", "")) + Double.valueOf(orderGson.getGoods().getPost_free());
        tvMoney.setText("￥" + String.valueOf(String.valueOf(df.format(price))));
        tvCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String singMoney = tvPrice.getText().toString().replace("￥", "");
                double v = Integer.valueOf(tvCount.getText().toString()) * Double.valueOf(singMoney) + Double.valueOf(orderGson.getGoods().getPost_free());
                tvMoney.setText("￥" + String.valueOf(df.format(v)));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void paySuccess() {
        Toast.makeText(this, "下单成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SubmitGoodsOrderActivity.this, UserPaymentProcessActivity.class);
        intent.putExtra("index", 1);
        startActivity(intent);
        finish();
    }

    @Override
    public void payFailed() {
        Toast.makeText(this, "下单失败", Toast.LENGTH_SHORT).show();
    }
}
