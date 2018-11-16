package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.HotGoodsContract;
import com.example.schoolshop.gson.HotGoodsGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/16.
 */

public class HotGoodsModel implements HotGoodsContract.Model {
    @Override
    public Observable<BaseGson<HotGoodsGson>> getHotGoodsList() {
        return RetrofitUtil.getInstance().getServerices().getHotGoodsList();
    }
}
