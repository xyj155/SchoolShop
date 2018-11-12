package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.LoginContract;
import com.example.schoolshop.gson.UserGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/2.
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public Observable<BaseGson<UserGson>> userLogin(String username, String password) {
        return RetrofitUtil.getInstance().getServerices().userLogin(username,password);
    }
}
