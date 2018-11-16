package com.example.schoolshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.HotGoodsContract;
import com.example.schoolshop.gson.HotGoodsGson;
import com.example.schoolshop.presenter.HotGoodsPresenter;
import com.example.schoolshop.util.GlideRoundTransform;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PurseGoodsActivity extends BaseActivity implements HotGoodsContract.View {

    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    RelativeLayout toolbar;
    @InjectView(R.id.ry_purse)
    RecyclerView ryPurse;
    @InjectView(R.id.sl_purse)
    SmartRefreshLayout slPurse;
    private HotGoodsPresenter hotGoodsPresenter = new HotGoodsPresenter(this);
    private PurseGoodsAdapter purseGoodsAdapter = new PurseGoodsAdapter(null);

    @Override
    public int intiLayout() {
        return R.layout.activity_purse_goods;
    }

    @Override
    public void initView() {
        initToolBar().setToolbarBackIco().setToolbarSubtitle("推荐产品");
        ButterKnife.inject(this);
        ryPurse.setLayoutManager(new LinearLayoutManager(PurseGoodsActivity.this));
        ryPurse.setNestedScrollingEnabled(false);
        slPurse.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                hotGoodsPresenter.getHotGoodsList();
            }
        });

    }

    @Override
    public void initData() {
        hotGoodsPresenter.getHotGoodsList();
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
        slPurse.finishRefresh();
        Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadHotGoodsList(List<HotGoodsGson> list) {
        purseGoodsAdapter.replaceData(list);
        slPurse.finishRefresh();
        ryPurse.setAdapter(purseGoodsAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    public class PurseGoodsAdapter extends BaseQuickAdapter<HotGoodsGson, BaseViewHolder> {

        public PurseGoodsAdapter(@Nullable List<HotGoodsGson> data) {
            super(R.layout.ry_purse_goods_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HotGoodsGson item) {
            helper.setText(R.id.tv_name, item.getKind());
            RecyclerView ryPurse = helper.getView(R.id.ry_goods);
            Glide.with(PurseGoodsActivity.this).asBitmap().load(item.getBanner()).apply(new RequestOptions().transform(new GlideRoundTransform(PurseGoodsActivity.this, 7))).into((ImageView) helper.getView(R.id.iv_cover));
            ryPurse.setLayoutManager(new GridLayoutManager(PurseGoodsActivity.this, 3));
            ryPurse.setNestedScrollingEnabled(false);
            PurseGoodsItemAdapter purseGoodsItemAdapter = new PurseGoodsItemAdapter(item.getGoods());
            ryPurse.setAdapter(purseGoodsItemAdapter);
        }
    }

    private class PurseGoodsItemAdapter extends BaseQuickAdapter<HotGoodsGson.GoodsBean, BaseViewHolder> {

        public PurseGoodsItemAdapter(@Nullable List<HotGoodsGson.GoodsBean> data) {
            super(R.layout.ry_purse_goods_ry_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final HotGoodsGson.GoodsBean item) {
            helper.setText(R.id.tv_name, item.getGoods_name())
                    .setText(R.id.tv_original, "原价 " + item.getOrignal_price())
                    .setText(R.id.tv_price, "￥ " + item.getGods_price())
                    .setOnClickListener(R.id.ll_goods, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(PurseGoodsActivity.this, GoodsDetailActivity.class);
                            intent.putExtra("id", String .valueOf(item.getG_id()));
                            intent.putExtra("kind", item.getGoods_kind());
                            Log.i(TAG, "onClick: " + item.getGoods_kind());
                            startActivity(intent);
                        }
                    });
            Glide.with(PurseGoodsActivity.this).load(item.getGoods_pic()).apply(new RequestOptions().transform(new GlideRoundTransform(PurseGoodsActivity.this, 7))).into((ImageView) helper.getView(R.id.iv_cover));
        }
    }
}
