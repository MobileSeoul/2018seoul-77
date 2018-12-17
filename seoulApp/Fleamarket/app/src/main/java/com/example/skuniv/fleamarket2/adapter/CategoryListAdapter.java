package com.example.skuniv.fleamarket2.adapter;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.skuniv.fleamarket2.databinding.CategoryItemBinding;
import com.bumptech.glide.Glide;
import com.example.skuniv.fleamarket2.view.categoryView.CategoryGoodsInfoDialog;
import com.example.skuniv.fleamarket2.viewModel.categoryViewmodel.CategoryCommand;
import com.example.skuniv.fleamarket2.viewModel.categoryViewmodel.CategoryMetaViewModel;
import com.example.skuniv.fleamarket2.viewModel.categoryViewmodel.CategoryShopViewModel;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryViewHolder>{
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG =0;
    private OnLoadMoreListener onLoadMoreListener;
    private LinearLayoutManager linearLayoutManager;

    private boolean isMoreLoading = false;
    private int visibleThreshold = 1;
    int firstVisibleItem, visibleItemCount, totalItemCount, lastVisibleItem;

    public ObservableArrayList<CategoryShopViewModel> shopsList;
    Context context;
    CategoryCommand categoryCommand;
    CategoryMetaViewModel categoryMetaViewModel;

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    public CategoryListAdapter(ObservableArrayList<CategoryShopViewModel> shopsList, Context context, CategoryCommand categoryCommand, OnLoadMoreListener onLoadMoreListener,
                               CategoryMetaViewModel categoryMetaViewModel){
        this.shopsList = shopsList;
        this.context = context;
        this.categoryCommand = categoryCommand;
        this.onLoadMoreListener = onLoadMoreListener;
        this.categoryMetaViewModel = categoryMetaViewModel;
//        System.out.println("==============="+shopsList.get(1));
    }

    public void setRecyclerView(RecyclerView mView){
        mView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Log.d("total", totalItemCount + "");
                Log.d("visible", visibleItemCount + "");

                Log.d("first", firstVisibleItem + "");
                Log.d("last", lastVisibleItem + "");
                if (totalItemCount < categoryMetaViewModel.getCount().get()) {
                    if (!isMoreLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                            System.out.println("onLoadMore()------");
                        }
                        isMoreLoading = true;
                    }
                }
            }
        });
    }

    // 현재 로딩중이면 프로그레스바 리턴 or 아니면 아이템 리턴
    /*@Override
    public int getItemViewType(int position) {
        return itemList.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }*/

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //만약 타입이 view_item이면 아이템 추가
        //if(viewType == VIEW_ITEM)
        CategoryItemBinding binding = CategoryItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.setCommand(categoryCommand);
        return new CategoryViewHolder(binding, context);
        //아니면 프로그레스바 아이템 추가
        //else{
    }

    public void setShopsList(ObservableArrayList<CategoryShopViewModel> shopsList) {
        this.shopsList = shopsList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, final int position) {
        CategoryShopViewModel shop = shopsList.get(position);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryGoodsInfoDialog categoryGoodsInfoDialog = new CategoryGoodsInfoDialog(context, shopsList.get(position));
                categoryGoodsInfoDialog.show();
            }
        });
        holder.bind(shop);
    }

    public void setMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading=isMoreLoading;
    }

    void setItem(ObservableArrayList<CategoryShopViewModel> goodsList) {
        if (goodsList == null) {
            return;
        }
        this.shopsList = goodsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return shopsList.size();
    }
}

class CategoryViewHolder extends RecyclerView.ViewHolder {
    CategoryItemBinding binding;
    Context context;

    //ViewHolder 생성
    public CategoryViewHolder(CategoryItemBinding binding, Context context) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = context;
    }

    void bind(CategoryShopViewModel shop) {
        Log.i("bind", "=======" + shop.goods.get().getImage());
        Glide.with(context).load(shop.goods.get().getImage()).override(300, 300).into(binding.imageId);
        binding.setShop(shop);
    }
}
