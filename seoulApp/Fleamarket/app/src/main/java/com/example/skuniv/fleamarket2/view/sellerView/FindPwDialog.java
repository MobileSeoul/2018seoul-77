package com.example.skuniv.fleamarket2.view.sellerView;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import com.example.skuniv.fleamarket2.databinding.FindPwBinding;

import com.example.skuniv.fleamarket2.R;

public class FindPwDialog extends Dialog {
    FindPwDialog findPwDialog;
    FindPwBinding findPwBinding;
    Context context;

    public FindPwDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findPwBinding = (FindPwBinding)
                DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.find_pw, null, false);
        setContentView(findPwBinding.getRoot());


        findPwDialog = this;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(findPwDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Window window = findPwDialog.getWindow();
        window.setAttributes(lp);
    }
}
