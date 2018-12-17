package com.example.skuniv.fleamarket2.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.databinding.ShopItemBinding;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.ShopViewModel;

public class ShopListAdapter extends BaseAdapter{

    private ObservableArrayList<ShopViewModel> shops = new ObservableArrayList<>();

    public void addAll(ObservableArrayList<ShopViewModel> shops){
        this.shops = shops;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return shops.size();
    }

    @Override
    public ShopViewModel getItem(int i) {
        return shops.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ShopItemBinding binding;

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            binding = DataBindingUtil.inflate(inflater, R.layout.shop_item, viewGroup, false);
            view = binding.getRoot();
            view.setTag(binding);
        } else {
            binding = (ShopItemBinding) view.getTag();
        }

        binding.setShopItem(shops.get(i));

        return view;
    }
}
