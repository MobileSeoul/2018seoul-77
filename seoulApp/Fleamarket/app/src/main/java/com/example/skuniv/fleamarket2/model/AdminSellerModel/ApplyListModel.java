package com.example.skuniv.fleamarket2.model.AdminSellerModel;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyListModel {
    private List<ApplyModel> applyList;
    private int applyCount = 200;
    private int page = 1;
}
