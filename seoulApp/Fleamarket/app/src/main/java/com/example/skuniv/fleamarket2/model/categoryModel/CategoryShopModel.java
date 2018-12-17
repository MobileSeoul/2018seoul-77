package com.example.skuniv.fleamarket2.model.categoryModel;

import com.example.skuniv.fleamarket2.model.locatonModel.Goods;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryShopModel{
    @SerializedName("no")
    private int no;
    @SerializedName("location")
    private String location;
    @SerializedName("shop")
    private String shop;
    @SerializedName("good")
    private Goods goods;

    public CategoryShopModel(int no, String location, String shop, Goods goods){
        this.no = no;
        this.location = location;
        this.shop = shop;
        this.goods = goods;
    }
}
