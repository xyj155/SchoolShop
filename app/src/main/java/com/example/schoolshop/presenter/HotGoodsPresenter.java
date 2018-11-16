package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.HotGoodsContract;
import com.example.schoolshop.gson.HotGoodsGson;
import com.example.schoolshop.model.HotGoodsModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 徐易杰 on 2018/11/16.
 */

public class HotGoodsPresenter implements HotGoodsContract.Presenter {
    private HotGoodsModel hotGoodsModel = new HotGoodsModel();
    private HotGoodsContract.View view;

    public HotGoodsPresenter(HotGoodsContract.View view) {
        this.view = view;
    }

    @Override
    public void getHotGoodsList() {
        view.showLoading();
        hotGoodsModel.getHotGoodsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<HotGoodsGson>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                        view.hideLoading();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<HotGoodsGson> hotGoodsGsonBaseGson) {
                        view.hideLoading();
                        if (hotGoodsGsonBaseGson.isStatus()) {
                            view.loadHotGoodsList(hotGoodsGsonBaseGson.getList());
                        } else {
                            view.loadFailed(hotGoodsGsonBaseGson.getMsg());
                        }
                    }
                });

    }
}
