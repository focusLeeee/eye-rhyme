package com.example.chenzhe.eyerhyme.model;

/**
 * Created by chenzhe on 2016/5/5.
 */
public class ProductItem {
//    “product_id”: integer, 电影产品的唯一标识
//    “price”: integer, 电影票价格
//    “round”: integer, 放映场次
//    “hall”: integer, 放映影厅
//    “type”: integer, 放映类型
//    “discount”: integer, 折扣数量
    private int product_id;
    private int price;
    private int round;
    private int hall;
    private int type;
    private int discount;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getHall() {
        return hall;
    }

    public void setHall(int hall) {
        this.hall = hall;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
