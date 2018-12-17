package com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.skuniv.fleamarket2.databinding.SignInBinding;
import com.example.skuniv.fleamarket2.databinding.SignUpBinding;
import com.example.skuniv.fleamarket2.databinding.FindIdBinding;
import com.example.skuniv.fleamarket2.model.AdminSellerModel.UserModel;
import com.example.skuniv.fleamarket2.model.jsonModel.FindIdJson;
import com.example.skuniv.fleamarket2.model.jsonModel.SignInJson;
import com.example.skuniv.fleamarket2.model.jsonModel.SignUpJson;
import com.example.skuniv.fleamarket2.retrofit.NetRetrofit;
import com.example.skuniv.fleamarket2.view.MainActivity;
import com.example.skuniv.fleamarket2.view.adminView.CurrentApplyView;
import com.example.skuniv.fleamarket2.view.sellerView.FindIdDialog;
import com.example.skuniv.fleamarket2.view.sellerView.SellerApplyDialog;
import com.example.skuniv.fleamarket2.view.sellerView.SellerGoodsList;
import com.example.skuniv.fleamarket2.view.sellerView.SignInDialog;
import com.example.skuniv.fleamarket2.view.sellerView.SignUpDialog;
import com.example.skuniv.fleamarket2.view.sellerView.UserInfoDialog;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MainCommand {

    UserModel userModel;
    SignInBinding signInBinding;
    SignUpBinding signUpBinding;
    FindIdBinding findIdBinding;
    MainActivity mainActivity;
    Context context;
    Gson gson = new Gson();
    SignInDialog signInDialog;
    SignUpDialog signUpDialog;
    FindIdDialog findIdDialog;
    //자동 로그인 SharedPreferences 객체 생성
    SharedPreferences loginSetting;
    SharedPreferences.Editor editor;




    public MainCommand(Context context, UserModel userModel, MainActivity mainActivity) {
        this.context = context;
        this.userModel = userModel;
        this.mainActivity = mainActivity;
    }

    public void setFindIdBinding(FindIdBinding findIdBinding) {
        this.findIdBinding = findIdBinding;
    }

    public void setFindIdDialog(FindIdDialog findIdDialog) {
        this.findIdDialog = findIdDialog;
    }

    public void setSignInDialog(SignInDialog signInDialog) {
        this.signInDialog = signInDialog;
    }

    public void setSignUpDialog(SignUpDialog signUpDialog) {
        this.signUpDialog = signUpDialog;
    }

    public void setSignInBinding(SignInBinding signInBinding) {
        this.signInBinding = signInBinding;
    }

    public void setSignUpBinding(SignUpBinding signUpBinding) {
        this.signUpBinding = signUpBinding;
    }


    public void signIn(SignInJson signInJson) {
        if (signInJson != null) {
            Call<UserModel> res = NetRetrofit.getInstance().getService().getSignInRepos(signInJson);
            Log.i("signIn", "" + res);
            res.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                    Log.i("Retrofit", response.toString());
                    if (response.body() != null) {
                        userModel = response.body();
                        Log.i("sign in", "" + gson.toJson(userModel));
                        if (userModel.getResponse().equals("success")) {
                            loginSetting = context.getSharedPreferences("loginSetting", MODE_PRIVATE);
                            editor = loginSetting.edit();
                            editor.putString("id",userModel.getId());
                            editor.putString("name",userModel.getName());
                            editor.putString("shop",userModel.getShop());
                            editor.putInt("role",userModel.getRole());
                            editor.commit();
                            signInDialog.cancel();
                            mainActivity.result.closeDrawer();
                            singInSuccess();
                        } else {
                            Toast.makeText(signInBinding.getRoot().getContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserModel> call, Throwable t) {
                    Log.e("에러", t.getMessage());
                }
            });
        } else {
            Log.e("getShopList", "getShopList error");
        }
    }

    public void signUp(final SignUpJson signUpJson) {
        Call<UserModel> res = NetRetrofit.getInstance().getService().getSignUpRepos(signUpJson);
        Log.i("signUp", "" + res);
        res.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.i("Retrofit", response.toString());
                if (response.body() != null) {
                    userModel = response.body();
                    Log.i("sign up", "" + gson.toJson(userModel));
                    if (userModel.getResponse().equals("success")) {
                        signUpDialog.cancel();
                        signInDialog.show();
                        signInBinding.idText.setText(signUpJson.getId());
                        signInBinding.pwText.setText(signUpJson.getPw());
                    } else {
                        Toast.makeText(signInBinding.getRoot().getContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("에러", t.getMessage());
            }
        });
    }

    public void findId(FindIdJson findIdJson){
        Call<UserModel> res = NetRetrofit.getInstance().getService().findIdRepos(findIdJson);
        Log.i("find Id", "" + res);
        res.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                Log.i("Retrofit", response.toString());
                if (response.body() != null) {
                    userModel = response.body();
                    Log.i("find id", "" + gson.toJson(userModel));
                    if (userModel.getResponse().equals("success")) {
                        findIdBinding.findText.setText("아이디는 " + userModel.getId() + " 입니다.");
                    } else {
                        findIdBinding.findText.setText("아이디 찾기 실패");
                    }
                }
            }
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("에러", t.getMessage());
            }
        });
    }

    public void autoLogin(){
        loginSetting = context.getSharedPreferences("loginSetting", MODE_PRIVATE);
        userModel.setId(loginSetting.getString("id",""));
        userModel.setName(loginSetting.getString("id",""));
        userModel.setShop(loginSetting.getString("shop",""));
        userModel.setRole(loginSetting.getInt("role",1));

        System.out.println("user shop========" +userModel.getShop());

        singInSuccess();
    }

    public void signOut(){
        loginSetting = context.getSharedPreferences("loginSetting", MODE_PRIVATE);
        editor = loginSetting.edit();

        editor.clear();
        editor.commit();

        mainActivity.result.removeAllItems();
        mainActivity.result.addItem(mainActivity.signInItem);
        mainActivity.result.closeDrawer();

        Toast.makeText(mainActivity,"로그아웃", Toast.LENGTH_SHORT).show();
    }

    public void moveCurrentApply(){
        Intent intent = new Intent(mainActivity, CurrentApplyView.class);
        intent.putExtra("userModel", userModel);
        mainActivity.startActivity(intent);
        mainActivity.result.closeDrawer();
    }

    public void moveGoodsList(){
        Intent intent = new Intent(mainActivity, SellerGoodsList.class);
        intent.putExtra("user",userModel);
        mainActivity.startActivity(intent);
        mainActivity.result.closeDrawer();
    }

    public void sellerApply(){
        SellerApplyDialog sellerApplyDialog = new SellerApplyDialog(context, userModel);
        sellerApplyDialog.show();
        mainActivity.result.closeDrawer();
    }

    public void info(){
        UserInfoDialog userInfoDialog = new UserInfoDialog(context, userModel);
        userInfoDialog.show();
        mainActivity.result.closeDrawer();
    }


    public void signInTest(){
        userModel.setId("test");
        userModel.setShop("a01");
        userModel.setRole(2);
        userModel.setResponse("success");

        loginSetting = context.getSharedPreferences("loginSetting", MODE_PRIVATE);
        editor = loginSetting.edit();
        editor.putString("id",userModel.getId());
        editor.putString("shop",userModel.getShop());
        editor.putInt("role",userModel.getRole());
        editor.commit();

        signInDialog.dismiss();
        mainActivity.result.closeDrawer();
        singInSuccess();
    }

    public void signUptest(){
        signUpDialog.dismiss();
        signInDialog.show();
    }

    public void findIdTest(){
        userModel.setResponse("success");
        userModel.setId("kim");
        if (userModel.getResponse().equals("success")) {
            findIdBinding.findText.setText("아이디는 " + userModel.getId() + " 입니다.");
        } else {
            findIdBinding.findText.setText("아이디 찾기 실패");
        }
    }


    public void singInSuccess() {
        mainActivity.result.removeAllItems();
        mainActivity.result.addItem(mainActivity.info);
        if (userModel.getRole() == 0) { // 관리자 로그인
            mainActivity.result.addItem(mainActivity.currentApplyItem);
            Toast.makeText(mainActivity,"관리자 로그인", Toast.LENGTH_SHORT).show();
        }
        else if (userModel.getRole() == 1) { // 신청서 작성 안한 판매자
            mainActivity.result.addItem(mainActivity.applyItem);
            Toast.makeText(mainActivity,"판매자 로그인", Toast.LENGTH_SHORT).show();
        }
        else if (userModel.getRole() == 2) { // 승인된 판매자
            mainActivity.result.addItem(mainActivity.goodsListItem);
            Toast.makeText(mainActivity,"판매자 로그인", Toast.LENGTH_SHORT).show();
        }
        else if (userModel.getRole() == 3) { // 승인 안된 판매자
            Toast.makeText(mainActivity,"판매자 로그인", Toast.LENGTH_SHORT).show();
        }
        mainActivity.result.addItem(mainActivity.signout);
    }
}
