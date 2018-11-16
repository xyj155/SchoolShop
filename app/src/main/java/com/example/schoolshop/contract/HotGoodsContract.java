package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.gson.HotGoodsGson;

import java.util.List;

import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/16.
 */

public interface HotGoodsContract {
    interface Model {
        Observable<BaseGson<HotGoodsGson>> getHotGoodsList();
    }

    interface View extends BaseView{
        void loadHotGoodsList(List<HotGoodsGson> list);
    }

    interface Presenter {
        void getHotGoodsList();
    }
}
