package com.example.skuniv.fleamarket2.view.sellerView;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.databinding.UserInfoDialogBinding;
import com.example.skuniv.fleamarket2.model.AdminSellerModel.UserModel;

public class UserInfoDialog extends Dialog {
    UserModel userModel;
    Context context;
    UserInfoDialogBinding userInfoDialogBinding;
    UserInfoDialog userInfoDialog;
    public UserInfoDialog(@NonNull Context context, UserModel userModel) {
        super(context);
        this.context = context;
        this.userModel = userModel;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userInfoDialogBinding = (UserInfoDialogBinding)
                DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.user_info_dialog, null, false);
        setContentView(userInfoDialogBinding.getRoot());

        userInfoDialog = this;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(userInfoDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Window window = userInfoDialog.getWindow();
        window.setAttributes(lp);

        userInfoDialogBinding.setUser(userModel);

        if(userModel.getRole() == 2){
            userInfoDialogBinding.shopId.setText(userModel.getShop());
        }
        else {
            userInfoDialogBinding.shopId.setText("배정된 장터가 없습니다.");
        }
    }


}
