package com.example.skuniv.fleamarket2.model.locatonModel;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationMeta {
    @SerializedName("count")
    private int shopCount;
}
