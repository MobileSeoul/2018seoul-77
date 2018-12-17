package com.example.skuniv.fleamarket2.view.locationView;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.databinding.MapBinding;
import com.example.skuniv.fleamarket2.databinding.ShopSelectDialogBinding;

public class MapDialog extends Dialog {
    MapBinding mapBinding;
    public MapDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapBinding = (MapBinding)
                DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.map, null, false);
        setContentView(mapBinding.getRoot());
    }
}
