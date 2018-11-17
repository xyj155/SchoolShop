package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.OrderContract;
import com.example.schoolshop.gson.UserOrderGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/17.
 */

public class OrderModel implements OrderContract.Model {
    @Override
    public Observable<BaseGson<UserOrderGson>> submitUserOrder(String uid, String gid, String in_id, String count, String money, String status) {
        return RetrofitUtil.getInstance().getServerices().submitUserOrder(uid, gid, in_id, count, money, status);
    }
}
