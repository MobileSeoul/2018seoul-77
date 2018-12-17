package com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.skuniv.fleamarket2.model.AdminSellerModel.ApplyData;
import com.example.skuniv.fleamarket2.model.AdminSellerModel.ApplyListModel;
import com.example.skuniv.fleamarket2.model.AdminSellerModel.UserModel;
import com.example.skuniv.fleamarket2.model.jsonModel.ApplyOneJson;
import com.example.skuniv.fleamarket2.model.jsonModel.ResponseJson;
import com.example.skuniv.fleamarket2.retrofit.NetRetrofit;
import com.example.skuniv.fleamarket2.view.adminView.ApplyDialog;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCommand {
    Context context;
    UserModel userModel;
    ApplyListModel applyListModel;
    ApplyData applyData;
    ApplyItemsViewModel applyItemsViewModel;
    ApplyMetaViewModel applyMetaViewModel;
    ApplyDialog applyDialog;
    Gson gson = new Gson();

    public AdminCommand(Context context, UserModel userModel, ApplyListModel applyListModel,
                        ApplyItemsViewModel applyItemsViewModel, ApplyMetaViewModel applyMetaViewModel) {
        this.context = context;
        this.userModel = userModel;
        this.applyListModel = applyListModel;
        this.applyItemsViewModel = applyItemsViewModel;
        this.applyMetaViewModel = applyMetaViewModel;
    }

    public void getApply(){
           if (applyListModel.getPage() > 0) {
            Call<ApplyData> res = NetRetrofit.getInstance().getService().getApplyRepos(applyListModel.getPage());
            Log.i("getApplyList",""+res);
            res.enqueue(new Callback<ApplyData>() {
                @Override
                public void onResponse(Call<ApplyData> call, Response<ApplyData> response) {
                    Log.i("Retrofit", response.toString());
                    if (response.body() != null) {
                        applyData = new ApplyData();
                        applyData = response.body();
                        Log.i("getApplyList",""+gson.toJson(applyData));
                        applyItemsViewModel.applyList.clear();
                        applyItemsViewModel.setApplyList(applyData.getItems());
                        applyMetaViewModel.pageList.clear();
                        applyMetaViewModel.count.set(applyData.getMeta().getCount());
                        applyMetaViewModel.setPageList();
                        System.out.println(applyItemsViewModel);
                        System.out.println(applyItemsViewModel.getApplyList());
                    }
                }
                @Override
                public void onFailure(Call<ApplyData> call, Throwable t) {
                    Log.e("에러", t.getMessage());
                }
            });
        } else {
            Log.e("getApplyList","getApplyList error");
        }
    }

    public void applySendOne(String id, int role){
        final int tempRole = role;
        System.out.println("role ===== " +  role);
        ApplyOneJson applyOneJson = new ApplyOneJson(id, role);
        if (applyOneJson != null) {
            Call<ApplyData> res = NetRetrofit.getInstance().getService().applySendOneRepos(applyListModel.getPage(),applyOneJson);
            Log.i("getApplyList",""+res);
            res.enqueue(new Callback<ApplyData>() {
                @Override
                public void onResponse(Call<ApplyData> call, Response<ApplyData> response) {
                    Log.i("Retrofit", response.toString());
                    if (response.body() != null) {
                        applyData = new ApplyData();
                        applyData = response.body();
                        Log.i("getApplyList",""+gson.toJson(applyData));
                        applyItemsViewModel.applyList.clear();
                        applyItemsViewModel.setApplyList(applyData.getItems());
                        applyMetaViewModel.pageList.clear();
                        applyMetaViewModel.count.set(applyData.getMeta().getCount());
                        applyMetaViewModel.setPageList();
                        System.out.println(applyItemsViewModel);
                        System.out.println(applyItemsViewModel.getApplyList());
                        }
                }
                @Override
                public void onFailure(Call<ApplyData> call, Throwable t) {
                    Log.e("에러", t.getMessage());
                }
            });
        } else {
            Log.e("getApplyList","getApplyList error");
        }
    }

    public void sendRandom(){
        if (applyListModel.getPage() > 0) {
            Call<ApplyData> res = NetRetrofit.getInstance().getService().randomApplyRepos(applyListModel.getApplyCount(),applyListModel.getPage());
            Log.i("getApplyList",""+res);
            res.enqueue(new Callback<ApplyData>() {
                @Override
                public void onResponse(Call<ApplyData> call, Response<ApplyData> response) {
                    Log.i("Retrofit", response.toString());
                    if (response.body() != null) {
                        applyData = new ApplyData();
                        applyData = response.body();
                        Log.i("getApplyList",""+gson.toJson(applyData));
                        applyItemsViewModel.applyList.clear();
                        applyItemsViewModel.setApplyList(applyData.getItems());
                        applyMetaViewModel.pageList.clear();
                        applyMetaViewModel.count.set(applyData.getMeta().getCount());
                        applyMetaViewModel.setPageList();
                        System.out.println(applyItemsViewModel);
                        System.out.println(applyItemsViewModel.getApplyList());
                    }
                }
                @Override
                public void onFailure(Call<ApplyData> call, Throwable t) {
                    Log.e("에러", t.getMessage());
                }
            });
        } else {
            Log.e("getApplyList","getApplyList error");
        }
    }

    public void sendFirstCome(){
        if (applyListModel.getPage() > 0) {
            Call<ApplyData> res = NetRetrofit.getInstance().getService().firstcomeApplyRepos(applyListModel.getApplyCount(),applyListModel.getPage());
            Log.i("getApplyList",""+res);
            res.enqueue(new Callback<ApplyData>() {
                @Override
                public void onResponse(Call<ApplyData> call, Response<ApplyData> response) {
                    Log.i("Retrofit", response.toString());
                    if (response.body() != null) {
                        applyData = new ApplyData();
                        applyData = response.body();
                        Log.i("getApplyList",""+gson.toJson(applyData));
                        applyItemsViewModel.applyList.clear();
                        applyItemsViewModel.setApplyList(applyData.getItems());
                        applyMetaViewModel.pageList.clear();
                        applyMetaViewModel.count.set(applyData.getMeta().getCount());
                        applyMetaViewModel.setPageList();
                        System.out.println(applyItemsViewModel);
                        System.out.println(applyItemsViewModel.getApplyList());
                    }
                }
                @Override
                public void onFailure(Call<ApplyData> call, Throwable t) {
                    Log.e("에러", t.getMessage());
                }
            });
        } else {
            Log.e("getApplyList","getApplyList error");
        }
    }

    public void sendAdmission(){
        if (applyListModel.getPage() > 0) {
            Call<ResponseJson> res = NetRetrofit.getInstance().getService().sendAdmissionRepos();
            Log.i("getApplyList",""+res);
            res.enqueue(new Callback<ResponseJson>() {
                @Override
                public void onResponse(Call<ResponseJson> call, Response<ResponseJson> response) {
                    Log.i("Retrofit", response.toString());
                    if (response.body() != null) {
                        ResponseJson responseJson = response.body();
                        //applyData = response.body();
                        Log.i("getApplyList",""+gson.toJson(responseJson));
                        if(responseJson.getResponse().equals("success")){
                            Toast.makeText(context,"success",Toast.LENGTH_SHORT).show();
                            getApply();
                        }
                        else{
                            Toast.makeText(context,"fail",Toast.LENGTH_SHORT).show();
                        }
                       /* applyItemsViewModel.applyList.clear();
                        applyItemsViewModel.setApplyList(applyData.getItems());
                        applyMetaViewModel.pageList.clear();
                        applyMetaViewModel.count.set(applyData.getMeta().getCount());
                        applyMetaViewModel.setPageList();
                        System.out.println(applyItemsViewModel);
                        System.out.println(applyItemsViewModel.getApplyList());*/
                    }
                }
                @Override
                public void onFailure(Call<ResponseJson> call, Throwable t) {
                    Log.e("에러", t.getMessage());
                }
            });
        } else {
            Log.e("getApplyList","getApplyList error");
        }
    }





    public void setApplyDialog(ApplyDialog applyDialog) {
        this.applyDialog = applyDialog;
    }

    public void jsonPaser(String jsonObject){
        Gson gson = new Gson();
        applyData = new ApplyData();
        applyData = gson.fromJson(jsonObject,  ApplyData.class);

        System.out.println("getApplyList======"+gson.toJson(applyData));
        applyItemsViewModel.setApplyList(applyData.getItems());
        applyMetaViewModel.count.set(applyData.getMeta().getCount());
        System.out.println("jsonPaser===========");
    }
}
