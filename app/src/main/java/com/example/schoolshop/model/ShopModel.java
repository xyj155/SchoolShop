package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.ShopContract;
import com.example.schoolshop.gson.ShopGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/5.
 */

public class ShopModel implements ShopContract.Model {
    @Override
    public Observable<BaseGson<ShopGson>> getSellerList(String location) {
        return RetrofitUtil.getInstance().getServerices().getSellerList(location);
    }

    @Override
    public Observable<BaseGson<ShopGson>> getSellerDetailById(String uid,String id) {
        return RetrofitUtil.getInstance().getServerices().getSellerDetailById(uid,id);
    }
}
