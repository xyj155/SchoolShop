package com.example.schoolshop.entity;

import com.example.schoolshop.gson.BannerGson;
import com.example.schoolshop.gson.GoodGson;

import java.util.List;

/**
 * Created by Administrator on 2018/11/3.
 */

public class HomeDataEntity {
    private List<GoodGson.GoodsBean> goodGson;

    public HomeDataEntity(List<GoodGson.GoodsBean> goodGson, List<BannerGson> bannerGsons) {
        this.goodGson = goodGson;
        this.bannerGsons = bannerGsons;
    }

    public List<GoodGson.GoodsBean> getGoodGson() {
        return goodGson;
    }

    public void setGoodGson(List<GoodGson.GoodsBean> goodGson) {
        this.goodGson = goodGson;
    }

    public List<BannerGson> getBannerGsons() {
        return bannerGsons;
    }

    public void setBannerGsons(List<BannerGson> bannerGsons) {
        this.bannerGsons = bannerGsons;
    }

    private List<BannerGson> bannerGsons;

}
