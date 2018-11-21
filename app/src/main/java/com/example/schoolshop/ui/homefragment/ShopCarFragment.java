package com.example.schoolshop.ui.homefragment;

import android.content.Context;
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
import com.example.schoolshop.contract.GoodCarContract;
import com.example.schoolshop.contract.UserShopCarContract;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.gson.UserShopCarGson;
import com.example.schoolshop.presenter.UserShopCarPresenter;
import com.example.schoolshop.view.SelfDialog;
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
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/31.
 */

public class ShopCarFragment extends BaseFragment implements UserShopCarContract.View, GoodCarContract.View {
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
    UserShopCarPresenter presenter = new UserShopCarPresenter(this);
    private ShopAdapter adapter;
    private SelfDialog selfDialog;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
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

    private String StrTotal;
    private String StrNum;
    private int StrShopCount;

    @Override
    protected void init(View view) {
        ButterKnife.inject(this, view);
        slShopcar.autoRefresh();
        slShopcar.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                presenter.submitUserShopCar("1");
                thirdAllselect.setChecked(false);
                thirdTotalnum.setText("0");
                thirdTotalprice.setText("￥ 0.00");
            }
        });
        //线性布局
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        thirdRecyclerview.setLayoutManager(manager);
        //绑定适配器
        adapter = new ShopAdapter(ShopCarFragment.this);
        thirdRecyclerview.setAdapter(adapter);

        thirdAllselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.selectAll(thirdAllselect.isChecked());
            }
        });
        adapter.setListener(new ShopAdapter.UpdateUiListener() {
            @Override
            public void setTotal(String total, String num, int shopCount, boolean allCheck) {
                if (num!=null){
                    thirdAllselect.setChecked(allCheck);
                    thirdTotalnum.setText("共计： " + num);
                    double v = Double.valueOf(total) + 15 * shopCount;
                    String price = df.format(v);
                    thirdTotalprice.setText("￥ " + price);
                    StrTotal = total;
                    StrNum = num;
                    StrShopCount = shopCount;
                }
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


    @Override
    public void addSuccess() {

    }

    @Override
    public void delSuccess() {

    }

    @Override
    public void addFailed() {

    }

    @Override
    public void loadShopCarList(List<GoodGson.GoodsBean> goodsBeen) {

    }

    @Override
    public void delShopCarSuccess() {

    }

    @Override
    public void delShopCarFailed() {

    }

    DecimalFormat df = new DecimalFormat("#.00");

    @OnClick(R.id.third_submit)
    public void onViewClicked() {
        if (StrNum == null && StrTotal == null) {
            Toast.makeText(getActivity(), "请勾选商品", Toast.LENGTH_SHORT).show();
        } else {
            selfDialog = new SelfDialog(getActivity());
            selfDialog.setTitle("提示");
            final double v = Double.valueOf(StrTotal) + 15 * StrShopCount;
            String Totalprice = df.format(v);
            Log.i(TAG, "onViewClicked: " + StrTotal);
            selfDialog.setMessage("亲！你商品消费 ￥" + StrTotal + "元 ，共" + StrNum + "件商品，邮费 3*15:" + 15 * StrShopCount + "元，总消费" + Totalprice + "元！是否继续支付？");
            selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
                @Override
                public void onYesClick() {//
                    EPay.getInstance(getActivity()).pay("商品购物", "商品", (new Double(v * 100)).intValue(),
                            "", "", "", new PayResultListener() {

                                @Override
                                public void onFinish(Context context, Long payId, String orderId, String payUserId,
                                                     EPayResult payResult, int payType, Integer amount) {
                                    EPay.getInstance(context).closePayView();//关闭快捷支付页面
                                    if (payResult.getCode() == EPayResult.SUCCESS_CODE.getCode()) {
                                        //支付成功逻辑处理
                                        Toast.makeText(getActivity(), payResult.getMsg(), Toast.LENGTH_LONG).show();
                                    } else if (payResult.getCode() == EPayResult.FAIL_CODE.getCode()) {
                                        //支付失败逻辑处理
                                        Toast.makeText(getActivity(), payResult.getMsg(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            });
            selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
                @Override
                public void onNoClick() {
                    selfDialog.dismiss();
                }
            });
            selfDialog.show();
        }
    }
}
