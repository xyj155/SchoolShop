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

import com.example.schoolshop.R;
import com.example.schoolshop.adapter.ShopAdapter;
import com.example.schoolshop.base.BaseFragment;
import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.ShopCarContract;
import com.example.schoolshop.gson.UserShopCarGson;
import com.example.schoolshop.presenter.ShopCarPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.example.schoolshop.R.id.third_allselect;
import static com.example.schoolshop.R.id.third_recyclerview;
import static com.example.schoolshop.R.id.third_totalnum;
import static com.example.schoolshop.R.id.third_totalprice;

/**
 * Created by Administrator on 2018/10/31.
 */

public class ShopCarFragment extends BaseFragment implements ShopCarContract.View {
    public static ShopCarFragment instance;
    @InjectView(third_recyclerview)
    RecyclerView thirdRecyclerview;
    @InjectView(third_allselect)
    CheckBox thirdAllselect;
    @InjectView(third_totalprice)
    TextView thirdTotalprice;
    @InjectView(third_totalnum)
    TextView thirdTotalnum;
    @InjectView(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @InjectView(R.id.third_submit)
    TextView thirdSubmit;
    @InjectView(R.id.fl_order)
    FrameLayout flOrder;
    private ShopCarPresenter presenter;
    private ShopAdapter adapter;
    BaseGson<UserShopCarGson> shopBean;
    List<BaseGson<UserShopCarGson>> list = new ArrayList<>();

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
        presenter = new ShopCarPresenter(this);
        presenter.submitUserShopCar("1");
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
                thirdTotalnum.setText("共计： "+num);
                thirdTotalprice.setText("￥ "+total);
            }
        });
    }


    public void loadList() {
        Log.i(TAG, "loadList: ");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadFailed(String msg) {

    }

    private static final String TAG = "TestActivity";

    @Override
    public void loadShopCarList(BaseGson<UserShopCarGson> userShopCarGsons) {
        Log.i(TAG, "loadShopCarList: " + userShopCarGsons);
        adapter.add(userShopCarGsons);
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
}
