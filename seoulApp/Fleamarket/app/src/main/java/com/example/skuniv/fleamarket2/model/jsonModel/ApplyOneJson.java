package com.example.skuniv.fleamarket2.model.jsonModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyOneJson {
    private String id;
    private int role;
    private String response;

    public ApplyOneJson(String id, int role) {
        this.id = id;
        this.role = role;
    }
}
