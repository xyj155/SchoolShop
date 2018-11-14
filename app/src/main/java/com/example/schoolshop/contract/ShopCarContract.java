package com.example.schoolshop.contract;


import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;

import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/13.
 */

public interface ShopCarContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> setUserOrder(String uid);
    }

    interface View {
        void submitSuccess();
        void submitFailed(String msg);
    }

    interface Presenter {
        void setUserOrder(String uid);
    }
}
