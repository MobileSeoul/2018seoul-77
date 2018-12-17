package com.example.skuniv.fleamarket2.view.sellerView;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.databinding.SellerApplyBinding;
import com.example.skuniv.fleamarket2.databinding.SellerGoodsDeleteBinding;
import com.example.skuniv.fleamarket2.model.jsonModel.SellerApplyJson;
import com.example.skuniv.fleamarket2.model.jsonModel.ShopGoodsJson;
import com.example.skuniv.fleamarket2.util.Util;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.SellerCommand;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.SellerGoodsViewModel;

/**
 * Created by c2619 on 2018-09-29.
 */

public class GoodsDeleteDialog extends Dialog {
    private SellerGoodsDeleteBinding sellerGoodsDeleteBinding;
    private SellerGoodsViewModel sellerGoodsViewModel;
    private Dialog dialog;
    private SellerCommand sellerCommand;

    public GoodsDeleteDialog(@NonNull Context context,SellerGoodsViewModel sellerGoodsViewModel,SellerCommand sellerCommand) {
        super(context);
        this.sellerGoodsViewModel = sellerGoodsViewModel;
        this.sellerCommand=sellerCommand;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = this;
        sellerGoodsDeleteBinding = (SellerGoodsDeleteBinding)
                DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.seller_goods_delete, null, false);
        setContentView(sellerGoodsDeleteBinding.getRoot());

        sellerGoodsDeleteBinding.sellerGoodsDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ShopGoodsJson shopGoodsJson = new ShopGoodsJson(sellerCommand.getUserModel().getShop(),sellerGoodsViewModel.name.get());
                sellerCommand.sellerGoodsDelete(shopGoodsJson);
                dialog.cancel();
            }
        });
        Util.settingDialogSize(this,500,500);
    }
}
