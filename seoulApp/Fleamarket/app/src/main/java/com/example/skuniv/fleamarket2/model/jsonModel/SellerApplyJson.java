package com.example.skuniv.fleamarket2.model.jsonModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerApplyJson {
    private String id;
    private String name;
    private String title;
    private String contents;

    public SellerApplyJson(String id, String name, String title, String contents) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.contents = contents;
    }
}
