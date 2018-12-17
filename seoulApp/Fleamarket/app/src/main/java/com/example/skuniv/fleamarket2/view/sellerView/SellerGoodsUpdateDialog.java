package com.example.skuniv.fleamarket2.view.sellerView;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.skuniv.fleamarket2.databinding.SellerGoodsUpdateDialogBinding;
import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.model.AdminSellerModel.UserModel;
import com.example.skuniv.fleamarket2.model.Category;
import com.example.skuniv.fleamarket2.model.locatonModel.Goods;
import com.example.skuniv.fleamarket2.util.Util;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.SellerCommand;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.SellerShopViewModel;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.GoodsViewModel;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.ShopViewModel;

import java.util.ArrayList;

public class SellerGoodsUpdateDialog extends Dialog {
    SellerGoodsUpdateDialogBinding sellerGoodsUpdateDialogBinding;
    Context context;
    GoodsViewModel goodsViewModel;
    SellerShopViewModel sellerShopViewModel;
    UserModel userModel;
    ArrayAdapter mainSpinner, middleSpinner;
    final String[] mainC = {"옷", "디지털", "잡화", "악세서리", "도서", "기타"};
    String mainCategory, middleCategory;
    SellerGoodsList sellerGoodsListView;
    SellerCommand sellerCommand;
    Category category;
    Uri fileUri;
    boolean imagebool;

    public SellerGoodsUpdateDialog(@NonNull Context context, UserModel userModel, SellerShopViewModel sellerShopViewModel,
                                   SellerGoodsList sellerGoodsListView, SellerCommand sellerCommand) {
        super(context);
        this.context = context;
        this.userModel = userModel;
        this.sellerShopViewModel = sellerShopViewModel;
        this.sellerGoodsListView = sellerGoodsListView;
        this.sellerCommand = sellerCommand;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SellerGoodsUpdateDialog sellerGoodsUpdateDialog = this;
        sellerGoodsUpdateDialogBinding = (SellerGoodsUpdateDialogBinding)
                DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.seller_goods_update_dialog, null, false);
        setContentView(sellerGoodsUpdateDialogBinding.getRoot());
        sellerCommand.setSellerGoodsUpdateDialog(this);
        category = new Category();
        //getImage = new GetImage();
        //getImage.setSellerGoodsUpdateDialogBinding(sellerGoodsUpdateDialogBinding);

        // 스피너 셋팅
        mainSpinner = new ArrayAdapter(
                context,android.R.layout.simple_spinner_item, mainC);

        mainSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sellerGoodsUpdateDialogBinding.mainSpinnerId.setAdapter(mainSpinner);

        sellerGoodsUpdateDialogBinding.mainSpinnerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mainCategory = adapterView.getItemAtPosition(i).toString();
                setMiddleSpinner();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {      }
        });

        sellerGoodsUpdateDialogBinding.middleSpinnerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                middleCategory = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {      }
        });

        sellerGoodsUpdateDialogBinding.cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellerGoodsUpdateDialog.dismiss();
            }
        });
        sellerGoodsUpdateDialogBinding.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goods goods = new Goods(sellerGoodsUpdateDialogBinding.nameText.getText().toString(), Integer.valueOf(sellerGoodsUpdateDialogBinding.priceText.getText().toString()),
                        Integer.valueOf(sellerGoodsUpdateDialogBinding.quantityText.getText().toString()));
                ArrayList<Integer> categoryList = new ArrayList<Integer>();
                categoryList.add(category.getCategoryNum(mainCategory));
                categoryList.add(category.getCategoryNum(middleCategory));
                goods.setCategory(categoryList);
                fileUri = sellerGoodsListView.getImageUri();
                sellerCommand.addGoods(sellerShopViewModel,goods,fileUri);
                fileUri = null;
                sellerGoodsListView.setImageUri(null);
            }
        });

        sellerGoodsUpdateDialogBinding.imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellerGoodsListView.getImage();
                imagebool = true;
            }
        });
        System.out.println("package name ====="+context.getPackageName());
        //sellerGoodsUpdateDialogBinding.imageView.setImageURI(Uri.parse("android.resource://"+context.getPackageName()+"/drawable/default_icon.png"));
        Util.settingDialogSize(this,1000,1700);
    }

    private void setMiddleSpinner(){
        middleSpinner = new ArrayAdapter(
                context,android.R.layout.simple_spinner_item, getList(mainCategory));
        middleSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sellerGoodsUpdateDialogBinding.middleSpinnerId.setAdapter(middleSpinner);
    }

    private String[] getList(String mainCategory){
        if(mainCategory.equals("옷")){
            return context.getResources().getStringArray(R.array.cloth);
        }else if(mainCategory.equals("디지털")){
            return context.getResources().getStringArray(R.array.digital);
        }else if(mainCategory.equals("잡화")){
            return context.getResources().getStringArray(R.array.fancy);
        }else if(mainCategory.equals("악세서리")){
            return context.getResources().getStringArray(R.array.acc);
        }else if(mainCategory.equals("도서")){
            return context.getResources().getStringArray(R.array.book);
        }else if(mainCategory.equals("기타")){
            return context.getResources().getStringArray(R.array.etc);
        }
        return null;
    }

    public void setGoodsViewModel(GoodsViewModel goodsViewModel) {
        this.goodsViewModel = goodsViewModel;
        sellerGoodsUpdateDialogBinding.nameText.setText(goodsViewModel.name.get());
        sellerGoodsUpdateDialogBinding.priceText.setText(""+goodsViewModel.price.get());
        sellerGoodsUpdateDialogBinding.quantityText.setText(""+goodsViewModel.quantity.get());
    }
}
