package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.UserAddCollectionContract;
import com.example.schoolshop.model.UserAddCollectionModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by 徐易杰 on 2018/11/22.
 */

public class UserAddCollectionPresenter implements UserAddCollectionContract.Presenter {
    private UserAddCollectionContract.View view;

    public UserAddCollectionPresenter(UserAddCollectionContract.View view) {
        this.view = view;

    }

    private UserAddCollectionModel userAddCollectionModel = new UserAddCollectionModel();

    @Override
    public void UserAddCollection(String uid, String gid, String isshop, String isdelete) {
        userAddCollectionModel.UserAddCollection(uid, gid, isshop, isdelete)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onError(String error) {
                        view.addCollectionFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        if (emptyGsonBaseGson.isStatus()) {
                            view.addCollectionSuccess();
                        } else {
                            view.addCollectionFailed(emptyGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
