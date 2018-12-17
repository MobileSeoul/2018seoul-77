package com.example.skuniv.fleamarket2.view.categoryView;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.databinding.ActivityCategoryBinding;

import java.util.HashMap;
import java.util.Map;


public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding categoryBinding;
    static private Map<String,String> map = new HashMap<String, String>() {
        {
            put("CLOTH", "옷");
            put("DIGITAL", "디지털");
            put("FANCY", "잡화");
            put("ETC", "기타");
            put("ACC", "악세서리");
            put("BOOK", "도서");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryBinding = DataBindingUtil.setContentView(this,R.layout.activity_category);
        categoryBinding.setCategory(this);

    }

    public void categoryClickListener(String category){
        Intent intent = new Intent(getApplicationContext(),CategoryListActivity.class);
        intent.putExtra("category",map.get(category));
        startActivity(intent);
    }
}

