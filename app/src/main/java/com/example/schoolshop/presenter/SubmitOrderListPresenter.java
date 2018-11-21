package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.SubmitOrderListContract;
import com.example.schoolshop.gson.SubmitOrderListGson;
import com.example.schoolshop.model.SubmitOrderListModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/21.
 */

public class SubmitOrderListPresenter implements SubmitOrderListContract.Presenter {
    private SubmitOrderListModel submitOrderListMode = new SubmitOrderListModel();
    private SubmitOrderListContract.View view;

    public SubmitOrderListPresenter(SubmitOrderListContract.View view) {
        this.view = view;
    }

    @Override
    public void getUserOrdersPayList(String uid, String shopid) {
        view.showLoading();
        submitOrderListMode.getUserOrdersPayList(uid, shopid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SubmitOrderListGson>>() {
                    @Override
                    public void onError(String error) {
                        view.hideLoading();
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SubmitOrderListGson> submitOrderListGsonBaseGson) {
                        view.hideLoading();
                        if (submitOrderListGsonBaseGson.isStatus()) {
                            view.loadOrderList(submitOrderListGsonBaseGson.getData());
                        } else {
                            view.loadFailed(submitOrderListGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
