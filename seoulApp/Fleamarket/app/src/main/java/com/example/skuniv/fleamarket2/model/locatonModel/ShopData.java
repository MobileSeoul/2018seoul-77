package com.example.skuniv.fleamarket2.model.locatonModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopData {
    @SerializedName("items")
    private List<ShopModel> items;
    @SerializedName("meta")
    private LocationMeta meta;
}
