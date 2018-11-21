package com.example.schoolshop.contract;


import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.base.EmptyGson;

import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/21.
 */

public interface GoodsOrderAddContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> addShopCarGoodsNum(String isdelete, String uid, String gid, String comment, String sid, String isbuy, String status);
    }

    interface View extends BaseView {
        void addSuccess();

        void addFailed();

    }

    interface Presenter {
        void addShopCarGoodsNum(String isdelete, String uid, String gid, String comment, String sid, String isbuy, String status);
    }
}
