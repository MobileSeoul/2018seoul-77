package com.example.skuniv.fleamarket2.view.noticeView;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.adapter.CategoryListAdapter;
import com.example.skuniv.fleamarket2.adapter.NoticeListAdapter;
import com.example.skuniv.fleamarket2.databinding.ActivityNoticeBinding;
import com.example.skuniv.fleamarket2.model.noticeModel.NoticeData;
import com.example.skuniv.fleamarket2.model.noticeModel.NoticeListModel;
import com.example.skuniv.fleamarket2.viewModel.categoryViewmodel.CategoryShopViewModel;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeCommand;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeFilesViewModel;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeItemViewModel;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeItemsViewModel;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeMetaViewModel;

public class NoticeActivity extends AppCompatActivity implements NoticeListAdapter.OnLoadMoreListener{
    static ActivityNoticeBinding binding;
    static NoticeItemsViewModel noticeItemsViewModel;
    NoticeListModel noticeListModel;
    static NoticeListAdapter adapter;
    static LinearLayoutManager llm;
    static Context context;
    static NoticeCommand noticeCommand;
    static NoticeListAdapter.OnLoadMoreListener onLoadMoreListener;
    static NoticeMetaViewModel noticeMetaViewModel;
    String type;
    NoticeData noticeData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_notice);

        noticeListModel = new NoticeListModel(1);
        noticeItemsViewModel = new NoticeItemsViewModel();
        noticeMetaViewModel = new NoticeMetaViewModel();


        binding.setNoticeList(noticeItemsViewModel);

        context = this;

        noticeCommand = new NoticeCommand(context, binding, noticeListModel, noticeItemsViewModel, noticeMetaViewModel, noticeData);

        onLoadMoreListener = this;

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        binding.recyclerId3.setLayoutManager(llm);
        noticeCommand.getNoticeList();
        //noticeCommand.jsonPaser(getJson(noticeListModel.getPage()));

        binding.spinnerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                type = "";
                if(adapterView.getItemAtPosition(i).equals("제목")){
                    noticeListModel.setType("title");
                }
                else if(adapterView.getItemAtPosition(i).equals("내용")){
                    noticeListModel.setType("contents");
                }else if(adapterView.getItemAtPosition(i).equals("제목+내용")){
                    noticeListModel.setType("titlecontents");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {        }
        });

        binding.searchBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = binding.searchTextId.getText().toString();
                noticeListModel.setKeyword(keyword);
                noticeListModel.setPage(1);
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                if(!keyword.equals("")){
                    noticeItemsViewModel.noticeList.clear();
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    noticeCommand.getSearchList();
                }
                else{
                    Toast.makeText(NoticeActivity.this, "키워드를 입력하세요", Toast.LENGTH_SHORT).show();
                    noticeCommand.getNoticeList();
                }
            }
        });
    }

    @Override
    public void onLoadMore() {
        Log.d("NoticeActivity_", "onLoadMore");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ///////이부분에을 자신의 프로젝트에 맞게 설정하면 됨
                //다음 페이지? 내용을 불러오는 부분
                //categoryCommand.scrollListener();
                noticeListModel.setPage(noticeListModel.getPage()+1);
                if(!noticeListModel.getKeyword().equals(""))
                    noticeCommand.getSearchList();
                else
                    noticeCommand.getNoticeList();
                //noticeCommand.jsonPaser(getJson(noticeListModel.getPage()));
                adapter.setMoreLoading(false);
                //////////////////////////////////////////////////
                //mAdapter.setMoreLoading(false);
            }
        }, 2000);
        /*categoryModel.setPageNum(categoryModel.getPageNum()+1);
        categoryCommand.jsonPaser(getJson(categoryModel.getPageNum()));
        adapter.setMoreLoading(false);*/
    }

    @BindingAdapter("app:items")
    public static void setNoticeList(RecyclerView recyclerView, ObservableArrayList<NoticeItemViewModel> shops){
        //adapter 없을 때 adapter 생성
        System.out.println("setShopList=============");
        if(recyclerView.getAdapter() == null){
            System.out.println("new categoryListAdapter");
            adapter = new NoticeListAdapter(noticeItemsViewModel.getNoticeList(),context,noticeCommand,onLoadMoreListener,noticeMetaViewModel);
            adapter.setLinearLayoutManager(llm);
            recyclerView.setAdapter(adapter);
            adapter.setRecyclerView(binding.recyclerId3);
        }
        else {
            // 있으면 getAdapter
            adapter = (NoticeListAdapter) recyclerView.getAdapter();
            adapter.setNoiceList(noticeItemsViewModel.getNoticeList());
            System.out.println("get adapter");
        }
        //adapter.addAll(shops);
        //Log.i("adapter", shops.get(0).getGoods().get(0).getImage());
    }

    public String getJson(int page){
        String json="";
        if(page == 1){
            json = "{\n" +
                    "\"items\": [\n" +
                    "{\n" +
                    "\"no\": 1,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 2,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 3,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 4,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 5,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 6,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 7,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 8,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 9,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 10,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"meta\": {\n" +
                    "\"count\": 16\n" +
                    "}\n" +
                    "}";
        }
        else if(page == 2){
            json = "{\n" +
                    "\"items\": [\n" +
                    "{\n" +
                    "\"no\": 1,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 2,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 3,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 4,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 5,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 6,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 7,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 8,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 9,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 10,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"meta\": {\n" +
                    "\"count\": 16\n" +
                    "}\n" +
                    "}";
        }
        else if(page == 3){
            json = "{\n" +
                    "\"items\": [\n" +
                    "{\n" +
                    "\"no\": 1,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 2,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 3,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 4,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 5,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 6,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 7,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 8,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 9,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 10,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"meta\": {\n" +
                    "\"count\": 16\n" +
                    "}\n" +
                    "}";
        }

        if(page ==4){
            json = "{\n" +
                    "\"items\": [\n" +
                    "{\n" +
                    "\"no\": 1,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 2,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 3,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 4,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 5,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 6,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 7,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 8,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 9,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"no\": 10,\n" +
                    "\"title\": \"윤상 아~\",\n" +
                    "\"files\": [\n" +
                    "{\n" +
                    "\"fName\": \"채지연 아~\",\n" +
                    "\"fPath\": \"http://13.125.128.130/static/files/sample.txt\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"contents\": \"좋아\",\n" +
                    "\"date\": \"2018-09-20\"\n" +
                    "}\n" +
                    "],\n" +
                    "\"meta\": {\n" +
                    "\"count\": 16\n" +
                    "}\n" +
                    "}";
        }
        return json;
    }
}
