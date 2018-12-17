package com.example.skuniv.fleamarket2.viewModel.locationViewModel;

import android.databinding.ObservableArrayList;

public class SelectDialogItemsModel {
    public final ObservableArrayList<SelectDialogItemModel> items = new ObservableArrayList<>();

    public void setItems(int count){
        int i;
        for(i =1; i<count-10; i += 10){
            items.add(new SelectDialogItemModel(""+i,""+(i+9)));
        }
        if(i%10 != 0)
            items.add(new SelectDialogItemModel(""+i,""+(count)));
    }
}
