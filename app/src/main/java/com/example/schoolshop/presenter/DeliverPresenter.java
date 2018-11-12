package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.DeliverContract;
import com.example.schoolshop.gson.PostPackageGson;
import com.example.schoolshop.model.DeliverModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/6.
 */

public class DeliverPresenter implements DeliverContract.Presenter {
    private DeliverModel deliverModel=new DeliverModel();
    private DeliverContract.View view;

    public DeliverPresenter(DeliverContract.View view) {
        this.view = view;
    }

    @Override
    public void getUserPackageListById(String uid) {
        view.showLoading();
        deliverModel.getUserPackageListById(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<PostPackageGson>>() {
                    @Override
                    public void onError(String error) {
                        view.hideLoading();
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<PostPackageGson> postPackageGsonBaseGson) {
                        view.hideLoading();
                        if (postPackageGsonBaseGson.isStatus()){
                            view.loadUserPackageListById(postPackageGsonBaseGson.getList());
                        }else {
                            view.loadFailed(postPackageGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
