package com.example.skuniv.fleamarket2.viewModel.locationViewModel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import java.util.List;

public class GoodsViewModel {

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<Integer> price = new ObservableField<>();
    public final ObservableField<Integer> quantity = new ObservableField<>();
    public final ObservableArrayList<Integer> category = new ObservableArrayList<>();
    public final ObservableField<String> image = new ObservableField<>();

    public GoodsViewModel(String name, Integer price, Integer quantity, List<Integer> category, String image) {
        this.name.set(name);
        this.price.set(price);
        this.quantity.set(quantity);
        this.category.addAll(category);
        this.image.set(image);
    }
}