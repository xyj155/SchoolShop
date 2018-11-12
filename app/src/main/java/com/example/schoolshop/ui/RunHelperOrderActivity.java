package com.example.schoolshop.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseActivity;
import com.example.schoolshop.contract.RunHelperContract;
import com.example.schoolshop.gson.RunHelperGson;
import com.example.schoolshop.presenter.RunHelperPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class RunHelperOrderActivity extends BaseActivity implements RunHelperContract.View {
    @InjectView(R.id.iv_back)
    ImageView ivBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    RelativeLayout toolbar;
    @InjectView(R.id.ry_helper)
    RecyclerView ryHelper;
    @InjectView(R.id.sl_layout)
    SmartRefreshLayout slLayout;
    private RunHelperPresenter runHelperPresenter = new RunHelperPresenter(this);
    private RunHelperAdapter runHelperAdapter = new RunHelperAdapter(null);

    @Override
    public int intiLayout() {
        return R.layout.activity_run_hleper_order;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        ryHelper.setLayoutManager(new LinearLayoutManager(RunHelperOrderActivity.this));
    }

    @Override
    public void initData() {
        initToolBar().setToolbarBackIco().setToolbarSubtitle("跑腿");
        runHelperPresenter.getRunHelperByLocation("嘉兴");
        ryHelper.setLayoutManager(new LinearLayoutManager(RunHelperOrderActivity.this));
        ryHelper.setAdapter(runHelperAdapter);
        slLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                runHelperPresenter.getRunHelperByLocation("嘉兴");
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
        slLayout.finishRefresh();
        Toast.makeText(this, "加载出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadHelperList(List<RunHelperGson> runHelperGsons) {
        slLayout.finishRefresh();
        runHelperAdapter.replaceData(runHelperGsons);
    }

    @Override
    public void success() {

    }

    @Override
    public void setOrderSuccess() {

    }

    @Override
    public void setOrderFailed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    private class RunHelperAdapter extends BaseQuickAdapter<RunHelperGson, BaseViewHolder> {

        public RunHelperAdapter(@Nullable List<RunHelperGson> data) {
            super(R.layout.ry_helper_run_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final RunHelperGson item) {
            helper.setText(R.id.tv_name, item.getName())
                    .setText(R.id.tv_address, "地址：" + item.getAddress())
                    .setOnClickListener(R.id.tv_hand, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog();
                            Log.i(TAG, "onClick: "+getSharedPreferences("user", MODE_PRIVATE).getString("token", ""));
                            RongIM.connect(getSharedPreferences("user", MODE_PRIVATE).getString("token", ""), new RongIMClient.ConnectCallback() {
                                @Override
                                public void onTokenIncorrect() {

                                }

                                @Override
                                public void onSuccess(String s) {
                                    dialogClose();
                                    RongIM.getInstance().startPrivateChat(RunHelperOrderActivity.this, item.getUsername(),  item.getUsername() );
                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                    dialogClose();
                                    Log.i(TAG, "onError: "+errorCode);
                                }
                            });
                        }
                    });
        }
    }
}
