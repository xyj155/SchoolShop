package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;

import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/22.
 */

public interface UserAddCollectionContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> UserAddCollection(String uid, String gid, String isshop, String isdelete);
    }

    interface View {
        void addCollectionSuccess();
        void addCollectionFailed(String error);

    }

    interface Presenter {
        void UserAddCollection(String uid, String gid, String isshop, String isdelete);
    }
}
