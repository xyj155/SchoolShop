package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.CouponContract;
import com.example.schoolshop.gson.CouponGson;
import com.example.schoolshop.model.CouponModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/12.
 */

public class CouponPresenter implements CouponContract.Presenter {
    private CouponModel couponModel = new CouponModel();
    private CouponContract.View view;

    public CouponPresenter(CouponContract.View view) {
        this.view = view;
    }

    @Override
    public void getCouponListByLocation(String uid, final String kind, String location) {
        view.showLoading();
        couponModel.getCouponListByLocation(uid, kind,location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseGson<CouponGson>>() {
                    @Override
                    public void onError(String error) {
                     view.hideLoading();
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(BaseGson<CouponGson> couponGsonBaseGson) {
                        view.hideLoading();
                        if (couponGsonBaseGson.isStatus()){
                            if (kind.isEmpty()){
                                view.loadCouponData(couponGsonBaseGson.getData());
                            }else {
                                view.loadCouponList(couponGsonBaseGson.getData().getCoupon());
                            }

                        }else {
                            view.loadFailed(couponGsonBaseGson.getMsg());
                        }
                    }
                });
    }
}
