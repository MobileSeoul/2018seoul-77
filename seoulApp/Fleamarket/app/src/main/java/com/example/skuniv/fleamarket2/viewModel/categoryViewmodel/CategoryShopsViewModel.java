package com.example.skuniv.fleamarket2.viewModel.categoryViewmodel;

import android.databinding.ObservableArrayList;

import com.example.skuniv.fleamarket2.model.categoryModel.CategoryShopModel;

import java.util.List;

public class CategoryShopsViewModel {
    public final ObservableArrayList<CategoryShopViewModel> shops = new ObservableArrayList<>();

    public void setCategoryShopsViewModel(List<CategoryShopModel> shopList){
        for(int i =0; i<shopList.size() ;i++){
            shops.add(new CategoryShopViewModel(shopList.get(i)));
        }
        System.out.println("====================set shop data ");
    }
    public ObservableArrayList<CategoryShopViewModel> getShops() {
        return shops;
    }
}
