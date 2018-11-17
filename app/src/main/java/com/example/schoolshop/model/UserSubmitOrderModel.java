package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.UserSubmitOrderContract;
import com.example.schoolshop.gson.SubmitOrderGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/17.
 */

public class UserSubmitOrderModel implements UserSubmitOrderContract.Model {
    @Override
    public Observable<BaseGson<SubmitOrderGson>> getOrderInformation(String gid, String uid) {
        return RetrofitUtil.getInstance().getServerices().getOrderInformation(gid,uid);
    }

    @Override
    public Observable<BaseGson<EmptyGson>> userPayGoodsOrder(String id, String address,String tel,String count,String money) {
        return RetrofitUtil.getInstance().getServerices().userPayGoodsOrder(id,address,tel,count,money);
    }
}
