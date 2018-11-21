package com.example.schoolshop.model;


import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.GoodsOrderAddContract;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/21.
 */

public class GoodsOrderAddModel implements GoodsOrderAddContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> addShopCarGoodsNum(String isdelete, String uid, String gid, String comment, String sid, String isbuy, String status) {
        return RetrofitUtil.getInstance().getServerices().addShopCarGoodsNum(isdelete,uid,gid,comment,sid,isbuy,status);
    }
}
