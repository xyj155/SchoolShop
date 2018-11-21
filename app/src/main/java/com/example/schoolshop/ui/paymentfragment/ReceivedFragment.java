package com.example.schoolshop.ui.paymentfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseFragment;
import com.example.schoolshop.contract.UserOrderContract;
import com.example.schoolshop.gson.UserOrderFormAllListGson;
import com.example.schoolshop.presenter.UserOrderPresenter;
import com.payelves.sdk.EPay;
import com.payelves.sdk.enums.EPayResult;
import com.payelves.sdk.listener.PayResultListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 徐易杰 on 2018/11/5.
 */

public class ReceivedFragment extends BaseFragment implements UserOrderContract.View {
    @InjectView(R.id.ry_order)
    RecyclerView ryOrder;
    @InjectView(R.id.sl_order)
    SmartRefreshLayout slOrder;
    private UserOrderPresenter orderPresenter = new UserOrderPresenter(this);
    private OrderAdapter orderAdapter;

    @Override
    protected int setView() {
        return R.layout.fragment_receive;
    }

    @Override
    protected void init(View view) {
        ButterKnife.inject(this, view);
        orderAdapter = new OrderAdapter(null, getContext());
        orderPresenter.getUserOrdersList("1", "3");
        slOrder.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                orderPresenter.getUserOrdersList("1", "3");
            }
        });
        ryOrder.setLayoutManager(new LinearLayoutManager(getContext()));
        orderAdapter.bindToRecyclerView(ryOrder);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void showLoading() {
        showDialog();
    }

    @Override
    public void hideLoading() {
        loadSuccess();
    }

    @Override
    public void loadFailed(String msg) {
        if (slOrder!=null){
            slOrder.finishRefresh();
        }
        Toast.makeText(getContext(), "订单拉取错误", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void loadOrderList(List<UserOrderFormAllListGson> userOrderFormAllListGsons) {
        if (slOrder!=null){
            slOrder.finishRefresh();
        }
        orderAdapter.replaceData(userOrderFormAllListGsons);
        ryOrder.setAdapter(orderAdapter);
    }

    @Override
    public void loadUserCount(List<Integer> list) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    private class OrderAdapter extends BaseQuickAdapter<UserOrderFormAllListGson, BaseViewHolder> {
        private Context context;

        public OrderAdapter(@Nullable List<UserOrderFormAllListGson> data, Context context) {
            super(R.layout.ry_user_order_list_item, data);
            this.context = context;
        }

        @Override
        protected void convert(BaseViewHolder helper, final UserOrderFormAllListGson item) {
            GoodsItemAdater goodsItemAdater = new GoodsItemAdater(item.getGoods());
            RecyclerView view = helper.getView(R.id.ry_goods_item);
            view.setLayoutManager(new LinearLayoutManager(getContext()));
            final DecimalFormat df = new DecimalFormat("#.00");
            view.setAdapter(goodsItemAdater);
//            final double v = Double.valueOf(item.getGoods_price()) * item.getNum() + 15;
//            helper.setText(R.id.tv_total, "共计：" + df.format(v))
            helper.setText(R.id.tv_shop_name, item.getShop_name())
                    .setText(R.id.tv_shop_name, item.getShop_name())
                    .setOnClickListener(R.id.tv_delete, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setOnClickListener(R.id.tv_pay, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            double v1 = Double.valueOf(item.getGoods().get(0).getGoods_price()) * item.getGoods().get(0).getNum() + 15;
                            EPay.getInstance(context).pay("商品购物", "商品", (Double.valueOf(v1 * 100)).intValue(),
                                    "", "", "", new PayResultListener() {

                                        @Override
                                        public void onFinish(Context context, Long payId, String orderId, String payUserId,
                                                             EPayResult payResult, int payType, Integer amount) {
                                            EPay.getInstance(context).closePayView();//关闭快捷支付页面
                                            if (payResult.getCode() == EPayResult.SUCCESS_CODE.getCode()) {
                                                //支付成功逻辑处理
                                                Toast.makeText(context, payResult.getMsg(), Toast.LENGTH_LONG).show();
                                            } else if (payResult.getCode() == EPayResult.FAIL_CODE.getCode()) {
                                                //支付失败逻辑处理
                                                Toast.makeText(context, payResult.getMsg(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }
                    });
            switch (item.getStatus()) {
                case 0:
                    helper.setText(R.id.tv_status, "订单取消");
                    break;
                case 1:
                    helper.setText(R.id.tv_status, "待付款");
                    break;
                case 2:
                    helper.setText(R.id.tv_status, "等待卖家发货");
                    break;
                case 3:
                    helper.setText(R.id.tv_status, "运输中");
                    break;
                case 4:
                    helper.setText(R.id.tv_status, "已到货");
                    break;
            }
            Glide.with(context).load(item.getShop_cover()).apply(new RequestOptions().transform(new RoundedCorners(7))).into((ImageView) helper.getView(R.id.iv_head));
            double price = 0;
            for (int i = 0; i < item.getGoods().size(); i++) {
                price += Double.valueOf(item.getGoods().get(i).getGoods_price()) * item.getGoods().get(i).getNum();
                Log.i(TAG, "convert: "+item.getGoods().get(i).getGoods_price());
            }
            final double v = price + 15;
            helper.setText(R.id.tv_price, "共计：￥" + df.format(v));
        }
    }

    private class GoodsItemAdater extends BaseQuickAdapter<UserOrderFormAllListGson.GoodsBean, BaseViewHolder> {

        public GoodsItemAdater(@Nullable List<UserOrderFormAllListGson.GoodsBean> data) {
            super(R.layout.ry_goods_handing_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, UserOrderFormAllListGson.GoodsBean item) {
            helper.setText(R.id.tv_comment, item.getComment() == null ? "" : item.getComment())
                    .setText(R.id.tv_goods_name, item.getGoods_name())
                    .setText(R.id.tv_num, "数量： " + item.getNum() + "")
                    .setText(R.id.tv_price, "￥ " + item.getGoods_price() + "");
            Glide.with(getContext()).load(item.getGoods_pic()).apply(new RequestOptions().transform(new RoundedCorners(7))).into((ImageView) helper.getView(R.id.imageView3));

        }
    }
}
