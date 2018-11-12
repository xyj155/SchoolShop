package com.example.schoolshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.schoolshop.view.CircleImageView;
import com.example.schoolshop.view.RatingBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ShopDetailActivity extends BaseActivity implements ShopContract.View {

    @InjectView(R.id.iv_head)
    CircleImageView ivHead;
    @InjectView(R.id.tv_username)
    TextView tvUsername;
    @InjectView(R.id.rk_seek)
    RatingBar rkSeek;
    @InjectView(R.id.tv_go)
    ImageView tvGo;
    @InjectView(R.id.iv_cover)
    ImageView ivCover;
    @InjectView(R.id.collapsing_tool_bar_test_ctl)
    CollapsingToolbarLayout collapsingToolBarTestCtl;
    @InjectView(R.id.id_appbarlayout)
    AppBarLayout idAppbarlayout;
    @InjectView(R.id.ry_list)
    RecyclerView ryList;
    //    @InjectView(R.id.myMainScrollView)
//    NestedScrollView myMainScrollView;
    @InjectView(R.id.scrollview)
    CoordinatorLayout scrollview;
    @InjectView(R.id.sl_shop)
    SmartRefreshLayout slShop;
    private GoodsAdapter goodsAdapter = new GoodsAdapter(null);
    private ShopPresenter shopPresenter = new ShopPresenter(this);

    @Override
    public int intiLayout() {
        return R.layout.activity_shop_detail;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        initToolBar().setToolbarBackIco().setToolbarSubtitle("店铺");


        ryList.setLayoutManager(new GridLayoutManager(ShopDetailActivity.this, 2));
        ryList.setNestedScrollingEnabled(false);
    }

    @Override
    public void initData() {
        slShop.autoRefresh();
        slShop.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                String id = getIntent().getStringExtra("id");
                shopPresenter.getSellerDetailById(id);
            }
        });
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
        slShop.finishRefresh();
        Toast.makeText(this, "请求失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadShopList(List<ShopGson> shopGsonList) {

    }

    @Override
    public void loadShopDetail(ShopGson shopGsonList) {
        slShop.finishRefresh();
        tvUsername.setText(shopGsonList.getShop_name());
        rkSeek.setSelectedNumber(Integer.valueOf(shopGsonList.getReputation()));
        Glide.with(ShopDetailActivity.this).load(shopGsonList.getBanner()).into(ivCover);
        Glide.with(ShopDetailActivity.this).load(shopGsonList.getShop_cover()).into(ivHead);
        goodsAdapter.replaceData(shopGsonList.getGoods());
        ryList.setAdapter(goodsAdapter);
    }

    private class GoodsAdapter extends BaseQuickAdapter<ShopGson.GoodsBean, BaseViewHolder> {

        public GoodsAdapter(List<ShopGson.GoodsBean> data) {
            super(R.layout.ry_store_goods_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final ShopGson.GoodsBean item) {
            helper.setText(R.id.tv_name, item.getGoods_name())
                    .setText(R.id.tv_price,"￥ "+ item.getGoods_price())
            .setOnClickListener(R.id.ll_goods, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(ShopDetailActivity.this, GoodsDetailActivity.class);
                    intent.putExtra("id", String.valueOf(item.getId()));
                    intent.putExtra("kind", String.valueOf(item.getGoods_kind()));
                    mContext.startActivity(intent);
                }
            });
            Glide.with(ShopDetailActivity.this).load(item.getGoods_pic()).apply(new RequestOptions().transform(new GlideRoundTransform(ShopDetailActivity.this, 7))).into((ImageView) helper.getView(R.id.iv_cover));
        }
    }
}
