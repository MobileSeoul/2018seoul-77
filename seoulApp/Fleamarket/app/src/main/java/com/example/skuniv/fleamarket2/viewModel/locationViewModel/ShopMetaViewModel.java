package com.example.skuniv.fleamarket2.viewModel.locationViewModel;

import android.databinding.ObservableField;

public class ShopMetaViewModel {
    public final ObservableField<Integer> count = new ObservableField<>();

    public ObservableField<Integer> getCount() {
        return count;
    }
}
