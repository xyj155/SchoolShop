package com.example.schoolshop.presenter;

import android.util.Log;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.UserAddressContract;
import com.example.schoolshop.gson.AddressGson;
import com.example.schoolshop.model.UserAddressModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/6.
 */

public class UserAddressPresenter implements UserAddressContract.Presenter {
    private UserAddressModel userAddressModel = new UserAddressModel();
    private UserAddressContract.View view;

    public UserAddressPresenter(UserAddressContract.View view) {
        this.view = view;
    }

    @Override
    public void getUserAddressList(String uid) {
        view.showLoading();
        userAddressModel.getUserAddressList(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<AddressGson>>() {
                    @Override
                    public void onError(String error) {

                        view.hideLoading();
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<AddressGson> emptyGsonBaseGson) {
                        view.hideLoading();
                        Log.i(TAG, "onNext: "+emptyGsonBaseGson.getList());
                        if (emptyGsonBaseGson.isStatus()) {
                            view.loadAddressList(emptyGsonBaseGson.getList());
                        } else {
                            view.loadFailed(emptyGsonBaseGson.getMsg());
                        }
                    }
                });
    }

    private static final String TAG = "UserAddressPresenter";

    @Override
    public void addUserReceiveAddress(String username, String tel, String address, String uid,String location, String isReceive) {
        view.showLoading();
        userAddressModel.addUserReceiveAddress(username, tel, address, uid,location, isReceive)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onError(String error) {

                        view.hideLoading();
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        view.hideLoading();
                        if (emptyGsonBaseGson.isStatus()) {
                            view.loadSuccess();
                        } else {
                            view.loadFailed(emptyGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
