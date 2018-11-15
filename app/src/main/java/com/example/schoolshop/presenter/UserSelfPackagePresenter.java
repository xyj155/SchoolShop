package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.UserSelfPackageContract;
import com.example.schoolshop.gson.PostPackageGson;
import com.example.schoolshop.model.UserSelfPackageModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/15.
 */

public class UserSelfPackagePresenter implements UserSelfPackageContract.Presenter {
    private UserSelfPackageModel userSelfPackageModel = new UserSelfPackageModel();
    private UserSelfPackageContract.View view;

    public UserSelfPackagePresenter(UserSelfPackageContract.View view) {
        this.view = view;
    }

    @Override
    public void addUserSelfPackage(String uId, String num, String code, String name, String comment) {
        view.showLoading();
        userSelfPackageModel.addUserSelfPackage(uId, num, code, name, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onError(String error) {
                        view.hideLoading();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        view.hideLoading();
                        if (emptyGsonBaseGson.isStatus()) {
                            view.addSuccess();
                        } else {
                            view.loadFailed(emptyGsonBaseGson.getMsg());
                        }
                    }
                });
    }

    @Override
    public void getUserAddedExpressList(String uid) {
        view.showLoading();
        userSelfPackageModel.getUserAddedExpressList(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<PostPackageGson>>() {
                    @Override
                    public void onError(String error) {
                        view.hideLoading();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<PostPackageGson> emptyGsonBaseGson) {
                        view.hideLoading();
                        if (emptyGsonBaseGson.isStatus()) {
                            view.loadExpressList(emptyGsonBaseGson.getList());
                        } else {
                            view.loadFailed(emptyGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
