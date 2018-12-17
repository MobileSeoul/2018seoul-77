package com.example.skuniv.fleamarket2.model.locatonModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopModel implements Serializable{
    @SerializedName("no")
    private int no;
    @SerializedName("location")
    private String location;
    @SerializedName("shop")
    private String shop;
    @SerializedName("goods")
    private List<Goods> goods;

    public ShopModel(int no, String location, String shop, List<Goods> goods){
        this.no = no;
        this.location = location;
        this.shop = shop;
        this.goods = goods;
    }
}