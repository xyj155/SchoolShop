package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.UserContract;
import com.example.schoolshop.gson.UserGson;
import com.example.schoolshop.model.UserModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/2.
 */

public class UserPresenter implements UserContract.Presenter {
    public UserPresenter(UserContract.View view) {
        this.view = view;
    }

    private UserContract.View view;
    private UserModel model = new UserModel();
    @Override
    public void userRegister(String username, String password, String location) {
        view.showLoading();
        model.userRegisterByName(username, password, location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<UserGson>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                        view.hideLoading();

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<UserGson> userGsonBaseGson) {
                        view.hideLoading();
                        if (userGsonBaseGson.isStatus()) {
                            view.register(userGsonBaseGson.getData());
                        } else {
                            view.loadFailed(userGsonBaseGson.getMsg());
                        }

                    }

                });
    }
}
