package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.GoodGson;

import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/1.
 */

public interface HomeSortContract {
    interface Model {
        Observable<BaseGson<GoodGson>> getGoodsListByLocation(String location, String kind, String uid);
    }

    interface View extends BaseView {
        void loadGoodsList(GoodGson goodGson);
    }

    interface Presenter {
        void getGoodsListByLocation(String location, String kind, String uid);
    }
}
