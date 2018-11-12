package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.CouponGson;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/12.
 */

public interface CouponContract {
    interface Model {
        Observable<BaseGson<CouponGson>> getCouponListByLocation(String uid,String kind,String location);
    }

    interface View extends BaseView{
        void loadCouponData(CouponGson couponGson);

        void loadCouponList(List<CouponGson.CouponBean> couponGson);
    }

    interface Presenter {
       void getCouponListByLocation(String uid,String kind,String location);
    }
}
