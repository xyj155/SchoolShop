package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.UserOrderContract;
import com.example.schoolshop.gson.UserOrderFormAllListGson;
import com.example.schoolshop.model.UserOrderModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 徐易杰 on 2018/11/9.
 */

public class UserOrderPresenter implements UserOrderContract.Presenter {
    private UserOrderModel userOrderModel = new UserOrderModel();
    private UserOrderContract.View view;

    public UserOrderPresenter(UserOrderContract.View view) {
        this.view = view;
    }

    @Override
    public void getUserOrdersList(String uid,String  status) {
        view.showLoading();
        userOrderModel.getUserOrdersList(uid,status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<UserOrderFormAllListGson>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                        view.hideLoading();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<UserOrderFormAllListGson> userOrderFormAllListGsonBaseGson) {
                        view.hideLoading();
                        if (userOrderFormAllListGsonBaseGson.isStatus()) {
                            view.loadOrderList(userOrderFormAllListGsonBaseGson.getList());
                        } else {
                            view.loadFailed(userOrderFormAllListGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
