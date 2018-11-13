package com.example.schoolshop.api;

import com.example.schoolshop.base.BaseGson;
import com.example.schoolshop.base.EmptyGson;
import com.example.schoolshop.gson.AdGson;
import com.example.schoolshop.gson.AddressGson;
import com.example.schoolshop.gson.BannerGson;
import com.example.schoolshop.gson.CouponGson;
import com.example.schoolshop.gson.ExpressGson;
import com.example.schoolshop.gson.GoodGson;
import com.example.schoolshop.gson.GoodsDetailGson;
import com.example.schoolshop.gson.GoodsPrice;
import com.example.schoolshop.gson.LeanCloudGson;
import com.example.schoolshop.gson.PostPackageGson;
import com.example.schoolshop.gson.RunHelperGson;
import com.example.schoolshop.gson.SecondHandGson;
import com.example.schoolshop.gson.ShopGson;
import com.example.schoolshop.gson.UserGson;
import com.example.schoolshop.gson.UserOrderFormAllListGson;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 徐易杰 on 2018/11/1.
 */

public interface Api {
    @GET("/SchoolShop/public/index.php/index/Goods/getGoodsListByLocation")
    Observable<BaseGson<GoodGson>> getGoodsListByLocation(@Query("location") String location, @Query("kind") String kind, @Query("uid") String uid);

    @GET("/SchoolShop/public/index.php/index/Secondhand/getSecondHandsByLocation")
    Observable<BaseGson<SecondHandGson>> getSecondHandsByLocation(@Query("location") String id, @Query("kind") String kind);
    @FormUrlEncoded
    @POST("/SchoolShop/public/index.php/index/User/login")
    Observable<BaseGson<UserGson>> userLogin(@Field("username") String username, @Field("password") String password);

    @Headers({
            "X-LC-Id:fhk7oAJlYQnAXDhVENN9aBlL-gzGzoHsz",
            "X-LC-Key:qecrVBKPwUyz4TgSpcHnjURF",
            "Content-Type:application/json"
    })
    @POST("/1.1/requestSmsCode")
    Observable<LeanCloudGson> requestSms(@Body RequestBody params);

    @Headers({
            "X-LC-Id:fhk7oAJlYQnAXDhVENN9aBlL-gzGzoHsz",
            "X-LC-Key:qecrVBKPwUyz4TgSpcHnjURF",
            "Content-Type:application/json"
    })
    @POST("/1.1/verifySmsCode/{code}")
    Observable<LeanCloudGson> verifySmsCode(@Body RequestBody params, @Path("code") String code);

    @FormUrlEncoded
    @POST("/SchoolShop/public/index.php/index/User/register")
    Observable<BaseGson<UserGson>> userRegister(@Field("username") String username, @Field("password") String password, @Field("location") String location);


    @GET("/SchoolShop/public/index.php/index/Home/getBanner")
    Observable<BaseGson<BannerGson>> getBannerList(@Query("location") String location);

    @GET("/SchoolShop/public/index.php/index/Home/getActiveList")
    Observable<BaseGson<GoodGson.GoodsBean>> getActiveList(@Query("location") String location);

    @GET("/SchoolShop/public/index.php/index/Goods/getGoodsDetail")
    Observable<BaseGson<GoodsDetailGson>> getGoodsDetail(@Query("good_id") String goodId, @Query("kind") String kind);

    @GET("/SchoolShop/public/index.php/index/Seller/getSellerList")
    Observable<BaseGson<ShopGson>> getSellerList(@Query("location") String location);

    @GET("/SchoolShop/public/index.php/index/Store/getSellerDetailById")
    Observable<BaseGson<ShopGson>> getSellerDetailById(@Query("id") String id);

    @GET("/SchoolShop/public/index.php/index/Deliver/getUserPackageListById")
    Observable<BaseGson<PostPackageGson>> getUserPackageListById(@Query("uid") String uid);

    @GET("/SchoolShop/public/index.php/index/User/getUserAddressList")
    Observable<BaseGson<AddressGson>> getUserAddressList(@Query("uid") String uid);

    @GET("/SchoolShop/public/index.php/index/Goods/getShopCarGoodsList")
    Observable<BaseGson<GoodGson.GoodsBean>> getShopCarGoodsList(@Query("uid") String uid);

    @FormUrlEncoded
    @POST("/SchoolShop/public/index.php/index/User/addUserReceiveAddress")
    Observable<BaseGson<EmptyGson>> addUserReceiveAddress(
            @Field("username") String username,
            @Field("tel") String tel,
            @Field("address") String address,
            @Field("uid") String uid,
            @Field("location") String location,
            @Field("is_receive") String isReceive
    );

    @GET("/SchoolShop/public/index.php/index/Buyer/addGoodsCar")
    Observable<BaseGson<EmptyGson>> addGoodsCar(@Query("uid") String uid, @Query("gid") String gid, @Query("comment") String comment, @Query("isDelete") String isDelete);

    @GET("/SchoolShop/public/index.php/index/User/getUserOrdersList")
    Observable<BaseGson<UserOrderFormAllListGson>> getUserOrdersList(@Query("uid") String uid, @Query("status") String status);

    @GET("/SchoolShop/public/index.php/index/User/getUserStoreCollection")
    Observable<BaseGson<ShopGson>> getUserStoreCollection(@Query("uid") String uid);

    @GET("/SchoolShop/public/index.php/index/User/getUserGoodsCollection")
    Observable<BaseGson<GoodGson.GoodsBean>> getUserGoodsCollection(@Query("uid") String uid);
    @GET("/SchoolShop/public/index.php/index/Deliver/getUserPackageById")
    Observable<BaseGson<ExpressGson>> getUserPackageById(@Query("id") String id);

    @GET("/SchoolShop/public/index.php/index/Order/getRunHelperByLocation")
    Observable<BaseGson<RunHelperGson>> getRunHelperByLocation(@Query("location") String location);
    @GET("/SchoolShop/public/index.php/index/Home/getADBanner")
    Observable<BaseGson<AdGson>> getADBanner(@Query("location") String location);
    @GET("/SchoolShop/public/index.php/index/Coupon/getCouponListByLocation")
    Observable<BaseGson<CouponGson>> getCouponListByLocation(@Query("uid") String uid,@Query("kind") String kind, @Query("location") String location);

    @GET("/SchoolShop/public/index.php/index/Goods/setUserOrder")
    Observable<BaseGson<EmptyGson>> setUserOrder(@Query("uid") String uid);

    @GET("/SchoolShop/public/index.php/index/Order/submitRunHelperOrder")
    Observable<BaseGson<EmptyGson>> submitRunHelperOrder(@Query("uid") String uid,@Query("hid") String hid,@Query("request") String request);

    @GET("/SchoolShop/public/index.php/index/Goods/getGoodsPrice")
    Observable<BaseGson<GoodsPrice>> getGoodsPrice(@Query("gid") String gid, @Query("color") String color, @Query("model") String model, @Query("size") String size);

}
