package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.UserContract;
import com.example.schoolshop.gson.UserGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/2.
 */

public class UserModel implements UserContract.Model {


    @Override
    public Observable<BaseGson<UserGson>> userRegisterByName(String username, String password, String location) {
        return RetrofitUtil.getInstance().getServerices().userRegister(username, password, location);
    }
}
