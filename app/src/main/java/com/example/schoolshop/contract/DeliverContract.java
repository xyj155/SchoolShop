package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.PostPackageGson;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/6.
 */

public interface DeliverContract {
    interface Model {
        Observable<BaseGson<PostPackageGson>> getUserPackageListById(String uid);
    }

    interface View extends BaseView{
        void loadUserPackageListById(List<PostPackageGson> postPackageGsonList);
    }

    interface Presenter {
       void getUserPackageListById(String uid);
    }
}
