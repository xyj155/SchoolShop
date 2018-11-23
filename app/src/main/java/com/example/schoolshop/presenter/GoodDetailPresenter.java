package com.example.schoolshop.presenter;

import android.util.Log;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.GoodDetailContract;
import com.example.schoolshop.gson.GoodsDetailGson;
import com.example.schoolshop.gson.GoodsPrice;
import com.example.schoolshop.model.GoodDetailModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/3.
 */

public class GoodDetailPresenter implements GoodDetailContract.Presenter {
    private GoodDetailModel model = new GoodDetailModel();
    private GoodDetailContract.View view;

    public GoodDetailPresenter(GoodDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void getGoodsDetail(String uid,String goodId,String kind) {
        view.showLoading();
        model.getGoodsDetail(uid,goodId,kind)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<GoodsDetailGson>>() {
                    @Override
                    public void onError(String error) {
                        view.loadFailed(error);
                        view.hideLoading();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<GoodsDetailGson> commentGsonBaseGson) {
                        view.hideLoading();
                        if (commentGsonBaseGson.isStatus()) {
                            view.setGoodDetail(commentGsonBaseGson.getData());
                        } else {
                            view.loadFailed(commentGsonBaseGson.getMsg());
                        }
                    }
                });
    }

    @Override
    public void getGoodsPrice(String gid, String color, String models, String size) {
        Log.i(TAG, "onNext: gid"+gid);
        Log.i(TAG, "onNext:color "+color);
        Log.i(TAG, "onNext:models "+models);
        Log.i(TAG, "onNext:size "+size);
        view.showLoading();
        model.getGoodsPrice(gid,color,models,size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<GoodsPrice>>() {
                    @Override
                    public void onError(String error) {
                        view.hideLoading();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<GoodsPrice> commentGsonBaseGson) {
                        view.hideLoading();
                        Log.i(TAG, "onNext: "+commentGsonBaseGson);
                        if (commentGsonBaseGson.isStatus()) {
                            view.setPrice(commentGsonBaseGson.getList());
                        }
                    }
                });
    }

    private static final String TAG = "GoodDetailPresenter";
}
