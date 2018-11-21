package com.example.schoolshop.contract;


import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.base.EmptyGson;

import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/21.
 */

public interface UserSubmitOrderListContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> userBuyGoodsListByShopId(String uid, String sid, String address, String tel, String money);
    }

    interface View extends BaseView {
        void submitSuccess();

        void submitFailed();
    }

    interface Presenter {
        void userBuyGoodsListByShopId(String uid, String sid, String address, String tel, String money);
    }
}
