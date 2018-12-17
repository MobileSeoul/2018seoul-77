package com.example.skuniv.fleamarket2.view.categoryView;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.example.skuniv.fleamarket2.adapter.CategoryListAdapter;
import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.databinding.ActivityCategoryListBinding;
import com.example.skuniv.fleamarket2.databinding.CategoryItemBinding;
import com.example.skuniv.fleamarket2.model.categoryModel.CategoryData;
import com.example.skuniv.fleamarket2.model.categoryModel.CategoryModel;
import com.example.skuniv.fleamarket2.viewModel.categoryViewmodel.CategoryCommand;
import com.example.skuniv.fleamarket2.viewModel.categoryViewmodel.CategoryMetaViewModel;
import com.example.skuniv.fleamarket2.viewModel.categoryViewmodel.CategoryShopViewModel;
import com.example.skuniv.fleamarket2.viewModel.categoryViewmodel.CategoryShopsViewModel;


public class CategoryListActivity extends AppCompatActivity implements CategoryListAdapter.OnLoadMoreListener{


    static ActivityCategoryListBinding categoryListBinding;
    CategoryModel categoryModel;
    static Context context;
    static CategoryShopsViewModel categoryShopsViewModel;
    static CategoryCommand categoryCommand;
    static CategoryListAdapter adapter;
    static LinearLayoutManager llm;
    static CategoryListAdapter.OnLoadMoreListener onLoadMoreListener;
    static CategoryData categoryData;
    static CategoryMetaViewModel categoryMetaViewModel;
    ArrayAdapter spinnerAdapter;
    String category="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryListBinding = DataBindingUtil.setContentView(this, R.layout.activity_category_list);

        category = getIntent().getStringExtra("category");
        categoryModel = new CategoryModel(category,1);
        categoryData = new CategoryData();

        categoryShopsViewModel = new CategoryShopsViewModel();
        categoryMetaViewModel = new CategoryMetaViewModel();

        // 카테고리모델 셋팅
        categoryListBinding.setCategoryModel(categoryModel);
        categoryListBinding.setShopsList(categoryShopsViewModel);

        categoryCommand = new CategoryCommand(this, categoryListBinding, categoryModel, categoryShopsViewModel, categoryData,categoryMetaViewModel);

        onLoadMoreListener = this;
        context = this;

        // Initializing an ArrayAdapter
        spinnerAdapter = new ArrayAdapter(
                this,android.R.layout.simple_spinner_item, getList());

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryListBinding.spinnerId.setAdapter(spinnerAdapter);

        categoryListBinding.spinnerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) adapterView.getChildAt(0)).setTextSize(30);
                categoryShopsViewModel.shops.clear();
                categoryModel.setPageNum(1);
                categoryModel.addCategory(adapterView.getItemAtPosition(i).toString());
                System.out.println("category ===="+categoryModel.categoryStr + "   "+categoryModel.categoryList);
                categoryCommand.getGoodsList();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        categoryListBinding.recyclerId2.setLayoutManager(llm);
        adapter = new CategoryListAdapter(categoryShopsViewModel.getShops(), context,categoryCommand,onLoadMoreListener,categoryMetaViewModel);
        categoryListBinding.recyclerId2.setAdapter(adapter);
    }

    @Override
    public void onLoadMore() {
        Log.d("MainActivity_", "onLoadMore");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    ///////이부분에을 자신의 프로젝트에 맞게 설정하면 됨
                    //다음 페이지? 내용을 불러오는 부분
                    //categoryCommand.scrollListener();

                    categoryModel.setPageNum(categoryModel.getPageNum() + 1);
                    categoryCommand.getGoodsList();
                    //categoryCommand.jsonPaser(getJson(categoryModel.getPageNum()));
                    adapter.setMoreLoading(false);
                    //////////////////////////////////////////////////
                    //mAdapter.setMoreLoading(false);
                }
            }, 2000);
        /*categoryModel.setPageNum(categoryModel.getPageNum()+1);
        categoryCommand.jsonPaser(getJson(categoryModel.getPageNum()));
        adapter.setMoreLoading(false);*/

    }

    //listView adapter 생성
    @BindingAdapter("app:items")
    public static void setShopList(RecyclerView recyclerView, ObservableArrayList<CategoryShopViewModel> shops){
        //adapter 없을 때 adapter 생성
        System.out.println("setShopList=============");
        if(recyclerView.getAdapter() == null){
            System.out.println("new categoryListAdapter");
            adapter = new CategoryListAdapter(shops, context,categoryCommand,onLoadMoreListener,categoryMetaViewModel);
            adapter.setLinearLayoutManager(llm);
            recyclerView.setAdapter(adapter);
            adapter.setRecyclerView(categoryListBinding.recyclerId2);
        }
        else {
            // 있으면 getAdapter
            adapter = (CategoryListAdapter) recyclerView.getAdapter();
            System.out.println("size =========" + categoryShopsViewModel.shops.size());
            adapter.setShopsList(categoryShopsViewModel.shops);
            System.out.println("get adapter");
        }
        //adapter.addAll(shops);
        //Log.i("adapter", shops.get(0).getGoods().get(0).getImage());
    }

    private String[] getList(){
        if(category.equals("옷")){
            return getResources().getStringArray(R.array.cloth);
        }else if(category.equals("디지털")){
            return getResources().getStringArray(R.array.digital);
        }else if(category.equals("잡화")){
            return getResources().getStringArray(R.array.fancy);
        }else if(category.equals("악세서리")){
            return getResources().getStringArray(R.array.acc);
        }else if(category.equals("도서")){
            return getResources().getStringArray(R.array.book);
        }else if(category.equals("기타")){
            return getResources().getStringArray(R.array.etc);
        }
        return null;
    }

    public String getJson(int page){
        String json="";
        System.out.println("getJson page======"+ page);
        if(page == 1) {
            json = "{\n" +
                    "\"items\": [\n" +
                    "{\n" +
                    "\"no\": 1,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E01\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 2,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E02\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 3,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E03\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 4,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E04\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 5,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E05\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 6,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E06\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 7,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E07\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 8,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E08\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 9,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E09\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 10,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E10\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "}\n" +
                    "],\n" +
                    "\"meta\": {\n" +
                    "\"count\": 36\n" +
                    "}\n" +
                    "}";
        }
        else if(page == 2){
            json = "{\n" +
                    "\"items\": [\n" +
                    "{\n" +
                    "\"no\": 1,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E01\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 2,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E02\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 3,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E03\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 4,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E04\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 5,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E05\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 6,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E06\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 7,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E07\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 8,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E08\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 9,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E09\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 10,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E10\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "}\n" +
                    "],\n" +
                    "\"meta\": {\n" +
                    "\"count\": 36\n" +
                    "}\n" +
                    "}";
        }
        else if(page ==3){
            json ="{\n" +
                    "\"items\": [\n" +
                    "{\n" +
                    "\"no\": 1,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E01\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 2,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E02\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 3,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E03\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 4,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E04\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 5,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E05\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 6,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E06\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 7,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E07\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 8,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E08\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 9,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E09\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 10,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E10\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "}\n" +
                    "],\n" +
                    "\"meta\": {\n" +
                    "\"count\": 36\n" +
                    "}\n" +
                    "}";
        }else if(page ==4){
            json ="{\n" +
                    "\"items\": [\n" +
                    "{\n" +
                    "\"no\": 1,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E01\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 2,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E02\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 3,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E03\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 4,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E04\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 5,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E05\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 6,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E06\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 7,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E07\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 8,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E08\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 9,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E09\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 10,\n" +
                    "\"location\": \"e\",\n" +
                    "\"shop\": \"E10\",\n" +
                    "\"good\": {\n" +
                    "\"name\": \"polo 카라티\",\n" +
                    "\"price\": 5000,\n" +
                    "\"quantity\": 2,\n" +
                    "\"category\": \"1\",\n" +
                    "\"image\": \"http://13.125.128.130/static/bazaar_img/e/1-1.jpg\"\n" +
                    "}\n" +
                    "}\n" +
                    "],\n" +
                    "\"meta\": {\n" +
                    "\"count\": 36\n" +
                    "}\n" +
                    "}";
        } else{
            System.out.println("마지막 페이지 입니다.");
        }

        return json;

    }
}
