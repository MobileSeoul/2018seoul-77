package com.example.skuniv.fleamarket2.model.AdminSellerModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel implements Serializable{
    @SerializedName("id")
    private String id="";
    @SerializedName("pw")
    private String pw="";
    @SerializedName("sex")
    private String sex="";
    @SerializedName("name")
    private String name="";
    @SerializedName("email")
    private String email="";
    @SerializedName("shop")
    private String shop ="";
    @SerializedName("role")
    private int role=-1;
    @SerializedName("response")
    private String response;

    public UserModel() {

    }
}
