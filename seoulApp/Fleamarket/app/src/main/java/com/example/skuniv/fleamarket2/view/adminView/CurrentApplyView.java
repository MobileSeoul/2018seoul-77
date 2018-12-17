package com.example.skuniv.fleamarket2.view.adminView;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.adapter.ApplyListAdapter;
import com.example.skuniv.fleamarket2.adapter.ApplyPageAdapter;
import com.example.skuniv.fleamarket2.databinding.CurrentApplyBinding;
import com.example.skuniv.fleamarket2.model.AdminSellerModel.ApplyListModel;
import com.example.skuniv.fleamarket2.model.AdminSellerModel.UserModel;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.AdminCommand;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.ApplyItemViewModel;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.ApplyItemsViewModel;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.ApplyMetaViewModel;

import java.util.ArrayList;

public class CurrentApplyView extends AppCompatActivity{

    static CurrentApplyBinding currentApplyBinding;
    static AdminCommand adminCommand;
    static ApplyListAdapter applyListAdapter;
    static LinearLayoutManager listLlm;
    static ApplyPageAdapter applyPageAdapter;
    static LinearLayoutManager pageLlm;
    static Context context;
    static ApplyItemsViewModel applyItemsViewModel;
    static ApplyMetaViewModel applyMetaViewModel;
    static ArrayList<Integer> pageList;
    static ApplyListModel applyListModel;
    UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentApplyBinding = DataBindingUtil.setContentView(this, R.layout.current_apply);
        context = this;

        userModel = (UserModel) getIntent().getSerializableExtra("userModel");

        applyListModel = new ApplyListModel();
        applyItemsViewModel = new ApplyItemsViewModel();
        applyMetaViewModel = new ApplyMetaViewModel();
        pageList = new ArrayList<>();
        adminCommand = new AdminCommand(context, userModel, applyListModel, applyItemsViewModel, applyMetaViewModel);

        listLlm = new LinearLayoutManager(this);
        listLlm.setOrientation(LinearLayoutManager.VERTICAL);
        currentApplyBinding.ListRecyclerId.setLayoutManager(listLlm);

        pageLlm = new LinearLayoutManager(this);
        pageLlm.setOrientation(LinearLayoutManager.HORIZONTAL);
        currentApplyBinding.pageRecyclerId.setLayoutManager(pageLlm);

        currentApplyBinding.setApplyList(applyItemsViewModel);
        currentApplyBinding.setMeta(applyMetaViewModel);

        //adminCommand.jsonPaser(getJson(applyListModel.getPage()));
        adminCommand.getApply();

        currentApplyBinding.randomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminCommand.sendRandom();
            }
        });

        currentApplyBinding.comfirstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminCommand.sendFirstCome();
            }
        });

        currentApplyBinding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminCommand.sendAdmission();
            }
        });
    }

    @BindingAdapter("app:items")
    public static void setApplyList(RecyclerView recyclerView, ObservableArrayList<ApplyItemViewModel> applyList){
        //adapter 없을 때 adapter 생성
        if(recyclerView.getAdapter() == null){
            applyListAdapter = new ApplyListAdapter(applyItemsViewModel.applyList, context, adminCommand,applyMetaViewModel);
            recyclerView.setAdapter(applyListAdapter);
        }
        else {
            // 있으면 getAdapter
            applyListAdapter = (ApplyListAdapter) recyclerView.getAdapter();
            applyListAdapter.setApplyList(applyItemsViewModel.getApplyList());
            System.out.println("get adapter");
        }
        //adapter.addAll(shops);
        //Log.i("adapter", shops.get(0).getGoods().get(0).getImage());
    }

    @BindingAdapter("app:items")
    public static void setPageList(RecyclerView recyclerView, ObservableArrayList<Integer> pageList){
        //adapter 없을 때 adapter 생성
        if(recyclerView.getAdapter() == null){
            applyPageAdapter = new ApplyPageAdapter(context, adminCommand, applyListModel,applyMetaViewModel);
            recyclerView.setAdapter(applyPageAdapter);
        }
        else {
            // 있으면 getAdapter
            applyPageAdapter = (ApplyPageAdapter) recyclerView.getAdapter();
            System.out.println("get adapter");
            applyPageAdapter.setPageList(applyMetaViewModel.pageList);
        }
        //adapter.addAll(shops);
        //Log.i("adapter", shops.get(0).getGoods().get(0).getImage());
    }

    public String getJson(int page){
        String json="";
        if(page == 1){
            json = "{\n" +
                    "items:[\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "}" +
                    "],\n" +
                    "meta:{\n" +
                    "\"count\": 45\n" +
                    "}\n" +
                    "}";
        } else if(page == 2){
            json = "{\n" +
                    "items:[\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "}" +
                    "],\n" +
                    "meta:{\n" +
                    "\"count\": 45\n" +
                    "}\n" +
                    "}";
        } else if(page == 3){
            json = "{\n" +
                    "items:[\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "}" +
                    "],\n" +
                    "meta:{\n" +
                    "\"count\": 45\n" +
                    "}\n" +
                    "}";
        } else if(page == 4){
            json = "{\n" +
                    "items:[\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "}" +
                    "],\n" +
                    "meta:{\n" +
                    "\"count\": 45\n" +
                    "}\n" +
                    "}";
        }else if(page == 5){
            json = "{\n" +
                    "items:[\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},\n" +
                    "{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "},{\n" +
                    "id: \"kim\",\n" +
                    "name: \"yunsang\",\n" +
                    "title: \"this is title\",\n" +
                    "contents: \"this is contents\",\n" +
                    "\"role\": 1.0,\n" +
                    "date: \"this is date\"\n" +
                    "}" +
                    "],\n" +
                    "meta:{\n" +
                    "\"count\": 45\n" +
                    "}\n" +
                    "}";
        }
        return json;
    }
}
