package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.UserShopCarContract;
import com.example.schoolshop.gson.UserShopCarGson;
import com.example.schoolshop.model.UserShopCarModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/14.
 */

public class UserShopCarPresenter implements UserShopCarContract.Presenter {
    private UserShopCarModel model = new UserShopCarModel();
    private UserShopCarContract.View view;

    public UserShopCarPresenter(UserShopCarContract.View view) {
        this.view = view;
    }

    @Override
    public void submitUserShopCar(String uid) {
        view.showLoading();
        model.submitUserShopCar(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<UserShopCarGson>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                        view.hideLoading();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<UserShopCarGson> shopCarGsonBaseGson) {
                        view.hideLoading();
                        if (shopCarGsonBaseGson.isStatus()) {
                            view.loadShopCarList(shopCarGsonBaseGson);
                        } else {
                            view.loadFailed(shopCarGsonBaseGson.getMsg());
                        }
                    }
                });
    }

    @Override
    public void submitUserShopCarWithoutDialog(String uid) {
        model.submitUserShopCar(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<UserShopCarGson>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<UserShopCarGson> shopCarGsonBaseGson) {
                        if (shopCarGsonBaseGson.isStatus()) {
                            view.loadShopCarList(shopCarGsonBaseGson);
                        } else {
                            view.loadFailed(shopCarGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
