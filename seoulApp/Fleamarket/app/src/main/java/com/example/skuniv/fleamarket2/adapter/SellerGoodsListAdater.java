package com.example.skuniv.fleamarket2.adapter;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.skuniv.fleamarket2.databinding.SellerGoodsItemBinding;
import com.example.skuniv.fleamarket2.model.AdminSellerModel.UserModel;
import com.example.skuniv.fleamarket2.util.Util;
import com.example.skuniv.fleamarket2.view.sellerView.GoodsDeleteDialog;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.SellerCommand;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.SellerGoodsViewModel;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.GoodsViewModel;

public class SellerGoodsListAdater extends RecyclerView.Adapter<SellerGoodsViewHolder>{
    public ObservableArrayList<SellerGoodsViewModel> goodsList;
    Context context;
    private SellerCommand sellerCommand;

    public SellerGoodsListAdater(ObservableArrayList<SellerGoodsViewModel> goodsList, Context context,SellerCommand sellerCommand){
        this.goodsList = goodsList;
        this.context = context;
        this.sellerCommand=sellerCommand;
    }

    @Override
    public SellerGoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SellerGoodsItemBinding binding = SellerGoodsItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SellerGoodsViewHolder(binding, context);
    }

    @Override
    public void onBindViewHolder(SellerGoodsViewHolder holder, int position) {
        final SellerGoodsViewModel goods = goodsList.get(position);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(Util.gson.toJson(goods));
                GoodsDeleteDialog goodsDeleteDialog = new GoodsDeleteDialog(context,goods,sellerCommand);
                goodsDeleteDialog.show();
            }
        });
        holder.bind(goods);
    }

    public void setItem(ObservableArrayList<SellerGoodsViewModel> goodsList) {
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

class SellerGoodsViewHolder extends RecyclerView.ViewHolder{
    SellerGoodsItemBinding binding;
    Context context;
    //ViewHolder 생성
    public SellerGoodsViewHolder(SellerGoodsItemBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    void bind(SellerGoodsViewModel goods) {
        Log.i("bind","======="+goods.image.get());
        Glide.with(context).load(goods.image.get()).override(300,300).into(binding.imageId);
        binding.setGoods(goods);
        //binding.setVariable(BR.goods, goods);
    }
}