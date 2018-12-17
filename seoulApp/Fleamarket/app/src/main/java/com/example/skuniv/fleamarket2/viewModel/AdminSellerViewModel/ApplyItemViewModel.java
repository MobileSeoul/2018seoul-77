package com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel;

import android.databinding.ObservableField;

public class ApplyItemViewModel {
    public final ObservableField<String> id = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> contents = new ObservableField<>();
    public final ObservableField<Integer> role = new ObservableField<>();
    public final ObservableField<String> date = new ObservableField<>();

    public ApplyItemViewModel(String id, String name, String title, String contents, Integer role, String date) {
        this.id.set(id);
        this.name.set(name);
        this.title.set(title);
        this.contents.set(contents);
        this.role.set(role);
        this.date.set(date);
    }
}
