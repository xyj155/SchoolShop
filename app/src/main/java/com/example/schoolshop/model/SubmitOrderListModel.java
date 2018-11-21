package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.contract.SubmitOrderListContract;
import com.example.schoolshop.gson.SubmitOrderListGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/21.
 */

public class SubmitOrderListModel implements SubmitOrderListContract.Model {
    @Override
    public Observable<BaseGson<SubmitOrderListGson>> getUserOrdersPayList(String uid, String shopid) {
        return RetrofitUtil.getInstance().getServerices().getUserOrdersPayList(uid,shopid);
    }
}
