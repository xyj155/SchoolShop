package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.SecondHandGson;

import rx.Observable;

public interface SecondHandsContract {
    interface Model {
        Observable<BaseGson<SecondHandGson>> getSecondHandsByLocation(String location, String kind);
    }

    interface View extends BaseView {
        void loadSecondList(SecondHandGson secondHandGson);
    }

    interface Presenter {
        void getSecondHandsByLocation(String location, String kind);
    }
}