package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.HomeContract;
import com.example.schoolshop.gson.AdGson;
import com.example.schoolshop.gson.BannerGson;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/3.
 */

public class HomeModel implements HomeContract.Model {
    @Override
    public Observable<BaseGson<BannerGson>> getBannerList(String location) {
        return RetrofitUtil.getInstance().getServerices().getBannerList(location);
    }

    @Override
    public Observable<BaseGson<GoodGson.GoodsBean>> getPurseGoodsList(String location) {
        return RetrofitUtil.getInstance().getServerices().getActiveList(location);
    }

    @Override
    public Observable<BaseGson<AdGson>> getAdBanner(String location) {
        return RetrofitUtil.getInstance().getServerices().getADBanner(location);
    }
}
