package com.example.schoolshop.gson;

/**
 * Created by 徐易杰 on 2018/11/9.
 */

public class UserOrderFormAllListGson {

    /**
     * id : 1
     * goods_id : 101
     * user_id : 1
     * goods_count : 4
     * total_money : 500.000
     * status : 1
     * express_name : 顺丰快递
     * express_num : 23123131
     * time : 2018-11-09 11:43:23
     * goods : {"id":101,"goods_name":"FILA斐乐女羽绒马甲2018冬季新品运动休闲轻质保暖连帽运动马夹女","goods_location":"嘉兴","goods_tags":"连帽","goods_price":"50.00","goods_pic":"https://img.alicdn.com/imgextra/i2/676606897/O1CN0120os8G5TgI2MfPQ_!!676606897.jpg_430x430q90.jpg","goods_describe":"FILA斐乐女羽绒马甲2018冬季新品运动休闲轻质保暖连帽运动马夹女","goods_stock":"166","goods_owner":"1","updatetime":"2018-11-08 12:14:33","goods_kind":"衣服","is_purse":"1","original_price":null}
     */

    private int id;
    private int goods_id;
    private int user_id;
    private int goods_count;
    private String total_money;
    private int status;
    private String express_name;
    private String express_num;
    private String time;
    private GoodGson.GoodsBean goods;
    private ShopGson shop;

    public ShopGson getShop() {
        return shop;
    }

    public void setShop(ShopGson shop) {
        this.shop = shop;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(int goods_count) {
        this.goods_count = goods_count;
    }

    public String getTotal_money() {
        return total_money;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }

    public String getExpress_num() {
        return express_num;
    }

    public void setExpress_num(String express_num) {
        this.express_num = express_num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public GoodGson.GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodGson.GoodsBean goods) {
        this.goods = goods;
    }

}
