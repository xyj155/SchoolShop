package com.example.schoolshop.contract;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.BaseView;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.gson.AddressGson;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2018/11/6.
 */

public interface UserAddressContract {
    interface Model {
        Observable<BaseGson<EmptyGson>> addUserReceiveAddress(

                String username,
               String tel,
                 String address,
                 String uid,
                 String location,
                String isReceive
        );
        Observable<BaseGson<AddressGson>> getUserAddressList(String uid);
    }

    interface View extends BaseView{
        void loadSuccess();
        void loadAddressList(List<AddressGson> addressGsonList);
    }

    interface Presenter {
        void getUserAddressList(String uid);
      void   addUserReceiveAddress(

                String username,
                String tel,
                String address,
                String uid,
                String location,
                String isReceive
        );
    }
}
