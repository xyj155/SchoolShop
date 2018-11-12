package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.UserOrderFormAllListGson;

import java.util.List;

import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/9.
 */

public interface UserOrderContract {
    interface Model {
        Observable<BaseGson<UserOrderFormAllListGson>> getUserOrdersList(String uid,String status);
    }

    interface View extends BaseView {
        void loadOrderList(List<UserOrderFormAllListGson> userOrderFormAllListGsons);
    }

    interface Presenter {
        void getUserOrdersList(String uid,String status);
    }
}
