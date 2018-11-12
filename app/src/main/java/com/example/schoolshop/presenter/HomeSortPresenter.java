package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.HomeSortContract;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.model.HomeSortModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 徐易杰 on 2018/11/1.
 */

public class HomeSortPresenter implements HomeSortContract.Presenter {
    private HomeSortContract.View view;
    private HomeSortModel homeSortModel = new HomeSortModel();

    public HomeSortPresenter(HomeSortContract.View view) {
        this.view = view;
    }

    @Override
    public void getGoodsListByLocation(String location,String kind,String uid) {
        view.showLoading();
        homeSortModel.getGoodsListByLocation(location,kind,uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<GoodGson>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<GoodGson> goodGsonBaseGson) {
                        view.hideLoading();
                        if (goodGsonBaseGson.isStatus()) {
                            view.loadGoodsList(goodGsonBaseGson.getData());
                        } else {
                            view.loadFailed(goodGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
