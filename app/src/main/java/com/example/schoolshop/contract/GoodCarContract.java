package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.gson.GoodGson;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/7.
 */

public interface GoodCarContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> addGoodsCar(String uid, String gid, String comment, String isDelete);
        Observable<BaseGson<GoodGson.GoodsBean>> getShopCarGoodsList(String uid);
        Observable<BaseGson<EmptyGson>> deleteUserShopCar(String uid);
    }

    interface View  {
        void addSuccess();
        void delSuccess();

        void addFailed();
        void loadShopCarList(List<GoodGson.GoodsBean> goodsBeen);
        void delShopCarSuccess();
        void delShopCarFailed();
    }

    interface Presenter {
        void addGoodsCar(String uid, String gid, String comment, String isDelete);
        void getShopCarGoodsList(String uid);
       void deleteUserShopCar(String uid);
    }
}
