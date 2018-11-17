package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.gson.SubmitOrderGson;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/17.
 */

public interface UserSubmitOrderContract {
    interface Model {
        Observable<BaseGson<SubmitOrderGson>> getOrderInformation(String gid, String uid);

        Observable<BaseGson<EmptyGson>> userPayGoodsOrder(String id, String address,String tel,String count,String money);
    }

    interface View extends BaseView {
        void getOrderDetail(SubmitOrderGson orderGson);

        void paySuccess();

        void payFailed();
    }

    interface Presenter {
        void getOrderInformation(String gid, String uid);

        void userPayGoodsOrder(String id, String address,String tel,String count,String money);
    }
}
