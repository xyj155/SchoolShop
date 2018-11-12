package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.ExpressGson;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/10.
 */

public interface ExpressContract {
    interface Model {
        Observable<BaseGson<ExpressGson>> getUserPackageById(String id);
    }

    interface View extends BaseView {
        void loadExpress(ExpressGson expressGson);
    }

    interface Presenter {
        void getUserPackageById(String id);
    }
}
