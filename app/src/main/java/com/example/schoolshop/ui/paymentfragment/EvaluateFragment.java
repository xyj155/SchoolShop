package com.example.schoolshop.ui.paymentfragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseFragment;
import com.example.schoolshop.contract.UserOrderContract;
import com.example.schoolshop.gson.UserOrderFormAllListGson;
import com.example.schoolshop.presenter.UserOrderPresenter;
import com.example.schoolshop.ui.UserOrderListActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 徐易杰 on 2018/11/5.
 */

public class EvaluateFragment extends BaseFragment implements UserOrderContract.View {
    @InjectView(R.id.ry_order)
    RecyclerView ryOrder;
    @InjectView(R.id.sl_order)
    SmartRefreshLayout slOrder;
    private UserOrderPresenter orderPresenter = new UserOrderPresenter(this);
    private UserOrderListActivity.OrderAdapter orderAdapter;

    @Override
    protected int setView() {
        return R.layout.fragment_evluate;
    }

    @Override
    protected void init(View view) {
        ButterKnife.inject(this, view);
        orderAdapter = new UserOrderListActivity.OrderAdapter(null, getContext());
        orderPresenter.getUserOrdersList("1", "4");
        slOrder.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                orderPresenter.getUserOrdersList("1", "4");
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
        slOrder.finishRefresh();
        Toast.makeText(getContext(), "订单拉取错误", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void loadOrderList(List<UserOrderFormAllListGson> userOrderFormAllListGsons) {
        slOrder.finishRefresh();
        orderAdapter.replaceData(userOrderFormAllListGsons);
        ryOrder.setAdapter(orderAdapter);
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
}
