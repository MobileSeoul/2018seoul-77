package com.example.skuniv.fleamarket2.model.jsonModel;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseJson {
    @SerializedName("response")
    private String response;
}
