package com.example.skuniv.fleamarket2.model.jsonModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyJson {
    private int role;
    private String shop;
    private String text;
    public ApplyJson(String id, int role, String text) {
        this.shop = shop;
        this.role = role;
        this.text = text;
    }

}
