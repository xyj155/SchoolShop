package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.ShopGson;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/5.
 */

public interface ShopContract {
    interface Model {
        Observable<BaseGson<ShopGson>> getSellerList(String location);

        Observable<BaseGson<ShopGson>> getSellerDetailById(String uid,String id);
    }

    interface View extends BaseView {
        void loadShopList(List<ShopGson> shopGsonList);
        void loadShopDetail(ShopGson shopGsonList);
    }

    interface Presenter {
        void getSellerList(String location);

        void getSellerDetailById(String uid,String id);
    }
}
