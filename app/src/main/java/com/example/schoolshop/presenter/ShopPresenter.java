package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.ShopContract;
import com.example.schoolshop.gson.ShopGson;
import com.example.schoolshop.model.ShopModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/5.
 */

public class ShopPresenter implements ShopContract.Presenter {
    private ShopModel shopModel = new ShopModel();
    private ShopContract.View view;

    public ShopPresenter(ShopContract.View view) {
        this.view = view;
    }

    @Override
    public void getSellerList(String location) {
        view.showLoading();
        shopModel.getSellerList(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<ShopGson>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                        view.hideLoading();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<ShopGson> shopGsonBaseGson) {
                        view.hideLoading();
                        if (shopGsonBaseGson.isStatus()) {
                            view.loadShopList(shopGsonBaseGson.getList());
                        } else {
                            view.loadFailed(shopGsonBaseGson.getMsg());
                        }
                    }
                });
    }

    @Override
    public void getSellerDetailById(String id) {
        view.showLoading();
        shopModel.getSellerDetailById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<ShopGson>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                        view.hideLoading();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<ShopGson> shopGsonBaseGson) {
                        view.hideLoading();
                        if (shopGsonBaseGson.isStatus()) {
                            view.loadShopDetail(shopGsonBaseGson.getData());
                        } else {
                            view.loadFailed(shopGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
