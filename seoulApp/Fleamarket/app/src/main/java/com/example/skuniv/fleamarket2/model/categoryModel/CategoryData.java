package com.example.skuniv.fleamarket2.model.categoryModel;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryData {
    private List<CategoryShopModel> items;
    private CategoryMeta meta;
}
