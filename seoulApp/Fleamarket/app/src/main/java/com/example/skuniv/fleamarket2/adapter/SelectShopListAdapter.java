package com.example.skuniv.fleamarket2.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.databinding.ShopSelectDialogItemBinding;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.SelectDialogItemModel;

public class SelectShopListAdapter extends BaseAdapter{

    private ObservableArrayList<SelectDialogItemModel> items = new ObservableArrayList<>();

    public void addAll(ObservableArrayList<SelectDialogItemModel> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public SelectDialogItemModel getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ShopSelectDialogItemBinding binding;

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            binding = DataBindingUtil.inflate(inflater, R.layout.shop_select_dialog_item, viewGroup, false);
            view = binding.getRoot();
            view.setTag(binding);
        } else {
            binding = (ShopSelectDialogItemBinding) view.getTag();
        }

        binding.setSelect(items.get(i));

        return view;
    }
}
