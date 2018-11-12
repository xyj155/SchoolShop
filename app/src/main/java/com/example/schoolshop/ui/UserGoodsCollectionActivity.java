package com.example.schoolshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.UserCollectionContract;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.gson.ShopGson;
import com.example.schoolshop.presenter.UserCollectionPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UserGoodsCollectionActivity extends BaseActivity implements UserCollectionContract.View {

    @InjectView(R.id.ry_collection)
    RecyclerView ryCollection;
    @InjectView(R.id.sl_collection)
    SmartRefreshLayout slCollection;
    private UserCollectionPresenter userCollectionPresenter = new UserCollectionPresenter(this);
    private StoreAdapter storeAdapter = new StoreAdapter(null);

    @Override
    public int intiLayout() {
        return R.layout.activity_user_goods_collection;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        initToolBar().setToolbarBackIco().setToolbarSubtitle("商品收藏");
        ryCollection.setLayoutManager(new LinearLayoutManager(UserGoodsCollectionActivity.this));
        ryCollection.setAdapter(storeAdapter);
        storeAdapter.bindToRecyclerView(ryCollection);
        userCollectionPresenter.getUserGoodsCollection("1");
        slCollection.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                userCollectionPresenter.getUserGoodsCollection("1");
            }
        });
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
        slCollection.finishRefresh();
        Toast.makeText(this, "获取收藏出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadShopList(List<ShopGson> shopGsonList) {

    }

    @Override
    public void loadGoodsList(List<GoodGson.GoodsBean> goodGsons) {
        storeAdapter.replaceData(goodGsons);
        slCollection.finishRefresh();

    }



    private class StoreAdapter extends BaseQuickAdapter<GoodGson.GoodsBean, BaseViewHolder> {

        public StoreAdapter(@Nullable List<GoodGson.GoodsBean> data) {
            super(R.layout.ry_shop_goods_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final GoodGson.GoodsBean item) {
            helper.setText(R.id.tv_name, item.getGoods_name())
                    .setOnClickListener(R.id.ll_collection, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent view = new Intent(UserGoodsCollectionActivity.this, GoodsDetailActivity.class);
                            view.putExtra("id", String .valueOf(item.getId()));
                            view.putExtra("kind", String .valueOf(item.getKind()));
                            startActivity(view);
                        }
                    });
            Glide.with(UserGoodsCollectionActivity.this).asBitmap().load(item.getGoods_pic()).into((ImageView) helper.getView(R.id.iv_head));

        }
    }
}
