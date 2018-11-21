package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.SubmitOrderListGson;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/21.
 */

public interface SubmitOrderListContract {
    interface Model {
        Observable<BaseGson<SubmitOrderListGson>> getUserOrdersPayList(String uid, String shopid);
    }

    interface View extends BaseView {
        void loadOrderList(SubmitOrderListGson submitOrderListGson);
    }

    interface Presenter {
        void getUserOrdersPayList(String uid,  String shopid);
    }
}
