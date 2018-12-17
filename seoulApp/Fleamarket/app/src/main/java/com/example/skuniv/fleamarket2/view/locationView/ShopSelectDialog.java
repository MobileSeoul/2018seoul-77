package com.example.skuniv.fleamarket2.view.locationView;


import android.app.Dialog;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.adapter.SelectShopListAdapter;
import com.example.skuniv.fleamarket2.model.locatonModel.SectionModel;
import com.example.skuniv.fleamarket2.model.locatonModel.ShopData;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.SectionCommand;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.SelectDialogItemModel;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.SelectDialogItemsModel;
import com.example.skuniv.fleamarket2.databinding.ShopSelectDialogBinding;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.ShopMetaViewModel;

public class ShopSelectDialog extends Dialog {

    //private SectionModel sectionModel = new SectionModel();
    SectionModel sectionModel;
    SelectDialogItemsModel selectDialogItemsModel;
    private ShopSelectDialogBinding dialogBinding;
    SectionCommand sectionCommand;
    ShopMetaViewModel shopMetaViewModel;
    ShopSelectDialog shopSelectDialog;


    public ShopSelectDialog(Context context, SectionModel sectionModel, SectionCommand sectionCommand,ShopMetaViewModel shopMetaViewModel) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.sectionModel = sectionModel;
        this.sectionCommand =sectionCommand;
        this.shopMetaViewModel = shopMetaViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectDialogItemsModel = new SelectDialogItemsModel();

        /*// 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);*/

        shopSelectDialog = this;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(shopSelectDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Window window = shopSelectDialog.getWindow();
        window.setAttributes(lp);

        dialogBinding = (ShopSelectDialogBinding)
                DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.shop_select_dialog, null, false);
        setContentView(dialogBinding.getRoot());
        selectDialogItemsModel.setItems(shopMetaViewModel.getCount().get());
        //selectDialogItemsModel.items.add(new SelectDialogItemModel("10","20"));
        dialogBinding.setModel(selectDialogItemsModel);

        dialogBinding.listId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sectionModel.setSectionNum(i+1);
                System.out.println("sectionMode Number====" + sectionModel.getSectionNum());
                sectionCommand.setSectionModel(sectionModel);
                sectionCommand.getShopList();
                ShopSelectDialog.this.dismiss();
            }
        });
    }

    //listView adapter 생성
    @BindingAdapter("app:items")
    public static void setSelectItemList(ListView listView, ObservableArrayList<SelectDialogItemModel> items) {
        SelectShopListAdapter adapter;
        //adapter 없을 때 adapter 생성
        if (listView.getAdapter() == null) {
            adapter = new SelectShopListAdapter();
            listView.setAdapter(adapter);
        } else {
            // 있으면 getAdapter
            adapter = (SelectShopListAdapter) listView.getAdapter();
        }

        adapter.addAll(items);
    }
}
