package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.ShopCarContract;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/13.
 */

public class ShopCarModel implements ShopCarContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> setUserOrder(String uid) {
        return RetrofitUtil.getInstance().getServerices().setUserOrder( uid);
    }
}
