package com.example.skuniv.fleamarket2.view.locationView;

import android.app.Dialog;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.adapter.GoodsListAdapter;
import com.example.skuniv.fleamarket2.databinding.GoodsRecyclerBinding;
import com.example.skuniv.fleamarket2.model.locatonModel.Goods;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.GoodsViewModel;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.ShopViewModel;

public class GoodsRecyclerDialog extends Dialog{

    public ShopViewModel shop;
    private GoodsListAdapter mAdapter;
    public static Context context;
    GoodsRecyclerDialog goodsRecyclerDialog;

    public GoodsRecyclerDialog(Context context, ShopViewModel shop) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.shop = shop;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        goodsRecyclerDialog = this;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(goodsRecyclerDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Window window = goodsRecyclerDialog.getWindow();
        window.setAttributes(lp);

        // goods_recycler binding 객체 생성, layout 연결
        GoodsRecyclerBinding binding = (GoodsRecyclerBinding)
                DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.goods_recycler, null, false);
        setContentView(binding.getRoot());

        Log.i("Goods dialog",""+shop.goods.size());

        // 레이아웃 매니져, 어댑터 생성 후 recycler에 set 함
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        mAdapter = new GoodsListAdapter(shop.goods, getContext());

        binding.recyclerId.setLayoutManager(llm);
        binding.recyclerId.setAdapter(mAdapter);
        binding.setShop(shop);
    }

    @BindingAdapter("app:items")
    public static void bindItemList(RecyclerView recyclerView, ObservableArrayList<GoodsViewModel> goods) {
        GoodsListAdapter adapter; //= (GoodsListAdapter) recyclerView.getAdapter();
        if(recyclerView.getAdapter() == null){
            adapter = new GoodsListAdapter(goods, context);
            recyclerView.setAdapter(adapter);
        }
        else {
            // 있으면 getAdapter
            System.out.println("seller goods adapter=============");
            adapter = (GoodsListAdapter) recyclerView.getAdapter();
        }
    }
}
