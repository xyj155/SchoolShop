package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.BannerGson;
import com.example.schoolshop.gson.GoodGson;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/3.
 */

public interface HomeContract {
    interface Model {
        Observable<BaseGson<BannerGson>> getBannerList(String location);

        Observable<BaseGson<GoodGson.GoodsBean>> getPurseGoodsList(String location);
    }

    interface View extends BaseView{
        void loadHomeData(List<BannerGson> bannerGsons,List<GoodGson.GoodsBean> goodGsons);
    }

    interface Presenter {
        void getHomeData(String location);

    }
}
