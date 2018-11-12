package com.example.schoolshop.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.CouponContract;
import com.example.schoolshop.gson.CouponGson;
import com.example.schoolshop.presenter.CouponPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CouponActivity extends BaseActivity implements CouponContract.View {


    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    RelativeLayout toolbar;
    @InjectView(R.id.ry_com)
    RecyclerView ryCom;
    @InjectView(R.id.ry_list)
    RecyclerView ryList;
    ComItemAdapter comItemAdaptem = new ComItemAdapter(null);
    @InjectView(R.id.rl_coupon)
    SmartRefreshLayout rlCoupon;
    private CouponPresenter couponPresenter = new CouponPresenter(this);
    private CouponAdapter couponAdapter = new CouponAdapter(null);
    private String kind = "";

    @Override
    public int intiLayout() {
        return R.layout.activity_coupon;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CouponActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ryCom.setLayoutManager(linearLayoutManager);
        ryList.setLayoutManager(new LinearLayoutManager(CouponActivity.this));
        ryList.setNestedScrollingEnabled(false);
        initToolBar().setToolbarBackIco().setToolbarSubtitle("优惠券");
        ryCom.setAdapter(comItemAdaptem);
        ryList.setAdapter(couponAdapter);
        rlCoupon.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                couponPresenter.getCouponListByLocation("1", kind, "嘉兴市");
            }
        });
    }

    @Override
    public void initData() {
        couponPresenter.getCouponListByLocation("1", kind, "嘉兴市");


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
        rlCoupon.finishRefresh();
        Toast.makeText(this, "获取优惠券出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadCouponData(CouponGson couponGson) {
        rlCoupon.finishRefresh();
        comItemAdaptem.replaceData(couponGson.getKinds());
        couponAdapter.replaceData(couponGson.getCoupon());
    }

    @Override
    public void loadCouponList(List<CouponGson.CouponBean> couponGson) {
        couponAdapter.replaceData(couponGson);
        rlCoupon.finishRefresh();
    }

    private class CouponAdapter extends BaseQuickAdapter<CouponGson.CouponBean, BaseViewHolder> {

        public CouponAdapter(@Nullable List<CouponGson.CouponBean> data) {
            super(R.layout.ry_conpoulist_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final CouponGson.CouponBean item) {
            helper.setText(R.id.tv_name, item.getShop_name())
                    .setText(R.id.tv_price, item.getCoupon_monney())
                    .setText(R.id.tv_kind, "优惠券：" + item.getCoupon_name())
                    .setText(R.id.tv_time, item.getCoupon_starttime() + "-" + item.getCoupon_endtime())
                    .setOnClickListener(R.id.tv_hand, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
            Glide.with(CouponActivity.this).asBitmap().load(item.getShop_pic()).into((ImageView) helper.getView(R.id.iv_head));
        }
    }

    private class ComItemAdapter extends BaseQuickAdapter<CouponGson.KindsBean, BaseViewHolder> {

        public ComItemAdapter(@Nullable List<CouponGson.KindsBean> data) {
            super(R.layout.ry_coupon_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final CouponGson.KindsBean item) {
            Glide.with(CouponActivity.this).asBitmap().load(item.getPic()).apply(new RequestOptions().error(R.mipmap.head)).into((ImageView) helper.getView(R.id.iv_cover));
            helper.setText(R.id.tv_name, item.getName())
            .setOnClickListener(R.id.ll_coupon, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    kind=item.getName();
                    couponPresenter.getCouponListByLocation("1", kind, "嘉兴市");
                }
            });
        }
    }
}
