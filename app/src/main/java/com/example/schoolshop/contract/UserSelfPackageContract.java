package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.gson.PostPackageGson;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/15.
 */

public interface UserSelfPackageContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> addUserSelfPackage(String uId, String num, String code, String name, String comment);
        Observable<BaseGson<PostPackageGson>> getUserAddedExpressList( String uid);
    }

    interface View extends BaseView {
        void addSuccess();
        void loadExpressList(List<PostPackageGson> postPackageGsonList);
    }

    interface Presenter {
        void addUserSelfPackage(String uId, String num, String code, String name, String comment);
        void getUserAddedExpressList( String uid);
    }
}
