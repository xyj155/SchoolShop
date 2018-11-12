package com.example.schoolshop.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.SecondHandsContract;
import com.example.schoolshop.gson.SecondHandGson;
import com.example.schoolshop.presenter.SecondHandsPresenter;
import com.example.schoolshop.view.SpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SecondSellerActivity extends BaseActivity implements SecondHandsContract.View {


    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    RelativeLayout toolbar;
    @InjectView(R.id.ry_head)
    RecyclerView ryHead;
    @InjectView(R.id.ry_list)
    RecyclerView ryList;
    @InjectView(R.id.sl_list)
    SmartRefreshLayout slList;
    private SecondHandsPresenter secondHandsPresenter = new SecondHandsPresenter(this);
    SecondHanAdapter adapter = new SecondHanAdapter(null);

    @Override
    public int intiLayout() {
        return R.layout.activity_second_seller;


    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        initToolBar().setToolbarBackIco().setToolbarSubtitle("二手闲置");
        ryHead.setLayoutManager(new LinearLayoutManager(this));
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//防止滑动时跳动
        layoutManager.invalidateSpanAssignments();
        ryList.setLayoutManager(layoutManager);
        ryList.setHasFixedSize(true);
        ryList.setNestedScrollingEnabled(false);
        ryList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                       @Override
                                       public void
                                       onScrollStateChanged(RecyclerView recyclerView,
                                                            int newState) {
                                           super.onScrollStateChanged(recyclerView,
                                                   newState);
                                           //防止第一行到顶部有空白区域
                                           layoutManager.invalidateSpanAssignments();
                                       }
                                   }
        );
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        ryList.addItemDecoration(decoration);
        secondHandsPresenter.getSecondHandsByLocation("嘉兴", "");
        ryList.setAdapter(adapter);
        slList.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                secondHandsPresenter.getSecondHandsByLocation("嘉兴", "");
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
    public void showLoading() {
        showDialog();
    }

    @Override
    public void hideLoading() {
        dialogClose();
    }

    @Override
    public void loadFailed(String msg) {
        slList.finishRefresh();
        Toast.makeText(this, "加载失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadSecondList(SecondHandGson secondHandGson) {
        slList.finishRefresh();
        adapter.replaceData(secondHandGson.getList());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    private class SecondHanAdapter extends BaseQuickAdapter<SecondHandGson.ListBean, BaseViewHolder> {

        public SecondHanAdapter(@Nullable List<SecondHandGson.ListBean> data) {
            super(R.layout.ry_second_hand_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SecondHandGson.ListBean item) {
            helper.setText(R.id.tv_username, item.getUsername().substring(0, 3) + "****")
                    .setText(R.id.tv_name, item.getGoods_name())
                    .setText(R.id.tv_price, "￥" + item.getGoods_price());
            Glide.with(SecondSellerActivity.this).asBitmap().load(item.getPic()).into((ImageView) helper.getView(R.id.iv_cover));
        }
    }
}