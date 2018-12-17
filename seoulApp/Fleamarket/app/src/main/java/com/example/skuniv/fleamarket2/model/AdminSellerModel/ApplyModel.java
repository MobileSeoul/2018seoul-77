package com.example.skuniv.fleamarket2.model.AdminSellerModel;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyModel {
    @SerializedName("id")
    private String id="";
    @SerializedName("name")
    private String name="";
    @SerializedName("title")
    private String title="";
    @SerializedName("contents")
    private String contents="";
    @SerializedName("role")
    private int role=-1;
    @SerializedName("date")
    private String date="";
}
