package com.example.skuniv.fleamarket2.viewModel.locationViewModel;

import android.util.Log;

import com.example.skuniv.fleamarket2.model.locatonModel.ShopData;
import com.example.skuniv.fleamarket2.retrofit.NetRetrofit;
import com.example.skuniv.fleamarket2.databinding.ActivitySectionBinding;
import com.example.skuniv.fleamarket2.model.locatonModel.SectionModel;
import com.example.skuniv.fleamarket2.model.locatonModel.ShopModel;
import com.example.skuniv.fleamarket2.view.locationView.ShopSelectDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SectionCommand {

    SectionModel sectionModel;
    ShopSelectDialog shopSelectDialog;
    ActivitySectionBinding sectionBinding;
    ShopsViewModel shopsViewModel;
    ShopData shopData;
    List<ShopModel> shops;
    ShopMetaViewModel shopMetaViewModel;
    Gson gson = new Gson();

    public SectionCommand(SectionModel sectionModel, ActivitySectionBinding sectionBinding,ShopsViewModel shopsViewModel,ShopData shopData,ShopMetaViewModel shopMetaViewModel) {
        this.sectionModel = sectionModel;
        this.sectionBinding = sectionBinding;
        this.shopsViewModel = shopsViewModel;
        this.shopData = shopData;
        this.shopMetaViewModel = shopMetaViewModel;
    }

   /* public void selectDialog(View view) {
        //shopSelectDialog = new ShopSelectDialog();
        shopSelectDialog.show();
    }*/

    public void getShopList() {
        if (!(sectionModel.getSection().isEmpty()) && sectionModel.getSectionNum() >= 0) {
            Call<ShopData> res = NetRetrofit.getInstance().getService().getListRepos(sectionModel.getSection(),sectionModel.getSectionNum());
            Log.i("getShopList",""+res);
            res.enqueue(new Callback<ShopData>() {
                @Override
                public void onResponse(Call<ShopData> call, Response<ShopData> response) {
                    Log.i("Retrofit", response.toString());
                    if (response.body() != null) {
                        shopData = response.body();
                        Log.i("getShopList",""+gson.toJson(response.body()));
                        shopsViewModel.setShops(shopData.getItems());
                        shopMetaViewModel.count.set(shopData.getMeta().getShopCount());
                    }
                }
                @Override
                public void onFailure(Call<ShopData> call, Throwable t) {
                    Log.e("에러", t.getMessage());
                }
            });
        } else {
            Log.e("getShopList","getShopList error");
        }
    }

    public List<ShopModel> jsonPaser(String jsonObject){

        Gson gson = new Gson();
        shopData = gson.fromJson(jsonObject,  ShopData.class);
        shopsViewModel.setShops(shopData.getItems());
        System.out.println("====shop count" + shopData.getMeta().getShopCount());
        shopMetaViewModel.count.set(shopData.getMeta().getShopCount());
        return shops;
    }

    public void setSectionModel(SectionModel sectionModel) {
        this.sectionModel = sectionModel;
    }
}