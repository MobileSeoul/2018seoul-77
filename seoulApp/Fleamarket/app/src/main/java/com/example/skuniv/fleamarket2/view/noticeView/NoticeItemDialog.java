package com.example.skuniv.fleamarket2.view.noticeView;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.adapter.GoodsListAdapter;
import com.example.skuniv.fleamarket2.adapter.NoticeFileListAdapter;
import com.example.skuniv.fleamarket2.databinding.NoticeItemBinding;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.GoodsViewModel;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeCommand;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeFileViewModel;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeFilesViewModel;
import com.example.skuniv.fleamarket2.viewModel.noticeViewModel.NoticeItemViewModel;

import java.io.File;
import java.io.Serializable;

import static com.example.skuniv.fleamarket2.view.noticeView.NoticeActivity.llm;

public class NoticeItemDialog extends Dialog{
    static NoticeItemBinding noticeItemBinding;
    NoticeItemViewModel noticeItemViewModel;
    static NoticeFilesViewModel noticeFilesViewModel;
    static Context context;
    static NoticeCommand noticeCommand;
    static LinearLayoutManager llm;

    static final int PERMISSION_REQUEST_CODE = 1;
    String[] PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"};

    public NoticeItemDialog(Context context, NoticeItemViewModel noticeItemViewModel, NoticeCommand noticeCommand){
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.noticeItemViewModel = noticeItemViewModel;
        this.noticeCommand = noticeCommand;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noticeItemBinding = (NoticeItemBinding)
                DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.notice_item, null, false);
        setContentView(noticeItemBinding.getRoot());

        noticeItemBinding.setNotice(noticeItemViewModel);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        /*noticeItemBinding.downId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticeCommand.downlaodFile("http://13.125.128.130/static/bazaar_img/e/1-1.jpg");
            }
        });*/
        noticeFilesViewModel = new NoticeFilesViewModel();
        noticeFilesViewModel.setFileList(noticeItemViewModel.getFiles());
        noticeItemBinding.setFiles(noticeFilesViewModel);

        llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        noticeItemBinding.recyclerId4.setLayoutManager(llm);

    }

    @BindingAdapter("app:items")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<NoticeFileViewModel> fileList) {
        NoticeFileListAdapter adapter; //= (GoodsListAdapter) recyclerView.getAdapter();
        if(recyclerView.getAdapter() == null){
            System.out.println("fileList adapter=====");
            adapter = new NoticeFileListAdapter(fileList, context,noticeCommand);
            recyclerView.setAdapter(adapter);
        }
        else {
            // 있으면 getAdapter
            adapter = (NoticeFileListAdapter) recyclerView.getAdapter();
        }
    }
}
