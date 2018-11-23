package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.GoodsDetailGson;
import com.example.schoolshop.gson.GoodsPrice;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/3.
 */

public interface GoodDetailContract {
    interface Model {
        Observable<BaseGson<GoodsDetailGson>> getGoodsDetail(String uid,String goodId, String kind);

        Observable<BaseGson<GoodsPrice>> getGoodsPrice(String gid, String color, String model, String size);
    }

    interface View extends BaseView {
        void setGoodDetail(GoodsDetailGson commentGson);
        void setPrice(List<GoodsPrice> price);
    }

    interface Presenter {
        void getGoodsDetail(String uid,String goodId, String kind);

        void getGoodsPrice(String gid, String color, String model, String size);
    }
}
