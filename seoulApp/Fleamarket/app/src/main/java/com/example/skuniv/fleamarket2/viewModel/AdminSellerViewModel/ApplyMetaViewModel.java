package com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import java.util.ArrayList;

public class ApplyMetaViewModel {
    public final ObservableField<Integer> count = new ObservableField<>();
    public final ObservableArrayList<Integer> pageList = new ObservableArrayList<>();

    public ObservableField<Integer> getCount() {
        return count;
    }

    public ObservableArrayList<Integer> getPageList() {
        return pageList;
    }

    public void setPageList(){
        int pageCount;
        pageCount = count.get() / 10;
        if(count.get() % 10 != 0)
            pageCount ++;

        for(int i = 1; i <= pageCount; i++)
            pageList.add(i);

        System.out.println("pageList size ======" + pageList.size());
    }
}
