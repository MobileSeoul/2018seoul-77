package com.example.skuniv.fleamarket2.viewModel.locationViewModel;

import android.databinding.ObservableField;

public class SelectDialogItemModel {
    public final ObservableField<String> start = new ObservableField<>();
    public final ObservableField<String> end = new ObservableField<>();

    public SelectDialogItemModel(String start, String end) {
        this.start.set(start);
        this.end.set(end);
    }

    public ObservableField<String> getStart() {
        return start;
    }

    public ObservableField<String> getEnd() {
        return end;
    }
}
