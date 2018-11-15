package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.UserSelfPackageContract;
import com.example.schoolshop.gson.PostPackageGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/15.
 */

public class UserSelfPackageModel implements UserSelfPackageContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> addUserSelfPackage(String uId, String num, String code, String name, String comment) {
        return RetrofitUtil.getInstance().getServerices().addUserSelfPackage(uId, num, code, name, comment);
    }

    @Override
    public Observable<BaseGson<PostPackageGson>> getUserAddedExpressList(String uid) {
        return RetrofitUtil.getInstance().getServerices().getUserAddedExpressList(uid);
    }
}
