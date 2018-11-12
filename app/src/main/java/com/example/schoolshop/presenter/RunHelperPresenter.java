package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.RunHelperContract;
import com.example.schoolshop.gson.RunHelperGson;
import com.example.schoolshop.model.RunHelperModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/11.
 */

public class RunHelperPresenter implements RunHelperContract.Presenter {
    private RunHelperModel runHelperModel = new RunHelperModel();
    private RunHelperContract.View view;

    public RunHelperPresenter(RunHelperContract.View view) {
        this.view = view;
    }

    @Override
    public void getRunHelperByLocation(String location) {
        view.showLoading();
        runHelperModel.getRunHelperByLocation(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<RunHelperGson>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<RunHelperGson> runHelperGsonBaseGson) {
                        view.hideLoading();
                        if (runHelperGsonBaseGson.isStatus()) {
                            view.loadHelperList(runHelperGsonBaseGson.getList());
                        } else {
                            view.loadFailed(runHelperGsonBaseGson.getMsg());
                        }
                    }
                });
    }

    @Override
    public void submitRunHelperOrder(String uid, String hid,String code) {
        runHelperModel.submitRunHelperOrder(uid,hid,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> runHelperGsonBaseGson) {
                        if (runHelperGsonBaseGson.isStatus()) {
                           view.success();
                        } else {
                            view.loadFailed(runHelperGsonBaseGson.getMsg());
                        }
                    }
                });
    }

    @Override
    public void submitOrder(String uid, String hid, String request) {
        runHelperModel.submitRunHelperOrder(uid,hid,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onError(String error) {
                        view.setOrderFailed();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> runHelperGsonBaseGson) {
                        if (runHelperGsonBaseGson.isStatus()) {
                            view.setOrderSuccess();
                        } else {
                            view.setOrderFailed();
                        }
                    }
                });
    }
}
