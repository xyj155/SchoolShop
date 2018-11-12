package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.UserAddressContract;
import com.example.schoolshop.gson.AddressGson;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/6.
 */

public class UserAddressModel implements UserAddressContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> addUserReceiveAddress(String username, String tel, String address, String uid, String location, String isReceive) {
        return RetrofitUtil.getInstance().getServerices().addUserReceiveAddress(username, tel, address, uid, location, isReceive);
    }

    @Override
    public Observable<BaseGson<AddressGson>> getUserAddressList(String uid) {
        return RetrofitUtil.getInstance().getServerices().getUserAddressList(uid);
    }
}
