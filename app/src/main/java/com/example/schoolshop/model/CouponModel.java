package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.CouponContract.Model;
import com.example.schoolshop.gson.CouponGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/12.
 */

public class CouponModel implements Model {
    @Override
    public Observable<BaseGson<CouponGson>> getCouponListByLocation(String uid,String kind, String location) {
        return RetrofitUtil.getInstance().getServerices().getCouponListByLocation(uid,kind,location);
    }
}
