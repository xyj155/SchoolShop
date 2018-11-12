package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.gson.RunHelperGson;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/11.
 */

public interface RunHelperContract {
    interface Model {
        Observable<BaseGson<RunHelperGson>> getRunHelperByLocation(String location);
        Observable<BaseGson<EmptyGson>> submitRunHelperOrder(String uid, String hid,String request);
        Observable<BaseGson<EmptyGson>>  submitOrder(String uid, String hid,String request);
    }

    interface View extends BaseView{
        void loadHelperList(List<RunHelperGson> runHelperGsons);
        void success();
        void setOrderSuccess();
        void setOrderFailed();
    }

    interface Presenter {
        void getRunHelperByLocation(String location);
        void submitRunHelperOrder(String uid, String hid,String request);
        void submitOrder(String uid, String hid,String request);
    }
}
