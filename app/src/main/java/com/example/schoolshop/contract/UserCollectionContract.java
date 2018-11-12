package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.gson.ShopGson;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/10.
 */

public interface UserCollectionContract {
    interface Model {
        Observable<BaseGson<ShopGson>> getUserStoreCollection(String uid);

        Observable<BaseGson<GoodGson.GoodsBean>> getUserGoodsCollection(String uid);
    }

    interface View extends BaseView{
        void loadShopList(List<ShopGson> shopGsonList);

        void loadGoodsList(List<GoodGson.GoodsBean> goodGsons);
    }

    interface Presenter {
        void getUserStoreCollection(String uid);

        void getUserGoodsCollection(String uid);
    }
}
