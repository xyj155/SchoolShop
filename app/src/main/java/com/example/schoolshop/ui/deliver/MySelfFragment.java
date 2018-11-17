package com.example.schoolshop.ui.deliver;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.schoolshop.R;
import com.example.schoolshop.base.BaseFragment;
import com.example.schoolshop.contract.UserSelfPackageContract;
import com.example.schoolshop.gson.PostPackageGson;
import com.example.schoolshop.presenter.UserSelfPackagePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.content.ContentValues.TAG;
import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by Administrator on 2018/11/6.
 */

public class MySelfFragment extends BaseFragment implements UserSelfPackageContract.View {
    @InjectView(R.id.ry_add)
    RecyclerView ryAdd;
    @InjectView(R.id.sl_self)
    SmartRefreshLayout slSelf;
    private UserSelfPackagePresenter selfPackagePresenter = new UserSelfPackagePresenter(this);
    private UserExpressAdapter userAdapter = new UserExpressAdapter(null);

    @Override
    protected int setView() {
        return R.layout.fragment_self_deliver;
    }

    @Override
    protected void init(View view) {
        ButterKnife.inject(this, view);
        slSelf.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                selfPackagePresenter.getUserAddedExpressList("1");
            }
        });
        selfPackagePresenter.getUserAddedExpressList("1");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        selfPackagePresenter.getUserAddedExpressList("1");
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i(TAG, "onHiddenChanged: ");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ryAdd.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadFailed(String msg) {
        slSelf.finishRefresh();
        Toast.makeText(getActivity(), "请求出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addSuccess() {

    }

    @Override
    public void loadExpressList(List<PostPackageGson> postPackageGsonList) {
        slSelf.finishRefresh();
        userAdapter.replaceData(postPackageGsonList);
        ryAdd.setAdapter(userAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private ClipboardManager mClipboardManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClipboardManager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
    }

    private class UserExpressAdapter extends BaseQuickAdapter<PostPackageGson, BaseViewHolder> {

        public UserExpressAdapter(@Nullable List<PostPackageGson> data) {
            super(R.layout.ry_user_self_express_item, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final PostPackageGson item) {
            helper.setText(R.id.tv_time, item.getUpdatetime())
                    .setText(R.id.tv_num, item.getP_cp() + " " + item.getP_num())
                    .setOnClickListener(R.id.iv_copy, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ClipData clipData = ClipData.newPlainText("", item.getP_num());
                            mClipboardManager.setPrimaryClip(clipData);
                            Toast.makeText(getActivity(), "订单号已复制", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: " + requestCode + resultCode);
        if (requestCode == 0) {

        }
    }
}
