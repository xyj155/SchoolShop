package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.UserOrderContract.Model;
import com.example.schoolshop.gson.UserOrderFormAllListGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/9.
 */

public class UserOrderModel implements Model {
    @Override
    public Observable<BaseGson<UserOrderFormAllListGson>> getUserOrdersList(String uid,String status) {
        return RetrofitUtil.getInstance().getServerices().getUserOrdersList(uid,status);
    }

    @Override
    public Observable<BaseGson<Integer>> getUserOrdersCount(String uid, String status) {
        return RetrofitUtil.getInstance().getServerices().getUserOrdersCount(uid,status);
    }
}
