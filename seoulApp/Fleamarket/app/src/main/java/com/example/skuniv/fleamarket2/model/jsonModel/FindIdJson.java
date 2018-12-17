package com.example.skuniv.fleamarket2.model.jsonModel;


import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindIdJson {
    private String id;
    String email;

    public FindIdJson(String id, String email) {
        this.id = id;
        this.email = email;
    }
}

    
