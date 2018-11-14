package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.UserShopCarGson;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/14.
 */

public interface UserShopCarContract {
    interface Model {
        Observable<BaseGson<UserShopCarGson>> submitUserShopCar(String uid);
    }

    interface View extends BaseView {
        void loadShopCarList(BaseGson<UserShopCarGson> shopCarGsonBaseGson);
    }

    interface Presenter {
        void submitUserShopCar(String uid);
    }
}
