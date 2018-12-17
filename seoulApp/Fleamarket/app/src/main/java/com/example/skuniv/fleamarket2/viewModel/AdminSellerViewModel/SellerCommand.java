package com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import com.example.skuniv.fleamarket2.model.AdminSellerModel.UserModel;
import com.example.skuniv.fleamarket2.model.jsonModel.ResponseJson;
import com.example.skuniv.fleamarket2.model.jsonModel.SellerApplyJson;
import com.example.skuniv.fleamarket2.model.jsonModel.ShopGoodsJson;
import com.example.skuniv.fleamarket2.model.locatonModel.Goods;
import com.example.skuniv.fleamarket2.model.locatonModel.ShopModel;
import com.example.skuniv.fleamarket2.retrofit.FileUtils;
import com.example.skuniv.fleamarket2.retrofit.NetRetrofit;
import com.example.skuniv.fleamarket2.view.sellerView.SellerGoodsList;
import com.example.skuniv.fleamarket2.view.sellerView.SellerGoodsUpdateDialog;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.ShopViewModel;
import com.google.gson.Gson;

import java.io.File;

import lombok.Getter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Getter
public class SellerCommand extends SellerGoodsList {

    Context context;
    UserModel userModel;
    SellerShopViewModel sellerShopViewModel;
    ShopModel shopModel;
    SellerGoodsList sellerGoodsListView;
    Gson gson = new Gson();
    SellerGoodsUpdateDialog sellerGoodsUpdateDialog;

    public void setSellerGoodsUpdateDialog(SellerGoodsUpdateDialog sellerGoodsUpdateDialog) {
        this.sellerGoodsUpdateDialog = sellerGoodsUpdateDialog;
    }

    public SellerCommand(Context context, UserModel userModel, SellerShopViewModel sellerShopViewModel, SellerGoodsList sellerGoodsListView) {
        this.context = context;
        this.userModel = userModel;
        this.sellerShopViewModel = sellerShopViewModel;
        this.sellerGoodsListView = sellerGoodsListView;
    }

    public SellerCommand(){

    }

    public void getShopModel() {
        if(userModel != null) {
            Call<ShopModel> res = NetRetrofit.getInstance().getService().getSellerGoodsRepos(userModel.getShop());
            Log.i("getShopList", "" + res);
            res.enqueue(new Callback<ShopModel>() {
                @Override
                public void onResponse(Call<ShopModel> call, Response<ShopModel> response) {
                    Log.i("Retrofit", response.toString());
                    if (response.body() != null) {
                        shopModel = response.body();
                        Log.i("getShopList", "" + gson.toJson(response.body()));
                        sellerShopViewModel.setSellerShopViewModel(shopModel);
                    }
                }
                @Override
                public void onFailure(Call<ShopModel> call, Throwable t) {
                    Log.e("에러", t.getMessage());
                }
            });
        }
    }

   public void addGoods(final SellerShopViewModel sellerShopViewModel, Goods goods, Uri fileUri){
       MultipartBody.Part body = null;
       if(fileUri == null) {

       } else {
           File file = FileUtils.getFile(context,fileUri);
           System.out.println("file=========" + file);
           System.out.println("file uri=========" + fileUri);
           RequestBody requestFile =
                   RequestBody.create(MediaType.parse(context.getContentResolver().getType(fileUri)),file );

           // MultipartBody.Part is used to send also the actual file name
           body =
                   MultipartBody.Part.createFormData("image", file.getName(), requestFile);
       }


       RequestBody location = RequestBody.create( MultipartBody.FORM, sellerShopViewModel.location.get());
       RequestBody shop = RequestBody.create( okhttp3.MultipartBody.FORM, sellerShopViewModel.shop.get());
       RequestBody name = RequestBody.create( okhttp3.MultipartBody.FORM, goods.getName());
       RequestBody price = RequestBody.create( okhttp3.MultipartBody.FORM, String.valueOf(goods.getPrice()));
       RequestBody quantity = RequestBody.create( okhttp3.MultipartBody.FORM, String.valueOf(goods.getQuantity()));
       RequestBody category = RequestBody.create( okhttp3.MultipartBody.FORM, gson.toJson(goods.getCategory()));

       if(sellerShopViewModel != null) {
           Call<ResponseJson> res = NetRetrofit.getInstance().getService().sellerinsertGoodsRepos(body, location,
                   shop,name ,price ,quantity , category);
           Log.i("getShopList", "" + res);
           res.enqueue(new Callback<ResponseJson>() {
               @Override
               public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                   Log.i("Retrofit", response.toString());
                   if (response.body() != null) {
                       ResponseJson responseJson = response.body();
                       Log.i("getShopList", "" + gson.toJson(response.body()));
                       if(responseJson.getResponse().equals("success")) {
                           Log.i("sellerApply result", "success");
                           sellerGoodsUpdateDialog.cancel();
                           sellerShopViewModel.goods.clear();
                           getShopModel();
                       }
                       else{
                           Log.i("sellerApply result", "fail");
                       }
                   }
               }
               @Override
               public void onFailure(Call<ResponseJson> call, Throwable t) {
                   Log.e("에러", t.getMessage());
               }
           });
       }
   }

   public void sellerGoodsDelete(ShopGoodsJson shopGoodsJson){
       if(shopGoodsJson != null) {
           Call<ResponseJson> res = NetRetrofit.getInstance().getService().sellerdeleteGoodsRepos(shopGoodsJson);
           Log.i("ResponseJson", "" + res);
           res.enqueue(new Callback<ResponseJson>() {
               @Override
               public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                   Log.i("Retrofit", response.toString());
                   if (response.body() != null) {
                       ResponseJson responseJson = response.body();
                       if(responseJson.getResponse().equals("success")) {
                           sellerShopViewModel.goods.clear();
                           getShopModel();
                       }
                       else{
                           Log.i("상품 삭제 실패", "fail");
                       }
                   }
               }
               @Override
               public void onFailure(Call<ResponseJson> call, Throwable t) {
                   Log.e("에러", t.getMessage());
               }
           });
       }
   }

   public void sellerApply(SellerApplyJson sellerApplyJson){
        if(sellerApplyJson != null) {
            Call<ResponseJson> res = NetRetrofit.getInstance().getService().sellerApplyRepos(sellerApplyJson);
            Log.i("getShopList", "" + res);
            res.enqueue(new Callback<ResponseJson>() {
                @Override
                public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                    Log.i("Retrofit", response.toString());
                    if (response.body() != null) {
                        ResponseJson responseJson = response.body();
                        Log.i("getShopList", "" + gson.toJson(response.body()));
                        if(responseJson.getResponse().equals("success")) {
                            Log.i("sellerApply result", "success");
                        }
                        else{
                            Log.i("sellerApply result", "fail");
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseJson> call, Throwable t) {
                    Log.e("에러", t.getMessage());
                }
            });
        }
   }
}

