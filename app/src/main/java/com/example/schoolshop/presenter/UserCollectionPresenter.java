package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.UserCollectionContract;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.gson.ShopGson;
import com.example.schoolshop.model.UserCollectionModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/10.
 */

public class UserCollectionPresenter implements UserCollectionContract.Presenter {
    private UserCollectionModel userCollectionModel = new UserCollectionModel();
    private UserCollectionContract.View view;

    public UserCollectionPresenter(UserCollectionContract.View view) {
        this.view = view;
    }

    @Override
    public void getUserStoreCollection(String uid) {
        view.showLoading();
        userCollectionModel.getUserStoreCollection(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<ShopGson>>() {
                    @Override
                    public void onError(String error) {
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
    public void getUserGoodsCollection(String uid) {
        view.showLoading();
        userCollectionModel.getUserGoodsCollection(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<GoodGson.GoodsBean>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<GoodGson.GoodsBean> shopGsonBaseGson) {
                        view.hideLoading();
                        if (shopGsonBaseGson.isStatus()) {
                            view.loadGoodsList(shopGsonBaseGson.getList());
                        }else {
                            view.loadFailed(shopGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
