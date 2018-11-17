package com.example.schoolshop.ui.deliver;

import android.content.ClipData;
import android.content.ClipboardManager;
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
import com.example.schoolshop.base.BaseFragment;
import com.example.schoolshop.contract.DeliverContract;
import com.example.schoolshop.gson.PostPackageGson;
import com.example.schoolshop.presenter.DeliverPresenter;
import com.example.schoolshop.ui.ExpressTraceActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by Administrator on 2018/11/6.
 */

public class GoodsFragment extends BaseFragment implements DeliverContract.View {
    private DeliverAdapter deliverAdapter = new DeliverAdapter(null);
    private DeliverPresenter deliverPresenter = new DeliverPresenter(this);
    private RecyclerView ryDeliver;
    private SmartRefreshLayout slDeliver;
    private ClipboardManager mClipboardManager;
    @Override
    protected int setView() {
        return R.layout.fragment_goods_deliver;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClipboardManager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
    }

    @Override
    protected void init(View view) {
        ryDeliver =view. findViewById(R.id.ry_deliver);
        slDeliver = view.findViewById(R.id.rl_deliver);
        ryDeliver.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        slDeliver.autoRefresh();
        slDeliver.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                deliverPresenter.getUserPackageListById("1");
            }
        });
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
        slDeliver.finishRefresh();
        loadSuccess();
        Toast.makeText(getActivity(), "获取快递列表失败！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadUserPackageListById(List<PostPackageGson> postPackageGsonList) {
        slDeliver.finishRefresh();
        loadSuccess();
        deliverAdapter.replaceData(postPackageGsonList);
        ryDeliver.setAdapter(deliverAdapter);
    }

    public class DeliverAdapter extends BaseQuickAdapter<PostPackageGson, BaseViewHolder> {

        public DeliverAdapter(@Nullable List<PostPackageGson> data) {
            super(R.layout.ry_deliver_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final PostPackageGson item) {
            helper.setText(R.id.tv_name, item.getGoodName())
                    .setText(R.id.tv_time, "日期：" + item.getUpdatetime().substring(5))
                    .setText(R.id.tv_num, item.getP_cp() + "：" + item.getP_num())
                    .setText(R.id.tv_location, item.getFrom() + "-" + item.getTo())
                    .setOnClickListener(R.id.iv_copy, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipData clipData = ClipData.newPlainText("", item.getP_num());
                            mClipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(getActivity(), "订单号已复制", Toast.LENGTH_SHORT).show();
                        }
                    })
            .setOnClickListener(R.id.ll_deliver, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), ExpressTraceActivity.class));
                }
            });
            Glide.with(getActivity()).asBitmap().load(item.getGoodPic()).into((ImageView) helper.getView(R.id.iv_cover));
        }
    }
}
