package com.example.skuniv.fleamarket2.viewModel.categoryViewmodel;

import android.databinding.ObservableField;

public class CategoryMetaViewModel {
    public final ObservableField<Integer> count = new ObservableField<>();

    public ObservableField<Integer> getCount() {
        return count;
    }
}
