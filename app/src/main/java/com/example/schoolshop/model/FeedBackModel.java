package com.example.schoolshop.model;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.FeedBackContract;
import com.example.schoolshop.http.RetrofitUtil;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/18.
 */

public class FeedBackModel implements FeedBackContract.Model {
    @Override
    public Observable<BaseGson<EmptyGson>> Userfeedback(String uid, String content) {
        return RetrofitUtil.getInstance().getServerices().Userfeedback(uid, content);
    }
}
