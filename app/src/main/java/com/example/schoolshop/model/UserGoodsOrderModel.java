package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.UserGoodsOrderContract;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/18.
 */

public class UserGoodsOrderModel implements UserGoodsOrderContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> updateOrderStatus(String id) {
        return null;
    }
}
