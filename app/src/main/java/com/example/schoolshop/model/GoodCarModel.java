package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.GoodCarContract;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/7.
 */

public class GoodCarModel implements GoodCarContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> addGoodsCar(String uid, String gid,String comment,String isDelete) {
        return RetrofitUtil.getInstance().getServerices().addGoodsCar(uid,gid,comment,isDelete);
    }

    @Override
    public Observable<BaseGson<GoodGson.GoodsBean>> getShopCarGoodsList(String uid) {
        return RetrofitUtil.getInstance().getServerices().getShopCarGoodsList(uid);
    }
}
