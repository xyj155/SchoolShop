package com.example.schoolshop.model;


import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.UserSubmitOrderListContract;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/21.
 */

public class UserSubmitOrderListModel implements UserSubmitOrderListContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> userBuyGoodsListByShopId(String uid, String sid, String address, String tel, String money) {
        return RetrofitUtil.getInstance().getServerices().userBuyGoodsListByShopId(uid, sid, address, tel, money);
    }
}
