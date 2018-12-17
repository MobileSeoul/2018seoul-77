package com.example.skuniv.fleamarket2.view.categoryView;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.example.skuniv.fleamarket2.R;
import com.bumptech.glide.Glide;
import com.example.skuniv.fleamarket2.databinding.CategoryGoodsInfoBinding;
import com.example.skuniv.fleamarket2.model.locatonModel.Goods;
import com.example.skuniv.fleamarket2.view.locationView.MapDialog;
import com.example.skuniv.fleamarket2.viewModel.categoryViewmodel.CategoryShopViewModel;

public class CategoryGoodsInfoDialog extends Dialog{
    CategoryShopViewModel categoryShopViewModel;
    private CategoryGoodsInfoBinding dialogBinding;
    private CategoryGoodsInfoDialog categoryGoodsInfoDialog;
    Context context;

    TextView[] shopText;

    String shop,shopNum;
    public CategoryGoodsInfoDialog(Context context, CategoryShopViewModel categoryShopViewModel) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.categoryShopViewModel = categoryShopViewModel;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryGoodsInfoDialog = this;
        shop = categoryShopViewModel.shop.get();

        shopNum = shop.split(categoryShopViewModel.location.get().toUpperCase())[1];
        System.out.println("shop num    "+shopNum);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        dialogBinding = (CategoryGoodsInfoBinding)
                DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.category_goods_info, null, false);
        setContentView(dialogBinding.getRoot());

        dialogBinding.setInfo(categoryGoodsInfoDialog);
        dialogBinding.setGoods(categoryShopViewModel.goods);
        Glide.with(context).load(categoryShopViewModel.goods.get().getImage()).override(300, 300).into(dialogBinding.imageId);
        setShopList();

        dialogBinding.mapId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapDialog mapDialog = new MapDialog(context);
                mapDialog.show();
            }
        });

        shopText[Integer.parseInt(shopNum)-1].setTextColor(getContext().getResources().getColor(R.color.red));
        //DialogBinding.setSelect(shopNum);
        //setBack = new Boolean[120];
        //setBack[shopNum] = true;
        //dialogBinding.setShopList(setBack);
    }


    //@BindingAdapter({"bind:setColor"})
    public void setBackground(View view) {


        System.out.println("select shop map==="+view.getId());
    }

    public void setShopList(){
        shopText = new TextView[]{ dialogBinding.id1 , dialogBinding.id2, dialogBinding.id3, dialogBinding.id4,dialogBinding.id5,dialogBinding.id6,dialogBinding.id7,dialogBinding.id8,dialogBinding.id9,dialogBinding.id10,
                dialogBinding.id11,dialogBinding.id12,dialogBinding.id13,dialogBinding.id14,dialogBinding.id15,dialogBinding.id16,dialogBinding.id17,dialogBinding.id18,dialogBinding.id19,dialogBinding.id20,
                dialogBinding.id21,dialogBinding.id22,dialogBinding.id23,dialogBinding.id24,dialogBinding.id25,dialogBinding.id26,dialogBinding.id27,dialogBinding.id28,dialogBinding.id29,dialogBinding.id30,
                dialogBinding.id31,dialogBinding.id32,dialogBinding.id33,dialogBinding.id34,dialogBinding.id35,dialogBinding.id36,dialogBinding.id37,dialogBinding.id38,dialogBinding.id39,dialogBinding.id40,
                dialogBinding.id41,dialogBinding.id42,dialogBinding.id43,dialogBinding.id44,dialogBinding.id45,dialogBinding.id46,dialogBinding.id47,dialogBinding.id48,dialogBinding.id49,dialogBinding.id50,
                dialogBinding.id51,dialogBinding.id52,dialogBinding.id53,dialogBinding.id54,dialogBinding.id55,dialogBinding.id56,dialogBinding.id57,dialogBinding.id58,dialogBinding.id59,dialogBinding.id60,
                dialogBinding.id61,dialogBinding.id62,dialogBinding.id63,dialogBinding.id64,dialogBinding.id65,dialogBinding.id66,dialogBinding.id67,dialogBinding.id68,dialogBinding.id69,dialogBinding.id70,
                dialogBinding.id71,dialogBinding.id72,dialogBinding.id73,dialogBinding.id74,dialogBinding.id75,dialogBinding.id76,dialogBinding.id77,dialogBinding.id78,dialogBinding.id79,dialogBinding.id80 };
    }
}
