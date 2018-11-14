package com.example.schoolshop.ui.homefragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolshop.R;
import com.example.schoolshop.adapter.ShopAdapter;
import com.example.schoolshop.base.BaseFragment;
import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.UserShopCarContract;
import com.example.schoolshop.gson.UserShopCarGson;
import com.example.schoolshop.presenter.UserShopCarPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2018/10/31.
 */

public class ShopCarFragment extends BaseFragment implements UserShopCarContract.View {
    public static ShopCarFragment instance;
    @InjectView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @InjectView(R.id.third_submit)
    TextView thirdSubmit;
    @InjectView(R.id.fl_order)
    FrameLayout flOrder;
    @InjectView(R.id.third_recyclerview)
    RecyclerView thirdRecyclerview;
    @InjectView(R.id.sl_shopcar)
    SmartRefreshLayout slShopcar;
    @InjectView(R.id.third_allselect)
    CheckBox thirdAllselect;
    @InjectView(R.id.third_totalprice)
    TextView thirdTotalprice;
    @InjectView(R.id.third_totalnum)
    TextView thirdTotalnum;
    UserShopCarPresenter  presenter = new UserShopCarPresenter(this);
    private ShopAdapter adapter;
    private ShopCarFragment shopCarFragment;

    public ShopCarFragment getShopCarFragment() {
        if (shopCarFragment == null) {
            shopCarFragment = new ShopCarFragment();
        }
        return shopCarFragment;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            Log.i(TAG, "onHiddenChanged: ");
            presenter.submitUserShopCarWithoutDialog("1");
        }
    }

    public static ShopCarFragment getInstance() {
        if (instance == null) {
            return new ShopCarFragment();
        }
        return instance;

    }

    @Override
    protected int setView() {
        return R.layout.fragment_shopcar;
    }


    @Override
    protected void init(View view) {
        ButterKnife.inject(this, view);
        slShopcar.autoRefresh();
        slShopcar.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                presenter.submitUserShopCar("1");
            }
        });
        //线性布局
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        thirdRecyclerview.setLayoutManager(manager);
        //绑定适配器
        adapter = new ShopAdapter(getActivity());
        thirdRecyclerview.setAdapter(adapter);

        thirdAllselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.selectAll(thirdAllselect.isChecked());
            }
        });
        adapter.setListener(new ShopAdapter.UpdateUiListener() {
            @Override
            public void setTotal(String total, String num, boolean allCheck) {
                thirdAllselect.setChecked(allCheck);
                thirdTotalnum.setText("共计： " + num);
                thirdTotalprice.setText("￥ " + total);
            }
        });
    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    private static final String TAG = "TestActivity";

    @Override
    public void loadShopCarList(BaseGson<UserShopCarGson> userShopCarGsons) {
        Log.i(TAG, "loadShopCarList: " + userShopCarGsons);
        adapter.add(userShopCarGsons);
        slShopcar.finishRefresh();
    }

    @Override
    public void loadShopCarListWithoutDialog(BaseGson<UserShopCarGson> shopCarGsonBaseGson) {
        adapter.add(shopCarGsonBaseGson);
        slShopcar.finishRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);


        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
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
        slShopcar.finishRefresh();
        Toast.makeText(getActivity(), "购物车加载错误", Toast.LENGTH_SHORT).show();
    }


}
