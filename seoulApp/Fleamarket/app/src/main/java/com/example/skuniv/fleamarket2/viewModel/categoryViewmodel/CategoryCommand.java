package com.example.skuniv.fleamarket2.viewModel.categoryViewmodel;

import android.content.Context;
import android.util.Log;

import com.example.skuniv.fleamarket2.model.categoryModel.CategoryData;
import com.example.skuniv.fleamarket2.retrofit.NetRetrofit;
import com.example.skuniv.fleamarket2.adapter.CategoryListAdapter;
import com.example.skuniv.fleamarket2.databinding.ActivityCategoryListBinding;
import com.example.skuniv.fleamarket2.databinding.CategoryItemBinding;
import com.example.skuniv.fleamarket2.model.categoryModel.CategoryModel;
import com.example.skuniv.fleamarket2.model.categoryModel.CategoryShopModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryCommand {

    Context context;
    ActivityCategoryListBinding categoryListBinding;
    CategoryModel categoryModel;

    List<CategoryShopModel> categoryShopList;
    CategoryShopsViewModel categoryShopsViewModel;
    Gson gson = new Gson();
    //CategoryGoodsInfoDialog dialog;
    CategoryCommand categoryCommand;
    CategoryData categoryData;
    CategoryMetaViewModel categoryMetaViewModel;


    public CategoryCommand(Context context, ActivityCategoryListBinding categoryListBinding, CategoryModel categoryModel,
                           CategoryShopsViewModel categoryShopsViewModel,CategoryData categoryData, CategoryMetaViewModel categoryMetaViewModel){
        this.context = context;
        this.categoryListBinding = categoryListBinding;
        this.categoryModel = categoryModel;
        this.categoryShopsViewModel = categoryShopsViewModel;
        categoryCommand = this;
        this.categoryData = categoryData;
        this.categoryMetaViewModel = categoryMetaViewModel;
    }

    public void getGoodsList(){
        if (categoryModel.categoryList.size() >= 1 && categoryModel.getPageNum() >= 0) {
            Call<CategoryData> res = NetRetrofit.getInstance().getService().getGoodsRepos(categoryModel.categoryLastValue(),categoryModel.getPageNum());
            Log.i("getGoodsList",""+res);
            res.enqueue(new Callback<CategoryData>() {
                @Override
                public void onResponse(Call<CategoryData> call, Response<CategoryData> response) {
                    Log.i("Retrofit", response.toString());
                    if (response.body() != null) {
                        categoryData = response.body();
                        Log.i("getCategoryData",""+gson.toJson(categoryData));
                        categoryShopsViewModel.setCategoryShopsViewModel(categoryData.getItems());
                        System.out.println(categoryShopsViewModel);
                        System.out.println(categoryShopsViewModel.getShops());
                        categoryMetaViewModel.count.set(categoryData.getMeta().getCount());
                    }
                }
                @Override
                public void onFailure(Call<CategoryData> call, Throwable t) {
                    Log.e("에러", t.getMessage());
                }
            });
        } else {
            Log.e("getShopList","getShopList error");
        }
    }

    /*public void mapClickListener(CategoryShopViewModel shop){
        dialog = new ShopMapDialog(context,shop);
        System.out.println("--=---------mapmap");
        dialog.show();
    }*/

    public void jsonPaser(String jsonObject){

        Gson gson = new Gson();
        categoryData = gson.fromJson(jsonObject,  CategoryData.class);
        categoryShopsViewModel.setCategoryShopsViewModel(categoryData.getItems());
        categoryMetaViewModel.count.set(categoryData.getMeta().getCount());

        //List<CategoryShopModel> categoryShops = new ArrayList<>(Arrays.asList(shopList));
        //categoryShopsViewModel.setCategoryShopsViewModel(categoryShops);
        // shops = shopData.getShops();
        System.out.println("jsonPaser===========");
    }

}
