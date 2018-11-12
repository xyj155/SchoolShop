package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.LeanCloudGson;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Administrator on 2018/11/2.
 */

public interface RegisterContract {
    interface Model {
        Observable<LeanCloudGson> registerSms(RequestBody params);

        Observable<LeanCloudGson> verifySmsCode(RequestBody params, String code);


    }

    interface View extends BaseView {
        void sendSuccess();

        void verify();


    }

    interface Presenter {
        void requestSms(String tel);

        void verifySmsCode(String tel, String code);

    }
}
