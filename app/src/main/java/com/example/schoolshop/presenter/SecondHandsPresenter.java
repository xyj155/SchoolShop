package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.SecondHandsContract;
import com.example.schoolshop.gson.SecondHandGson;
import com.example.schoolshop.model.SecondHandsModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SecondHandsPresenter implements SecondHandsContract.Presenter {
    private SecondHandsContract.View view;
    private SecondHandsModel secondHandsModel = new SecondHandsModel();

    public SecondHandsPresenter(SecondHandsContract.View view) {
        this.view = view;
    }

    @Override
    public void getSecondHandsByLocation(String location, String kind) {
        view.showLoading();
        secondHandsModel.getSecondHandsByLocation(location, kind)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<SecondHandGson>>() {
                    @Override
                    public void onError(String error) {
                        view.hideLoading();
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<SecondHandGson> secondHandGsonBaseGson) {
                        view.hideLoading();
                        if (secondHandGsonBaseGson.isStatus()) {
                            view.loadSecondList(secondHandGsonBaseGson.getData());
                        } else {
                            view.loadFailed(secondHandGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}