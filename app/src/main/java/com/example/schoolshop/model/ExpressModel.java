package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.ExpressContract;
import com.example.schoolshop.gson.ExpressGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/10.
 */

public class ExpressModel implements ExpressContract.Model {
    @Override
    public Observable<BaseGson<ExpressGson>> getUserPackageById(String id) {
        return RetrofitUtil.getInstance().getServerices().getUserPackageById(id);
    }
}
