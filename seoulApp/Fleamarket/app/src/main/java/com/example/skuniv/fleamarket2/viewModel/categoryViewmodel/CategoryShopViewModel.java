package com.example.skuniv.fleamarket2.viewModel.categoryViewmodel;

import android.databinding.ObservableField;

import com.example.skuniv.fleamarket2.model.categoryModel.CategoryShopModel;
import com.example.skuniv.fleamarket2.model.locatonModel.Goods;

public class CategoryShopViewModel {
    public final ObservableField<Integer> no = new ObservableField<>();
    public final ObservableField<String> location = new ObservableField<>();
    public final ObservableField<String> shop = new ObservableField<>();
    public final ObservableField<Goods> goods = new ObservableField<>();

    public CategoryShopViewModel(CategoryShopModel categoryShopModel) {
        //this.shopModel.set(shopModel);
        System.out.println("==========="+categoryShopModel.getLocation());
        this.no.set(categoryShopModel.getNo());
        this.location.set(categoryShopModel.getLocation());
        this.shop.set(categoryShopModel.getShop());
        this.goods.set(categoryShopModel.getGoods());
    }
}