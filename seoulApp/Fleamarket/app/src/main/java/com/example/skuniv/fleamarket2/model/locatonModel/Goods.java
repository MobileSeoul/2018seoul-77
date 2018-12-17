package com.example.skuniv.fleamarket2.model.locatonModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Goods {
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private int price;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("category")
    private ArrayList<Integer> category;
    @SerializedName("image")
    private String image;

    public Goods(String name,int price, int quantity){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Goods(String name,int price, int quantity, ArrayList<Integer> category, String image){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.image = image;
    }
}
