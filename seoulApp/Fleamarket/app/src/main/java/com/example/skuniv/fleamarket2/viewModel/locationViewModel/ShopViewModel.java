package com.example.skuniv.fleamarket2.viewModel.locationViewModel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.example.skuniv.fleamarket2.model.locatonModel.Goods;
import com.example.skuniv.fleamarket2.model.locatonModel.ShopModel;

public class ShopViewModel {
    public final ObservableField<Integer> no = new ObservableField<>();
    public final ObservableField<String> location = new ObservableField<>();
    public final ObservableField<String> shop = new ObservableField<>();
    public final ObservableArrayList<GoodsViewModel> goods = new ObservableArrayList<>();

    public ShopViewModel(){

    }

    public ShopViewModel(ShopModel shopModel) {
        //this.shopModel.set(shopModel);
        this.no.set(shopModel.getNo());
        this.location.set(shopModel.getLocation());
        this.shop.set(shopModel.getShop());
        for(int i =0; i<shopModel.getGoods().size() ;i++){
            goods.add(new GoodsViewModel(shopModel.getGoods().get(i).getName(), shopModel.getGoods().get(i).getPrice(),
                    shopModel.getGoods().get(i).getQuantity(),shopModel.getGoods().get(i).getCategory(),shopModel.getGoods().get(i).getImage()));
        }
    }

    public void setShopViewModel(ShopModel shopModel){
        this.no.set(shopModel.getNo());
        this.location.set(shopModel.getLocation());
        this.shop.set(shopModel.getShop());
        for(int i =0; i<shopModel.getGoods().size() ;i++){
            goods.add(new GoodsViewModel(shopModel.getGoods().get(i).getName(), shopModel.getGoods().get(i).getPrice(),
                    shopModel.getGoods().get(i).getQuantity(),shopModel.getGoods().get(i).getCategory(),shopModel.getGoods().get(i).getImage()));
        }
    }

    /*public void ShopClickListener(View view) {
        binding = (ShopItemBinding)
                DataBindingUtil.inflate(LayoutInflater.from(view.getContext()), R.layout.shop_item, null, false);
        binding.setShopItem(this);
        Log.i("ShopClickListener", "call==========" + no);
        //goodsArrayList = new ArrayList<Goods>();
        //goodsArrayList = binding.getShop().goods;
        ////goodsArrayList.addAll()binding.getShop().goods.get());
        //goodsListViewModel = new GoodsListViewModel();
        //goodsListViewModel.setGoodsList(goodsArrayList);

        //goodsListAdapter = new GoodsListAdapter();
        Intent intent = new Intent(view.getContext(), RecyclerView.class);
        intent.putExtra("goods", goods);
        view.getContext().startActivity(intent);
    }*/
}
