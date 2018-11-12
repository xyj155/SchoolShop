package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.UserGson;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/2.
 */

public interface UserContract {
    interface Model {
        Observable<BaseGson<UserGson>> userRegisterByName(String username, String password, String location);
    }

    interface View extends BaseView {
        void register(UserGson userGson);
    }

    interface Presenter {

        void userRegister(String username, String password, String location);
    }
}
