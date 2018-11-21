package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.UserSubmitOrderListContract;
import com.example.schoolshop.model.UserSubmitOrderListModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 徐易杰 on 2018/11/21.
 */

public class UserSubmitOrderListPresenter implements UserSubmitOrderListContract.Presenter {
    private UserSubmitOrderListModel submitOrderListModel = new UserSubmitOrderListModel();
    private UserSubmitOrderListContract.View view;

    public UserSubmitOrderListPresenter(UserSubmitOrderListContract.View view) {
        this.view = view;
    }

    @Override
    public void userBuyGoodsListByShopId(String uid, String sid, String address, String tel, String money) {
        view.showLoading();
        submitOrderListModel.userBuyGoodsListByShopId(uid,sid,address,tel,money)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onError(String error) {
                        view.hideLoading();
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        view.hideLoading();
                        if (emptyGsonBaseGson.isStatus()) {

                            view.submitSuccess();
                        } else {
                            view.submitFailed();
                        }
                    }
                });
    }
}
