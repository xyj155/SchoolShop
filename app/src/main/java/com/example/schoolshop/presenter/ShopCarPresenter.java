package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.ShopCarContract;
import com.example.schoolshop.gson.UserShopCarGson;
import com.example.schoolshop.model.ShopCarModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/14.
 */

public class ShopCarPresenter implements ShopCarContract.Presenter {
    private ShopCarContract.View view;
    private ShopCarModel shopCarModel = new ShopCarModel();

    public ShopCarPresenter(ShopCarContract.View view) {
        this.view = view;
    }

    @Override
    public void submitUserShopCar(String uid) {
        view.showLoading();
        shopCarModel.submitUserShopCar(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<UserShopCarGson>>() {
                    @Override
                    public void onError(String error) {
                        view.hideLoading();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<UserShopCarGson> userShopCarGsonBaseGson) {
                        view.hideLoading();
                        if (userShopCarGsonBaseGson.isStatus()) {
                            view.loadShopCarList(userShopCarGsonBaseGson);
                        }else {
                            view.loadFailed(userShopCarGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
