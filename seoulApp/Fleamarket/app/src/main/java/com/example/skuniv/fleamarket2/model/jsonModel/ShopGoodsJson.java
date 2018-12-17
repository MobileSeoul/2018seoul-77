package com.example.skuniv.fleamarket2.model.jsonModel;

import lombok.Getter;
import lombok.Setter;
import retrofit2.http.GET;

/**
 * Created by c2619 on 2018-09-29.
 */

@Getter
@Setter
public class ShopGoodsJson {
    private String shop;
    private String name;

    public ShopGoodsJson(String shop, String name) {
        this.shop = shop;
        this.name = name;
    }
}
