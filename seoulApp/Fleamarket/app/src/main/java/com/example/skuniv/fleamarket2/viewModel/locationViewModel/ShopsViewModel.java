package com.example.skuniv.fleamarket2.viewModel.locationViewModel;

import android.databinding.ObservableArrayList;
import android.util.Log;

import com.example.skuniv.fleamarket2.model.locatonModel.ShopModel;

import java.util.List;

public class ShopsViewModel{
    public final ObservableArrayList<ShopViewModel> shops = new ObservableArrayList<>();

    public void setShops(List<ShopModel> shopModels){
//        Log.i("adapter", shopModels.get(0).getGoods().get(0).getImage());
        shops.clear();
        for(int i =0; i<shopModels.size() ;i++){
            //shops.add(new ShopViewModel(shopModels.get(i).getNo(),shopModels.get(i).getLocation(),shopModels.get(i).getShop()));
            shops.add(new ShopViewModel(shopModels.get(i)));
        }
    }
}
