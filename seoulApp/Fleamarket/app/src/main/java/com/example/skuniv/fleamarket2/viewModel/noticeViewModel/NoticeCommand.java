package com.example.skuniv.fleamarket2.viewModel.noticeViewModel;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.skuniv.fleamarket2.databinding.ActivityNoticeBinding;
import com.example.skuniv.fleamarket2.model.noticeModel.NoticeData;
import com.example.skuniv.fleamarket2.model.noticeModel.NoticeListModel;
import com.example.skuniv.fleamarket2.model.noticeModel.NoticeModel;
import com.example.skuniv.fleamarket2.retrofit.FileDownloadService;
import com.example.skuniv.fleamarket2.retrofit.NetRetrofit;
import com.example.skuniv.fleamarket2.retrofit.RetrofitService;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.support.constraint.Constraints.TAG;

public class NoticeCommand {
    Context context;
    ActivityNoticeBinding categoryListBinding;
    NoticeListModel noticeListModel;
    NoticeItemsViewModel noticeItemsViewModel;
    NoticeCommand noticeCommand;
    List<NoticeModel> noticeList;
    Gson gson = new Gson();
    String fileUrl;
    NoticeMetaViewModel noticeMetaViewModel;
    NoticeData noticeData;

    public NoticeCommand(Context context, ActivityNoticeBinding categoryListBinding, NoticeListModel noticeListModel, NoticeItemsViewModel noticeItemsViewModel,
                         NoticeMetaViewModel noticeMetaViewModel,NoticeData noticeData){
        this.context = context;
        this.categoryListBinding = categoryListBinding;
        this.noticeListModel = noticeListModel;
        this.noticeItemsViewModel = noticeItemsViewModel;
        this.noticeMetaViewModel = noticeMetaViewModel;
        this.noticeData = noticeData;
        noticeCommand = this;

    }

    public void getNoticeList(){
        if (!(noticeListModel.getPage() <= 0)) {
            Call<NoticeData> res = NetRetrofit.getInstance().getService().getNoticeRepos(noticeListModel.getPage());
            Log.i("getNoticeList",""+res);
            res.enqueue(new Callback<NoticeData>() {
                @Override
                public void onResponse(Call<NoticeData> call, Response<NoticeData> response) {
                    Log.i("Retrofit", response.toString());
                    if (response.body() != null) {
                        noticeData = response.body();
                        Log.i("getShopList",""+gson.toJson(noticeList));
                        noticeItemsViewModel.setNoticeList(noticeData.getItems());
                        noticeMetaViewModel.count.set(noticeData.getMeta().getCount());
                        System.out.println(noticeItemsViewModel);
                        System.out.println(noticeItemsViewModel.getNoticeList());
                        //mAdapter = new CategoryListAdapter(categoryShopsViewModel.getShops(), context,categoryCommand);
                        //categoryListBinding.recyclerId2.setAdapter(mAdapter);
                        //getAdapter();
                    }
                }
                @Override
                public void onFailure(Call<NoticeData> call, Throwable t) {
                    Log.e("에러", t.getMessage());
                }
            });
        } else {
            Log.e("getNoticeList","getNoticeList error");
        }
    }

    public void getSearchList(){
        System.out.println("type =====" + noticeListModel.getType() + "  keyword====" + noticeListModel.getKeyword() + "   page====" + noticeListModel.getPage());
        if (!(noticeListModel.getKeyword().equals("")) && !(noticeListModel.getType().equals(""))) {
            Call<NoticeData> res = NetRetrofit.getInstance().getService().getNoticeSearchRepos(noticeListModel.getType(), noticeListModel.getKeyword(), noticeListModel.getPage());
            Log.i("getSearchList",""+res);
            res.enqueue(new Callback<NoticeData>() {
                @Override
                public void onResponse(Call<NoticeData> call, Response<NoticeData> response) {
                    Log.i("Retrofit", response.toString());
                    if (response.body() != null) {
                        noticeData = response.body();
                        Log.i("getShopList",""+gson.toJson(noticeList));
                        noticeItemsViewModel.setNoticeList(noticeData.getItems());
                        noticeMetaViewModel.count.set(noticeData.getMeta().getCount());
                        System.out.println(noticeItemsViewModel);
                        System.out.println(noticeItemsViewModel.getNoticeList());
                        //mAdapter = new CategoryListAdapter(categoryShopsViewModel.getShops(), context,categoryCommand);
                        //categoryListBinding.recyclerId2.setAdapter(mAdapter);
                        //getAdapter();
                    }
                }
                @Override
                public void onFailure(Call<NoticeData> call, Throwable t) {
                    Log.e("에러", t.getMessage());
                }
            });
        } else {
            Log.e("getNoticeList","getNoticeList error");
        }
    }

    public void jsonPaser(String jsonObject){
        Gson gson = new Gson();
        noticeData = gson.fromJson(jsonObject,  NoticeData.class);

        noticeItemsViewModel.setNoticeList(noticeData.getItems());
        noticeMetaViewModel.count.set(noticeData.getMeta().getCount());
        System.out.println("jsonPaser===========");
    }

    public void downlaodFile(String fileUrl){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://13.125.128.130/");
        Retrofit retrofit = builder.build();
        FileDownloadService fileDownloadService = retrofit.create(FileDownloadService.class);

        this.fileUrl = fileUrl;

        Call<ResponseBody> call = fileDownloadService.downloadFileWithDynamicUrlAsync(fileUrl);
        System.out.println("======url===" + fileUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "server contacted and has file");

                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            boolean writtenToDisk = writeResponseBodyToDisk(response.body());
                            System.out.println("response body--------" + gson.toJson(response));

                            Log.d(TAG, "file download was a success? " + writtenToDisk);
                            return null;
                        }
                    }.execute();
                }
                else {
                    Log.d(TAG, "server contact failed");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error");
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {

        try {
            String sdcard = Environment.getExternalStorageState();
            // todo change the file location/name according to your needs
            File futureStudioIconFile = null;

            if ( !sdcard.equals(Environment.MEDIA_MOUNTED))
            {
                // SD카드가 마운트되어있지 않음
                futureStudioIconFile = Environment.getRootDirectory();
                Log.i("SD 카드 마운트 ㅌㅌㅌㅌㅌ","ㅇ");
            }
            else
            {
                // SD카드가 마운트되어있음
                futureStudioIconFile = Environment.getExternalStorageDirectory();
                Log.i("SD 카드 마운트 되어있음","ㅇ");
            }
            String dir = futureStudioIconFile.getAbsolutePath() + "/notice";
            futureStudioIconFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Downloads","WIT");

            System.out.println("========"+Environment.getExternalStorageDirectory().getAbsolutePath());

            if(!futureStudioIconFile.exists()) {
                futureStudioIconFile.mkdirs();
                System.out.println("make dir============"+futureStudioIconFile.mkdirs());
            }

            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/"),fileUrl.lastIndexOf("."));
            System.out.println("file name ======" + fileName);
            futureStudioIconFile = new File(futureStudioIconFile,fileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}