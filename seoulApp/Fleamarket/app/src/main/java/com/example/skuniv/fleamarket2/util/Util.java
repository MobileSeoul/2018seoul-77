package com.example.skuniv.fleamarket2.util;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import com.example.skuniv.fleamarket2.view.sellerView.SellerGoodsUpdateDialog;
import com.google.gson.Gson;

/**
 * Created by c2619 on 2018-09-29.
 */

public class Util {
    public static Gson gson = new Gson();
    public static void settingDialogSize(final Dialog dialog,final int width,final int height) {
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = width;
        lp.height = height;
        dialog.getWindow().setAttributes(lp);
    }
}
