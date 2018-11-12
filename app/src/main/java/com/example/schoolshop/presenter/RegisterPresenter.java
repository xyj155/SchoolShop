package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.RegisterContract;
import com.example.schoolshop.gson.LeanCloudGson;
import com.example.schoolshop.model.RegisterModel;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/2.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }

    private RegisterContract.View view;
    private RegisterModel model = new RegisterModel();

    @Override
    public void requestSms(String tel) {
        view.showLoading();
        JSONObject root = new JSONObject();
        try {
            root.put("mobilePhoneNumber", tel);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
            model.registerSms(requestBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<LeanCloudGson>() {
                        @Override
                        public void onError(String error) {
                            view.loadFailed("验证码发送错误");
                            view.hideLoading();

                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onNext(LeanCloudGson leanCloudGson) {
                            view.hideLoading();
                            view.sendSuccess();
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void verifySmsCode(String tel, String code) {
        view.showLoading();
        JSONObject root = new JSONObject();
        try {
            root.put("mobilePhoneNumber", tel);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
            model.verifySmsCode(requestBody, code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<LeanCloudGson>() {
                        @Override
                        public void onError(String error) {
                            view.loadFailed("验证码验证错误");
                            view.hideLoading();

                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onNext(LeanCloudGson leanCloudGson) {
                            view.hideLoading();
                            view.verify();
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
