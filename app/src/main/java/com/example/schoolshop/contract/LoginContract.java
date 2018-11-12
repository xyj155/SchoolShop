package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.UserGson;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/2.
 */

public interface LoginContract {
    interface Model {
        Observable<BaseGson<UserGson>> userLogin(String username, String password);
    }

    interface View extends BaseView{
        void login(UserGson userGson);
    }

    interface Presenter {
        void userLogin(String username, String password);
    }
}
