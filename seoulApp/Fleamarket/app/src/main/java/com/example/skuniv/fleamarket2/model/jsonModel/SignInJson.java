package com.example.skuniv.fleamarket2.model.jsonModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInJson {
    private String id;
    private String pw;

    public SignInJson(String id, String pw) {
        this.id = id;
        this.pw = pw;
    }
}
