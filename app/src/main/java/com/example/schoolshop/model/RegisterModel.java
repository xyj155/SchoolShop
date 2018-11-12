package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.RegisterContract;
import com.example.schoolshop.gson.LeanCloudGson;
import com.example.schoolshop.gson.UserGson;
import com.example.schoolshop.http.RetrofitUtil;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by Administrator on 2018/11/2.
 */

public class RegisterModel implements RegisterContract.Model {
    @Override
    public Observable<LeanCloudGson> registerSms(RequestBody params) {
        return RetrofitUtil.getLeanCloudInstance().getServerices().requestSms(params);
    }

    @Override
    public Observable<LeanCloudGson> verifySmsCode(RequestBody params, String code) {
        return RetrofitUtil.getLeanCloudInstance().getServerices().verifySmsCode(params, code);
    }


}
