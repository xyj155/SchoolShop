package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.ShopCarContract;
import com.example.schoolshop.gson.UserShopCarGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/14.
 */

public class ShopCarModel implements ShopCarContract.Model {
    @Override
    public Observable<BaseGson<UserShopCarGson>> submitUserShopCar(String uid) {
        return RetrofitUtil.getInstance().getServerices().submitUserShopCar(uid);
    }
}
