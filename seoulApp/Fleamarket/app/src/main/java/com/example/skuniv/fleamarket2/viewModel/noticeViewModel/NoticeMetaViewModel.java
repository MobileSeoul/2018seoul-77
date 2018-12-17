package com.example.skuniv.fleamarket2.viewModel.noticeViewModel;

import android.databinding.ObservableField;

public class NoticeMetaViewModel {
    public final ObservableField<Integer> count = new ObservableField<>();

    public ObservableField<Integer> getCount() {
        return count;
    }
}
