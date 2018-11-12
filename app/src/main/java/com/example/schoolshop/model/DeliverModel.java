package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.DeliverContract;
import com.example.schoolshop.gson.PostPackageGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/6.
 */

public class DeliverModel implements DeliverContract.Model {
    @Override
    public Observable<BaseGson<PostPackageGson>> getUserPackageListById(String uid) {
        return RetrofitUtil.getInstance().getServerices().getUserPackageListById(uid);
    }
}
