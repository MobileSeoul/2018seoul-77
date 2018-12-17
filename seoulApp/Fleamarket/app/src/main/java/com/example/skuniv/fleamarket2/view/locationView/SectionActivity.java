package com.example.skuniv.fleamarket2.view.locationView;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.adapter.ShopListAdapter;
import com.example.skuniv.fleamarket2.databinding.ActivitySectionBinding;
import com.example.skuniv.fleamarket2.model.locatonModel.SectionModel;
import com.example.skuniv.fleamarket2.model.locatonModel.ShopData;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.SectionCommand;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.ShopMetaViewModel;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.ShopViewModel;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.ShopsViewModel;

public class SectionActivity extends AppCompatActivity {
    ShopsViewModel shopsViewModel = new ShopsViewModel();
    private ShopSelectDialog dialog;
    SectionCommand sectionCommand;
    ActivitySectionBinding sectionBinding;
    SectionModel sectionModel;
    String json;
    ShopData shopData;
    GoodsRecyclerDialog recyclerDialog;
    ShopMetaViewModel shopMetaViewModel;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // activity_section과 databiding할 객체 생성
        sectionBinding = DataBindingUtil.setContentView(this, R.layout.activity_section);
        Log.i("section","==========="+getIntent().getStringExtra("section"));

        shopData = new ShopData();
        shopMetaViewModel = new ShopMetaViewModel();

        // section model객체 생성
        sectionModel = new SectionModel(getIntent().getStringExtra("section"),1);

        //sectionCommand 객체 생성
        sectionCommand = new SectionCommand(sectionModel, sectionBinding,shopsViewModel,shopData,shopMetaViewModel);

        // activity_section의 sectionModel 변수에 sectionModel 넣음
        sectionBinding.setSectionModel(sectionModel);

        // activity_section의 ShopModel 변수에 shopsViewModel 넣음
        sectionBinding.setShopModel(shopsViewModel);

        /*shopItemBinding = DataBindingUtil.setContentView(this,R.layout.shop_item);
        Myhandler myhandler = new Myhandler(this,shopItemBinding);
        shopItemBinding.setHandler(myhandler);*/

        sectionCommand.getShopList();
        //sectionCommand.jsonPaser(getJson());
        context = this;

        //select diaolg 띄우기
        sectionBinding.selectId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ShopSelectDialog(SectionActivity.this,sectionModel,sectionCommand,shopMetaViewModel);
                dialog.show();
            }
        });

        sectionBinding.listId.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("ShopClickListener", "call==========" + i + "======="+l);
                if(shopsViewModel.shops.get(i).goods.size() <= 0){
                    Toast.makeText(context,"등록된 상품이 없습니다.",Toast.LENGTH_SHORT).show();
                } else {
                    recyclerDialog = new GoodsRecyclerDialog(SectionActivity.this, shopsViewModel.shops.get(i));
                    recyclerDialog.show();
                }
            }
        });

        sectionBinding.mapId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapDialog mapDialog = new MapDialog(context);
                mapDialog.show();
            }
        });
    }

    //listView adapter 생성
    @BindingAdapter("app:items")
    public static void setShopList(ListView listView, ObservableArrayList<ShopViewModel> shops){
        ShopListAdapter adapter;
        //adapter 없을 때 adapter 생성
        if(listView.getAdapter() == null){
            adapter = new ShopListAdapter();
            listView.setAdapter(adapter);
        }
        else {
            // 있으면 getAdapter
            adapter = (ShopListAdapter) listView.getAdapter();
        }
        adapter.addAll(shops);
       //Log.i("adapter", shops.get(0).getGoods().get(0).getImage());
    }

    public String getJson(){
        String jsonObject;

        jsonObject = "{\n" +
                "\"items\": [\n" +
                "{\n" +
                "\"no\": 1.0,\n" +
                "\"location\": \"a\",\n" +
                "\"shop\": \"A01\",\n" +
                "\"goods\": [\n" +
                "{\n" +
                "\"name\": \"polo 카라티\",\n" +
                "\"price\": 5000.0,\n" +
                "\"quantity\": 2.0,\n" +
                "\"category\": \"1\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-1.jpg\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"Tepal 다리미\",\n" +
                "\"price\": 25000.0,\n" +
                "\"quantity\": 1.0,\n" +
                "\"category\": \"6\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-2.jpg\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"반팔티\",\n" +
                "\"price\": \"3000\",\n" +
                "\"quantity\": \"2\",\n" +
                "\"category\": \"1\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/d/1-1.jpg\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"no\": 2.0,\n" +
                "\"location\": \"a\",\n" +
                "\"shop\": \"A02\",\n" +
                "\"goods\": [\n" +
                "{\n" +
                "\"name\": \"polo 카라티\",\n" +
                "\"price\": 5000.0,\n" +
                "\"quantity\": 2.0,\n" +
                "\"category\": \"1\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-1.jpg\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"Tepal 다리미\",\n" +
                "\"price\": 25000.0,\n" +
                "\"quantity\": 1.0,\n" +
                "\"category\": \"6\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-2.jpg\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"no\": 3.0,\n" +
                "\"location\": \"a\",\n" +
                "\"shop\": \"A03\",\n" +
                "\"goods\": [\n" +
                "{\n" +
                "\"name\": \"polo 카라티\",\n" +
                "\"price\": 5000.0,\n" +
                "\"quantity\": 2.0,\n" +
                "\"category\": \"1\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-1.jpg\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"Tepal 다리미\",\n" +
                "\"price\": 25000.0,\n" +
                "\"quantity\": 1.0,\n" +
                "\"category\": \"6\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-2.jpg\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"no\": 4.0,\n" +
                "\"location\": \"a\",\n" +
                "\"shop\": \"A04\",\n" +
                "\"goods\": [\n" +
                "{\n" +
                "\"name\": \"polo 카라티\",\n" +
                "\"price\": 5000.0,\n" +
                "\"quantity\": 2.0,\n" +
                "\"category\": \"1\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-1.jpg\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"Tepal 다리미\",\n" +
                "\"price\": 25000.0,\n" +
                "\"quantity\": 1.0,\n" +
                "\"category\": \"6\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-2.jpg\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"no\": 5.0,\n" +
                "\"location\": \"a\",\n" +
                "\"shop\": \"A05\",\n" +
                "\"goods\": [\n" +
                "{\n" +
                "\"name\": \"polo 카라티\",\n" +
                "\"price\": 5000.0,\n" +
                "\"quantity\": 2.0,\n" +
                "\"category\": \"1\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-1.jpg\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"Tepal 다리미\",\n" +
                "\"price\": 25000.0,\n" +
                "\"quantity\": 1.0,\n" +
                "\"category\": \"6\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-2.jpg\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"no\": 6.0,\n" +
                "\"location\": \"a\",\n" +
                "\"shop\": \"A06\",\n" +
                "\"goods\": [\n" +
                "{\n" +
                "\"name\": \"polo 카라티\",\n" +
                "\"price\": 5000.0,\n" +
                "\"quantity\": 2.0,\n" +
                "\"category\": \"1\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-1.jpg\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"Tepal 다리미\",\n" +
                "\"price\": 25000.0,\n" +
                "\"quantity\": 1.0,\n" +
                "\"category\": \"6\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-2.jpg\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"no\": 7.0,\n" +
                "\"location\": \"a\",\n" +
                "\"shop\": \"A07\",\n" +
                "\"goods\": [\n" +
                "{\n" +
                "\"name\": \"polo 카라티\",\n" +
                "\"price\": 5000.0,\n" +
                "\"quantity\": 2.0,\n" +
                "\"category\": \"1\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-1.jpg\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"Tepal 다리미\",\n" +
                "\"price\": 25000.0,\n" +
                "\"quantity\": 1.0,\n" +
                "\"category\": \"6\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-2.jpg\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"no\": 8.0,\n" +
                "\"location\": \"a\",\n" +
                "\"shop\": \"A08\",\n" +
                "\"goods\": [\n" +
                "{\n" +
                "\"name\": \"polo 카라티\",\n" +
                "\"price\": 5000.0,\n" +
                "\"quantity\": 2.0,\n" +
                "\"category\": \"1\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-1.jpg\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"Tepal 다리미\",\n" +
                "\"price\": 25000.0,\n" +
                "\"quantity\": 1.0,\n" +
                "\"category\": \"6\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-2.jpg\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"no\": 9.0,\n" +
                "\"location\": \"a\",\n" +
                "\"shop\": \"A09\",\n" +
                "\"goods\": [\n" +
                "{\n" +
                "\"name\": \"polo 카라티\",\n" +
                "\"price\": 5000.0,\n" +
                "\"quantity\": 2.0,\n" +
                "\"category\": \"1\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-1.jpg\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"Tepal 다리미\",\n" +
                "\"price\": 25000.0,\n" +
                "\"quantity\": 1.0,\n" +
                "\"category\": \"6\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-2.jpg\"\n" +
                "}\n" +
                "]\n" +
                "},\n" +
                "{\n" +
                "\"no\": 10.0,\n" +
                "\"location\": \"a\",\n" +
                "\"shop\": \"A10\",\n" +
                "\"goods\": [\n" +
                "{\n" +
                "\"name\": \"polo 카라티\",\n" +
                "\"price\": 5000.0,\n" +
                "\"quantity\": 2.0,\n" +
                "\"category\": \"1\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-1.jpg\"\n" +
                "},\n" +
                "{\n" +
                "\"name\": \"Tepal 다리미\",\n" +
                "\"price\": 25000.0,\n" +
                "\"quantity\": 1.0,\n" +
                "\"category\": \"6\",\n" +
                "\"image\": \"http://13.125.128.130/static/bazaar_img/a/1-2.jpg\"\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "],\n" +
                "\"meta\": {\n" +
                "\"count\": 75\n" +
                "}\n" +
                "}";

        return jsonObject;
    }
}
