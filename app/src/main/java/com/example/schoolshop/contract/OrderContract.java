package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.gson.UserOrderGson;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/17.
 */

public interface OrderContract {
    interface Model {
        Observable<BaseGson<UserOrderGson>> submitUserOrder(String uid, String gid, String in_id, String count, String money, String status);
    }

    interface View {
        void submitSuccess(UserOrderGson userOrderGson);

        void submitFailed();
    }

    interface Presenter {
        void submitUserOrder(String uid, String gid, String in_id, String count, String money, String status);
    }
}
