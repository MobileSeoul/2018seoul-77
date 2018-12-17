package com.example.skuniv.fleamarket2.adapter;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.skuniv.fleamarket2.databinding.GoodsItemBinding;
import com.example.skuniv.fleamarket2.model.locatonModel.Goods;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.GoodsViewModel;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsViewHolder>{

    public ObservableArrayList<GoodsViewModel> goodsList;
    Context context;

    public GoodsListAdapter(ObservableArrayList<GoodsViewModel> goodsList, Context context){
        this.goodsList = goodsList;
        this.context = context;
    }

    @Override
    public GoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GoodsItemBinding binding = GoodsItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GoodsViewHolder(binding, context);
    }

    @Override
    public void onBindViewHolder(GoodsViewHolder holder, int position) {
        GoodsViewModel goods = goodsList.get(position);
        holder.bind(goods);
    }

    public void setItem(ObservableArrayList<GoodsViewModel> goodsList) {
        if (goodsList == null) {
            return;
        }
        this.goodsList = goodsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }
}

class GoodsViewHolder extends RecyclerView.ViewHolder{
    GoodsItemBinding binding;
    Context context;
    //ViewHolder 생성
    public GoodsViewHolder(GoodsItemBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    void bind(GoodsViewModel goods) {
        Log.i("bind","======="+goods.image.get());
        Glide.with(context).load(goods.image.get()).override(300,300).into(binding.imageId);
        binding.setGoods(goods);
        //binding.setVariable(BR.goods, goods);
    }
}

