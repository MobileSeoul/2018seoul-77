package com.example.skuniv.fleamarket2.model.jsonModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpJson {
    private String id;
    private String pw;
    private String name;
    private String sex;
    private String email;
    private String token;

    public SignUpJson(String id, String pw, String name, String sex, String email, String token) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.sex = sex;
        this.email = email;
        this.token = token;
    }
}
