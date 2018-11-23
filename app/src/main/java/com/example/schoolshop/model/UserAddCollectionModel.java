package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.UserAddCollectionContract;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/22.
 */

public class UserAddCollectionModel implements UserAddCollectionContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> UserAddCollection(String uid, String gid, String isshop, String isdelete) {
        return RetrofitUtil.getInstance().getServerices().UserAddCollection(uid,gid,isshop,isdelete);
    }
}
