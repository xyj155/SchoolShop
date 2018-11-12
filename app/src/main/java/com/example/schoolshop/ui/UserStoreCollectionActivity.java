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

public class UserStoreCollectionActivity extends BaseActivity implements UserCollectionContract.View {

    @InjectView(R.id.ry_collection)
    RecyclerView ryCollection;
    @InjectView(R.id.sl_collection)
    SmartRefreshLayout slCollection;
    private UserCollectionPresenter userCollectionPresenter = new UserCollectionPresenter(this);
    private StoreAdapter storeAdapter = new StoreAdapter(null);

    @Override
    public int intiLayout() {
        return R.layout.activity_user_store_collection;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        initToolBar().setToolbarBackIco().setToolbarSubtitle("店铺收藏");
        ryCollection.setLayoutManager(new LinearLayoutManager(UserStoreCollectionActivity.this));
        ryCollection.setAdapter(storeAdapter);
        storeAdapter.bindToRecyclerView(ryCollection);
        userCollectionPresenter.getUserStoreCollection("1");
        slCollection.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                userCollectionPresenter.getUserStoreCollection("1");
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
        storeAdapter.replaceData(shopGsonList);
        slCollection.finishRefresh();
    }

    @Override
    public void loadGoodsList(List<GoodGson.GoodsBean> goodGsons) {

    }

    private class StoreAdapter extends BaseQuickAdapter<ShopGson, BaseViewHolder> {

        public StoreAdapter(@Nullable List<ShopGson> data) {
            super(R.layout.ry_shop_collection_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ShopGson item) {
            helper.setText(R.id.tv_name, item.getShop_name())
                    .setOnClickListener(R.id.ll_collection, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent view = new Intent(UserStoreCollectionActivity.this, ShopDetailActivity.class);
                            view.putExtra("id", String .valueOf(item.getId()));
                            startActivity(view);
                        }
                    });
            Glide.with(UserStoreCollectionActivity.this).asBitmap().load(item.getShop_cover()).into((ImageView) helper.getView(R.id.iv_head));

        }
    }

}
