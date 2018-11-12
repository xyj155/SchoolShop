package com.example.schoolshop.ui.homefragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseFragment;
import com.example.schoolshop.contract.HomeContract;
import com.example.schoolshop.gson.BannerGson;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.presenter.HomePresenter;
import com.example.schoolshop.ui.CaptureActivity;
import com.example.schoolshop.ui.ConversationListActivity;
import com.example.schoolshop.ui.CouponActivity;
import com.example.schoolshop.ui.GoodsDetailActivity;
import com.example.schoolshop.ui.PostDeliverActivity;
import com.example.schoolshop.ui.RechargeActivity;
import com.example.schoolshop.ui.RunHelperOrderActivity;
import com.example.schoolshop.ui.SecondSellerActivity;
import com.example.schoolshop.ui.ShopListActivity;
import com.example.schoolshop.util.GlideRoundTransform;
import com.example.schoolshop.view.banner.AutoScrollViewPager;
import com.example.schoolshop.view.banner.BaseViewPagerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/10/31.
 */

public class HomeFragment extends BaseFragment implements HomeContract.View {

    TextView ivScan;
    @InjectView(R.id.iv_purse)
    TextView ivPurse;
    @InjectView(R.id.iv_seller)
    TextView ivSeller;
    @InjectView(R.id.ry_hot)
    RecyclerView ryHot;
    @InjectView(R.id.sl_home)
    SmartRefreshLayout slHome;
    @InjectView(R.id.tv_run)
    TextView tvRun;
    @InjectView(R.id.tv_recharge)
    TextView tvRecharge;
    @InjectView(R.id.tv_ticket)
    TextView tvTicket;
    @InjectView(R.id.tv_helper)
    TextView tvHelper;
    @InjectView(R.id.tv_deliver)
    TextView tvDeliver;
    @InjectView(R.id.tv_message)
    TextView tvMessage;
    @InjectView(R.id.banner_home)
    AutoScrollViewPager bannerHome;
    private BaseViewPagerAdapter<String> mBaseViewPagerAdapter;
    private HomePresenter homePresenter = new HomePresenter(this);
    private GoodsAdapter adapter;
    private List<GoodGson.GoodsBean> goodsList = new ArrayList<>();

    @Override
    protected int setView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(View view) {
        ButterKnife.inject(this, view);
        bannerHome = view.findViewById(R.id.banner_home);
        adapter = new GoodsAdapter(goodsList, getActivity());
        adapter.bindToRecyclerView(ryHot);
        ryHot.setNestedScrollingEnabled(false);
        ryHot.setLayoutManager(new LinearLayoutManager(getContext()));
        ryHot.setAdapter(adapter);
        mBaseViewPagerAdapter = new BaseViewPagerAdapter<String>(getContext(), listener) {
            @Override
            public void loadImage(ImageView view, int position, String url) {
                Glide.with(getContext()).asBitmap().load(url).into(view);
            }

            @Override
            public void setSubTitle(TextView textView, int position, String s) {

            }
        };
        bannerHome.setAdapter(mBaseViewPagerAdapter);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bannerHome != null) {
            bannerHome.onDestroy();
        }

    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        slHome.autoRefresh();
        slHome.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                homePresenter.getHomeData("嘉兴");
            }
        });
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
        loadFailed();
        slHome.finishRefresh();
        Toast.makeText(getContext(), "数据加载失败，请重试！", Toast.LENGTH_SHORT).show();
    }

    private static final String TAG = "HomeFragment";
    private BaseViewPagerAdapter.OnAutoViewPagerItemClickListener listener = new BaseViewPagerAdapter.OnAutoViewPagerItemClickListener<String>() {

        @Override
        public void onItemClick(int position, String url) {
            Toast.makeText(getContext(),
                    position + " ========= " + url, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void loadHomeData(List<BannerGson> bannerGsons, List<GoodGson.GoodsBean> goodGsons) {
        slHome.finishRefresh();
        List<String> data = new ArrayList<>();
        for (int i = 0; i < bannerGsons.size(); i++) {
            data.add(bannerGsons.get(i).getImg_url());
        }
        Log.i(TAG, "loadHomeData: " + data.size());
        Log.i(TAG, "loadHomeData: " + goodGsons.size());
        mBaseViewPagerAdapter.add(data);
        goodsList.clear();
        goodsList.addAll(goodGsons);
        adapter.replaceData(goodsList);
        ryHot.setAdapter(adapter);
    }


    @OnClick({R.id.tv_run, R.id.tv_recharge, R.id.tv_ticket, R.id.tv_helper, R.id.tv_deliver, R.id.tv_message, R.id.iv_purse, R.id.iv_scan, R.id.iv_seller})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_purse:
                break;
            case R.id.iv_scan:
                startActivity(new Intent(getContext(), CaptureActivity.class));
                break;
            case R.id.iv_seller:
                startActivity(new Intent(getContext(), ShopListActivity.class));
                break;
            case R.id.tv_run:
                startActivity(new Intent(getContext(), RunHelperOrderActivity.class));
                break;
            case R.id.tv_recharge:
                startActivity(new Intent(getContext(), RechargeActivity.class));
                break;
            case R.id.tv_ticket:
                startActivity(new Intent(getContext(), CouponActivity.class));

                break;
            case R.id.tv_helper:
                startActivity(new Intent(getContext(), SecondSellerActivity.class));
                break;
            case R.id.tv_deliver:
                startActivity(new Intent(getContext(), PostDeliverActivity.class));
                break;
            case R.id.tv_message:
                startActivity(new Intent(getContext(), ConversationListActivity.class));
                break;
        }
    }

    private class GoodsAdapter extends BaseQuickAdapter<GoodGson.GoodsBean, BaseViewHolder> {
        private Context context;


        public GoodsAdapter(@Nullable List<GoodGson.GoodsBean> data, Context context) {
            super(R.layout.ry_purse_goods_list_item, data);
            this.context = context;

        }


        @Override
        protected void convert(final BaseViewHolder helper, final GoodGson.GoodsBean item) {
            helper.setText(R.id.tv_name, item.getGoods_name())
                    .setText(R.id.tv_price, "￥" + item.getGoods_price())
                    .setOnClickListener(R.id.ll_item, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, GoodsDetailActivity.class);
                            intent.putExtra("id", String.valueOf(item.getId()));
                            intent.putExtra("kind", String.valueOf(item.getGoods_kind()));
                            context.startActivity(intent);
                        }
                    });
            Glide.with(context).load(item.getGoods_pic()).apply(new RequestOptions().transform(new GlideRoundTransform(context, 7))).into((ImageView) helper.getView(R.id.iv_cover));
            String[] split = item.getGoods_tags().split(",");
            List<String> stringB = Arrays.asList(split);
            TagAdapter tagAdapter = new TagAdapter(stringB);
            RecyclerView view = helper.getView(R.id.ry_tags);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            view.setLayoutManager(linearLayoutManager);
            view.setAdapter(tagAdapter);

        }

        private class TagAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

            public TagAdapter(@Nullable List<String> data) {
                super(R.layout.ry_goods_tag_item, data);
            }

            @Override
            protected void convert(BaseViewHolder helper, String item) {
                if (helper.getPosition() == 0) {
                    helper.setBackgroundRes(R.id.tv_tag, R.drawable.goods_item_tag_hot)
                            .setTextColor(R.id.tv_tag, context.getResources().getColor(R.color.red));
                } else if (helper.getPosition() == 1) {
                    helper.setBackgroundRes(R.id.tv_tag, R.drawable.goods_item_tag_new)
                            .setTextColor(R.id.tv_tag, context.getResources().getColor(R.color.blue));
                }
                if (helper.getPosition() == 2) {
                    helper.setBackgroundRes(R.id.tv_tag, R.drawable.goods_item_tag_super)
                            .setTextColor(R.id.tv_tag, context.getResources().getColor(R.color.yellow));
                }
                helper.setText(R.id.tv_tag, item);
            }
        }
    }
}
