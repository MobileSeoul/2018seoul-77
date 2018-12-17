package com.example.skuniv.fleamarket2.view.sellerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.adapter.SellerGoodsListAdater;
import com.example.skuniv.fleamarket2.databinding.SellerGoodsListBinding;
import com.example.skuniv.fleamarket2.model.AdminSellerModel.UserModel;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.SellerCommand;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.SellerGoodsViewModel;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.SellerShopViewModel;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.GoodsViewModel;
import com.example.skuniv.fleamarket2.viewModel.locationViewModel.ShopViewModel;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SellerGoodsList extends AppCompatActivity {
    private static final int PICK_FROM_GALLERY = 1;
    private Uri imageUri ;
    private String filename;
    private static final int REQ_CODE_SELECT_IMAGE = 100;
    SellerGoodsListBinding sellerGoodsListBinding;
    UserModel userModel;
    static SellerShopViewModel sellerShopViewModel;
    static Context context;
    static SellerCommand sellerCommand;
    boolean imagebool;
    static SellerGoodsListAdater adapter;
    SellerGoodsList sellerGoodsListview;
    SellerGoodsUpdateDialog sellerGoodsUpdateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sellerGoodsListBinding = DataBindingUtil.setContentView(this, R.layout.seller_goods_list);

        userModel = (UserModel) getIntent().getSerializableExtra("user");
        sellerShopViewModel = new SellerShopViewModel();
        context = this;
        sellerGoodsListview = this;
        sellerCommand = new SellerCommand(context, userModel, sellerShopViewModel, sellerGoodsListview);

        sellerCommand.getShopModel();

        // 레이아웃 매니져, 어댑터 생성 후 recycler에 set 함
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = new SellerGoodsListAdater(sellerShopViewModel.goods, context,sellerCommand);

        sellerGoodsListBinding.recyclerId.setLayoutManager(llm);
        sellerGoodsListBinding.recyclerId.setAdapter(adapter);
        sellerGoodsListBinding.setShop(sellerShopViewModel);

        sellerGoodsListBinding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sellerGoodsUpdateDialog = new SellerGoodsUpdateDialog(context, userModel, sellerShopViewModel, sellerGoodsListview, sellerCommand);
                sellerGoodsUpdateDialog.show();
                checkPremission();
            }
        });

        /*sellerGoodsListBinding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
                //Glide.with(context).load(selectedImagePath).override(300,300).into(sellerGoodsListBinding.imageView);
            }
        });*/
    }



    @BindingAdapter("app:items")
    public static void bindItem(RecyclerView recyclerView, ObservableArrayList<SellerGoodsViewModel> goodsList) {
        //= (GoodsListAdapter) recyclerView.getAdapter();
        if (recyclerView.getAdapter() == null) {
            adapter = new SellerGoodsListAdater(goodsList, context,sellerCommand);
            recyclerView.setAdapter(adapter);
        } else {
            // 있으면 getAdapter
            System.out.println("seller goods adapter=============");
            adapter = (SellerGoodsListAdater) recyclerView.getAdapter();
            adapter.setItem(goodsList);
        }
    }

    public void getImage(){
        imageUri = null;
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(getBaseContext(), "resultCode : " + resultCode + " requestCode : " + requestCode, Toast.LENGTH_SHORT).show();
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    //String name_Str = getImageNameToUri(data.getData());
                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    imageUri = data.getData();
                    filename = getImageNameToUri(data.getData());
                    //배치해놓은 ImageView에 set
                    sellerGoodsUpdateDialog.sellerGoodsUpdateDialogBinding.imageView.setImageBitmap(image_bitmap);
                    imagebool = true;
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getImageNameToUri(Uri data)
    {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgName;
    }

    private void checkPremission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if((ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE))||
                    (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA))){
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한 거부")
                        .setNegativeButton("설정",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i =new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.setData(Uri.parse("package"+getPackageName()));
                                startActivity(i);
                            }
                        })
                        .setPositiveButton("확인",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},PICK_FROM_GALLERY);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PICK_FROM_GALLERY:
                for(int i=0; i<grantResults.length;i++){
                    if(grantResults[i]<0){
                        Toast.makeText(SellerGoodsList.this,"해당 권한을 활성화 해야합니다.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
        }
    }

    public Uri getImageUri() {
        if(imagebool)
            return imageUri;
        else {
            return null;
        }
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}