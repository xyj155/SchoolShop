package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.base.EmptyGson;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/18.
 */

public interface FeedBackContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> Userfeedback(String uid, String content);
    }

    interface View extends BaseView {
        void submitSuccess();
    }

    interface Presenter {
        void Userfeedback(String uid, String content);
    }
}
