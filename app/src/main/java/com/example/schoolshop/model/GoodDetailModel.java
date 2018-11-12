package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.GoodDetailContract;
import com.example.schoolshop.gson.GoodsDetailGson;
import com.example.schoolshop.gson.GoodsPrice;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/3.
 */

public class GoodDetailModel implements GoodDetailContract.Model {
    @Override
    public Observable<BaseGson<GoodsDetailGson>> getGoodsDetail(String goodId,String kind) {
        return RetrofitUtil.getInstance().getServerices().getGoodsDetail(goodId,kind);
    }

    @Override
    public Observable<BaseGson<GoodsPrice>> getGoodsPrice(String gid, String color, String model, String size) {
        return RetrofitUtil.getInstance().getServerices().getGoodsPrice(gid,color,model,size);
    }
}
