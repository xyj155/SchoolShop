package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.OrderContract;
import com.example.schoolshop.gson.UserOrderGson;
import com.example.schoolshop.model.OrderModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/17.
 */

public class OrderPresenter implements OrderContract.Presenter {
    private OrderModel orderModel = new OrderModel();
    private OrderContract.View view;

    public OrderPresenter(OrderContract.View view) {
        this.view = view;
    }

    @Override
    public void submitUserOrder(String uid, String gid, String information, String count, String money, String status) {
        orderModel.submitUserOrder(uid, gid, information, count, money, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<UserOrderGson>>() {
                    @Override
                    public void onError(String error) {
                        view.submitFailed();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<UserOrderGson> emptyGsonBaseGson) {
                        if (emptyGsonBaseGson.isStatus()) {
                            view.submitSuccess(emptyGsonBaseGson.getData());
                        } else {
                            view.submitFailed();
                        }
                    }
                });
    }
}
