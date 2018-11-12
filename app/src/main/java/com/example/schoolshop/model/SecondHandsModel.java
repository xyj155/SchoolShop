package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.SecondHandsContract;
import com.example.schoolshop.gson.SecondHandGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

public class SecondHandsModel implements SecondHandsContract.Model {
    @Override
    public Observable<BaseGson<SecondHandGson>> getSecondHandsByLocation(String location, String kind) {
        return RetrofitUtil.getInstance().getServerices().getSecondHandsByLocation(location,kind);
    }
}