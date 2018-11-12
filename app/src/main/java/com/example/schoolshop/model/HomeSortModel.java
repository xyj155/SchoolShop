package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.HomeSortContract;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/1.
 */

public class HomeSortModel implements HomeSortContract.Model {
    @Override
    public Observable<BaseGson<GoodGson>> getGoodsListByLocation(String location,String kind,String uid) {
        return RetrofitUtil.getInstance().getServerices().getGoodsListByLocation(location,kind,uid);
    }
}
