package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.ExpressContract;
import com.example.schoolshop.gson.ExpressGson;
import com.example.schoolshop.model.ExpressModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/10.
 */

public class ExpressPresenter implements ExpressContract.Presenter {
    private ExpressModel expressModel = new ExpressModel();
    private ExpressContract.View view;

    public ExpressPresenter(ExpressContract.View view) {
        this.view = view;
    }

    @Override
    public void getUserPackageById(String id) {
        view.showLoading();
        expressModel.getUserPackageById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<ExpressGson>>() {
                    @Override
                    public void onError(String error) {
                        view.hideLoading();
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<ExpressGson> expressGsonBaseGson) {
                        view.hideLoading();
                        if (expressGsonBaseGson.isStatus()) {
                            view.loadExpress(expressGsonBaseGson.getData());
                        } else {
                            view.loadFailed(expressGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
