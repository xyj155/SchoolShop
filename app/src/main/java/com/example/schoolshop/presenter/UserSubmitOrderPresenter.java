package com.example.schoolshop.presenter;

import android.util.Log;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.UserSubmitOrderContract;
import com.example.schoolshop.gson.SubmitOrderGson;
import com.example.schoolshop.model.UserSubmitOrderModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/17.
 */

public class UserSubmitOrderPresenter implements UserSubmitOrderContract.Presenter {
    private UserSubmitOrderModel submitOrderModel = new UserSubmitOrderModel();
    private UserSubmitOrderContract.View view;

    public UserSubmitOrderPresenter(UserSubmitOrderContract.View view) {
        this.view = view;
    }

    @Override
    public void getOrderInformation(String gid, String uid) {
        view.showLoading();
        submitOrderModel.getOrderInformation(gid, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SubmitOrderGson>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                        view.hideLoading();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SubmitOrderGson> orderGsonBaseGson) {
                        view.hideLoading();
                        if (orderGsonBaseGson.isStatus()) {
                            view.getOrderDetail(orderGsonBaseGson.getData());
                        } else {
                            view.loadFailed(orderGsonBaseGson.getMsg());
                        }
                    }
                });
    }

    private static final String TAG = "UserSubmitOrderPresente";

    @Override
    public void userPayGoodsOrder(String id, String address,String tel,String count,String money) {
        submitOrderModel.userPayGoodsOrder(id, address,tel,count,money)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onError(String error) {
                        Log.i(TAG, "onError: "+error);
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> orderGsonBaseGson) {
                        if (orderGsonBaseGson.isStatus()) {
                          view.paySuccess();
                        } else {
                            view.payFailed();
                        }
                    }
                });
    }
}
