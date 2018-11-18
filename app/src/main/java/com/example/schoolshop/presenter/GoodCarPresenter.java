package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.GoodCarContract;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.model.GoodCarModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/7.
 */

public class GoodCarPresenter implements GoodCarContract.Presenter {
    private GoodCarModel goodCarModel = new GoodCarModel();
    private GoodCarContract.View view;

    public GoodCarPresenter(GoodCarContract.View view) {
        this.view = view;
    }

    @Override
    public void addGoodsCar(String uid, String gid,String comment, final String isDelete) {
        goodCarModel.addGoodsCar(uid, gid, comment,isDelete)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onError(String error) {
                        view.addFailed();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        if (emptyGsonBaseGson.isStatus()) {
                            if (isDelete.equals("0")) {
                                view.addSuccess();
                            } else {
                                view.delSuccess();
                            }
                        } else {
                            view.addFailed();
                        }
                    }
                });
    }

    @Override
    public void getShopCarGoodsList(String uid) {
        goodCarModel.getShopCarGoodsList(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<GoodGson.GoodsBean>>() {
                    @Override
                    public void onError(String error) {
                        view.addFailed();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<GoodGson.GoodsBean> emptyGsonBaseGson) {
                        if (emptyGsonBaseGson.isStatus()) {
                            view.loadShopCarList(emptyGsonBaseGson.getList());
                        } else {
                            view.addFailed();
                        }
                    }
                });
    }

    @Override
    public void deleteUserShopCar(String uid) {
        goodCarModel.deleteUserShopCar(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onError(String error) {
                        view.addFailed();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        if (emptyGsonBaseGson.isStatus()) {
                            view.delShopCarSuccess();
                        } else {
                            view.delShopCarFailed();
                        }
                    }
                });
    }
}
