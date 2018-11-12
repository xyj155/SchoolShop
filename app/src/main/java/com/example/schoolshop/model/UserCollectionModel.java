package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.UserCollectionContract;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.gson.ShopGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/10.
 */

public class UserCollectionModel implements UserCollectionContract.Model {
    @Override
    public Observable<BaseGson<ShopGson>> getUserStoreCollection(String uid) {
        return RetrofitUtil.getInstance().getServerices().getUserStoreCollection(uid);
    }

    @Override
    public Observable<BaseGson<GoodGson.GoodsBean>> getUserGoodsCollection(String uid) {
        return RetrofitUtil.getInstance().getServerices().getUserGoodsCollection(uid);
    }
}
