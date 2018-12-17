package com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel;

import android.databinding.ObservableArrayList;

import com.example.skuniv.fleamarket2.model.AdminSellerModel.ApplyModel;

import java.util.List;

public class ApplyItemsViewModel {
    public final ObservableArrayList<ApplyItemViewModel> applyList = new ObservableArrayList<>();

    public void setApplyList(List<ApplyModel> applyList){
        for(int i =0; i<applyList.size();i++){
            this.applyList.add(new ApplyItemViewModel(applyList.get(i).getId(),applyList.get(i).getName(),applyList.get(i).getTitle(),
                    applyList.get(i).getContents(),
                    applyList.get(i).getRole(),applyList.get(i).getDate()));
        }

    }

    public ObservableArrayList<ApplyItemViewModel> getApplyList() {
        return applyList;
    }
}
