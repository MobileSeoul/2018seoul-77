package com.example.skuniv.fleamarket2.model.locatonModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionModel {
    private String section;
    private int sectionNum;

    public SectionModel(String section, int sectionNum){
        this.section = section;
        this.sectionNum = sectionNum;
    }
}
