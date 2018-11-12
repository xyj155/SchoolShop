package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.LoginContract;
import com.example.schoolshop.gson.UserGson;
import com.example.schoolshop.model.LoginModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/2.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private LoginModel model = new LoginModel();

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void userLogin(String username, String password) {
        view.showLoading();
        model.userLogin(username, password)
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
                            view.login(userGsonBaseGson.getData());
                        } else {
                            view.loadFailed(userGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
