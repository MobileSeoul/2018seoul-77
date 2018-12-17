package com.example.skuniv.fleamarket2.view.sellerView;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.databinding.SellerApplyBinding;
import com.example.skuniv.fleamarket2.model.AdminSellerModel.UserModel;
import com.example.skuniv.fleamarket2.model.jsonModel.SellerApplyJson;
import com.example.skuniv.fleamarket2.util.Util;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.SellerCommand;
import com.google.gson.Gson;

public class SellerApplyDialog extends Dialog{
    UserModel userModel;
    SellerApplyBinding sellerApplyBinding;
    SellerCommand sellerCommand;
    SellerApplyDialog sellerApplyDialog;
    public SellerApplyDialog(@NonNull Context context, UserModel userModel) {
        super(context);
        this.userModel = userModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("----------------------------------------------------- SellerApplyDialog Log!!!!!");
        sellerApplyBinding = (SellerApplyBinding)
                DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.seller_apply, null, false);
        setContentView(sellerApplyBinding.getRoot());

        sellerApplyBinding.idId.setText(userModel.getId());

        sellerApplyBinding.nameId.setText(userModel.getName());
        sellerCommand = new SellerCommand();
        sellerApplyDialog = this;

        sellerApplyBinding.applyId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sellerApplyBinding.titleId.getText().toString() != null && sellerApplyBinding.contentId.getText().toString() != null){
                    SellerApplyJson sellerApplyJson = new SellerApplyJson(userModel.getId(),userModel.getName(),sellerApplyBinding.titleId.getText().toString(),sellerApplyBinding.contentId.getText().toString());
                    sellerCommand.sellerApply(sellerApplyJson);
                    sellerApplyDialog.cancel();
                }
            }
        });
        Util.settingDialogSize(this,1000,1700);
    }
}
