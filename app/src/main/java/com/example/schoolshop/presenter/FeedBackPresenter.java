package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.FeedBackContract;
import com.example.schoolshop.model.FeedBackModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/18.
 */

public class FeedBackPresenter implements FeedBackContract.Presenter {
    private FeedBackContract.View view;
    private FeedBackModel model = new FeedBackModel();

    public FeedBackPresenter(FeedBackContract.View view) {
        this.view = view;
    }

    @Override
    public void Userfeedback(String uid, String content) {
        view.showLoading();
        model.Userfeedback(uid, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                        view.hideLoading();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        view.hideLoading();
                        if (emptyGsonBaseGson.isStatus()) {
                            view.submitSuccess();
                        } else {
                            view.loadFailed(emptyGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
