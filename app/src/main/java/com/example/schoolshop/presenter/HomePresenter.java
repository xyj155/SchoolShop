package com.example.schoolshop.presenter;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseObserver;
import com.example.schoolshop.contract.HomeContract;
import com.example.schoolshop.entity.HomeDataEntity;
import com.example.schoolshop.gson.BannerGson;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.model.HomeModel;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/11/3.
 */

public class HomePresenter implements HomeContract.Presenter {
    private HomeModel homeModel = new HomeModel();
    private HomeContract.View view;

    public HomePresenter(HomeContract.View view) {
        this.view = view;
    }

    @Override
    public void getHomeData(String location) {
        view.showLoading();
        Observable.zip(homeModel.getBannerList(location), homeModel.getPurseGoodsList(location), new Func2<BaseGson<BannerGson>, BaseGson<GoodGson.GoodsBean>, HomeDataEntity>() {
            @Override
            public HomeDataEntity call(BaseGson<BannerGson> bannerGsonBaseGson, BaseGson<GoodGson.GoodsBean> goodGsonBaseGson) {
                return new HomeDataEntity(goodGsonBaseGson.getList(), bannerGsonBaseGson.getList());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<HomeDataEntity>() {
                    @Override
                    public void onError(String error) {
                        view.hideLoading();
                        view.loadFailed(error);
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onNext(HomeDataEntity homeDataEntity) {
                        view.hideLoading();
                        view.loadHomeData(homeDataEntity.getBannerGsons(),homeDataEntity.getGoodGson());
                    }
                });
    }
}
