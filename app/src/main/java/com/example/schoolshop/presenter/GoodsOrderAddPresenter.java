package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.contract.GoodsOrderAddContract;
import com.example.schoolshop.model.GoodsOrderAddModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by 徐易杰 on 2018/11/21.
 */

public class GoodsOrderAddPresenter implements GoodsOrderAddContract.Presenter {
    private GoodsOrderAddModel goodsOrderAddModel = new GoodsOrderAddModel();
    private GoodsOrderAddContract.View view;

    public GoodsOrderAddPresenter(GoodsOrderAddContract.View view) {
        this.view = view;
    }

    @Override
    public void addShopCarGoodsNum(String isdelete, String uid, String gid, String comment, String sid, String isbuy, String status) {
        goodsOrderAddModel.addShopCarGoodsNum(isdelete, uid, gid, comment, sid, isbuy, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<EmptyGson>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                        view.addFailed();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<EmptyGson> emptyGsonBaseGson) {
                        if (emptyGsonBaseGson.isStatus()){
                            view.addSuccess();
                        }else {
                            view.addFailed();
                        }
                    }
                });
    }
}
