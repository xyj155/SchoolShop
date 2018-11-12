package com.example.schoolshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.ShopContract;
import com.example.schoolshop.gson.ShopGson;
import com.example.schoolshop.presenter.ShopPresenter;
import com.example.schoolshop.util.GlideRoundTransform;
import com.example.schoolshop.view.RatingBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShopListActivity extends BaseActivity implements ShopContract.View {
    @InjectView(R.id.toolbar)
    RelativeLayout toolbar;
    @InjectView(R.id.ry_seller)
    RecyclerView rySeller;
    @InjectView(R.id.sl_shop)
    SmartRefreshLayout slShop;
    private ShopPresenter shopPresenter = new ShopPresenter(this);
    private ShopAdapter shopAdapter = new ShopAdapter(null);

    @Override
    public int intiLayout() {
        return R.layout.activity_seller_list;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        rySeller.setLayoutManager(new LinearLayoutManager(ShopListActivity.this));
        slShop.autoRefresh();
        initToolBar().setToolbarBackIco().setToolbarSubtitle("附近商家");

    }

    @Override
    public void initData() {
        slShop.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                shopPresenter.getSellerList("嘉兴");
            }
        });
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
        Toast.makeText(this, "请求出错", Toast.LENGTH_SHORT).show();
        slShop.finishRefresh();
    }

    @Override
    public void loadShopList(List<ShopGson> shopGsonList) {
        slShop.finishRefresh();
        shopAdapter.replaceData(shopGsonList);
        rySeller.setAdapter(shopAdapter);

    }

    @Override
    public void loadShopDetail(ShopGson shopGsonList) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    private class ShopAdapter extends BaseQuickAdapter<ShopGson, BaseViewHolder> {

        public ShopAdapter(@Nullable List<ShopGson> data) {
            super(R.layout.ry_shop_list_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ShopGson item) {
            helper.setText(R.id.tv_name, item.getShop_name())
                    .setOnClickListener(R.id.ll_shop, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ShopListActivity.this, ShopDetailActivity.class);
                            intent.putExtra("id", String .valueOf(item.getId()));
                            startActivity(intent);
                        }
                    });
            Glide.with(ShopListActivity.this).load(item.getShop_cover()).into((ImageView) helper.getView(R.id.civ_head));
            RecyclerView view = helper.getView(R.id.ry_shop);
            view.setLayoutManager(new GridLayoutManager(ShopListActivity.this, 3));
            ShopPicListAdapter shopPicList = new ShopPicListAdapter(item.getGoods());
            view.setAdapter(shopPicList);
            RatingBar view1 = helper.getView(R.id.rk_seek);
            view1.setSelectedNumber(Integer.valueOf(item.getReputation()));
        }
    }

    private class ShopPicListAdapter extends BaseQuickAdapter<ShopGson.GoodsBean, BaseViewHolder> {

        public ShopPicListAdapter(@Nullable List<ShopGson.GoodsBean> data) {
            super(R.layout.ry_shop_pic_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ShopGson.GoodsBean item) {
            helper.setText(R.id.tv_name, item.getGoods_name())
            .setOnClickListener(R.id.tv_cover, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShopListActivity.this, GoodsDetailActivity.class);
                    intent.putExtra("id", String.valueOf(item.getId()));
                    intent.putExtra("kind", String.valueOf(item.getGoods_kind()));
                    startActivity(intent);
                }
            });
            Glide.with(ShopListActivity.this).load(item.getGoods_pic()).apply(new RequestOptions().transform(new GlideRoundTransform(ShopListActivity.this, 9))).into((ImageView) helper.getView(R.id.tv_cover));
        }
    }
}
