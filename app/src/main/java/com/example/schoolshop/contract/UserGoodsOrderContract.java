package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/18.
 */

public interface UserGoodsOrderContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> updateOrderStatus(String id);
    }

    interface View {
        void updateSuccess();

        void updateFailed();
    }

    interface Presenter {
        void updateOrderStatus(String id);
    }
}
