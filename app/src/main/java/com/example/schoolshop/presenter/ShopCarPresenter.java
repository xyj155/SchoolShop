package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.ShopCarContract;
import com.example.schoolshop.model.ShopCarModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by 徐易杰 on 2018/11/13.
 */

public class ShopCarPresenter implements ShopCarContract.Presenter {
    private ShopCarModel secondHandsModel = new ShopCarModel();
    private ShopCarContract.View view;

    public ShopCarPresenter(ShopCarContract.View view) {
        this.view = view;
    }

    @Override
    public void setUserOrder(String uid) {
        secondHandsModel.setUserOrder(uid)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onError(String error) {
                        view.submitFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        if (emptyGsonBaseGson.isStatus()) {
                            view.submitSuccess();
                        } else {
                            view.submitFailed(emptyGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
