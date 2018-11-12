package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.RunHelperContract;
import com.example.schoolshop.gson.RunHelperGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/11.
 */

public class RunHelperModel implements RunHelperContract.Model {
    @Override
    public Observable<BaseGson<RunHelperGson>> getRunHelperByLocation(String location) {
        return RetrofitUtil.getInstance().getServerices().getRunHelperByLocation(location);
    }

    @Override
    public Observable<BaseGson<EmptyGson>> submitRunHelperOrder(String uid, String hid,String request) {
        return RetrofitUtil.getInstance().getServerices().submitRunHelperOrder(uid,hid,request);
    }

    @Override
    public Observable<BaseGson<EmptyGson>> submitOrder(String uid, String hid, String request) {
        return RetrofitUtil.getInstance().getServerices().submitRunHelperOrder(uid,hid,request);
    }
}
