package com.example.jizhangdemo.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.add.FirstCategory;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnOptionsSelectListener;

import java.util.ArrayList;
import java.util.List;

public class ManageInCategoryActivity extends AppCompatActivity {

    private ImageButton btn_back;
    private Button btn_confirm1,btn_confirm2,btn_confirm3,btn_confirm4,btn_select_category,btn_delete_category,btn_delete_subcategory;
    private EditText et_category,et_subcategory;
    private String category,subcategory;
    private OptionsPickerView pvOptions,pvOptions2,pvOptions3;
    private List<FirstCategory> options1Items = new ArrayList<>();
    private List<List<String>> options2Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_in_category);
        btn_back = findViewById(R.id.btn_back);
        btn_confirm1 = findViewById(R.id.btn_confirm1);
        btn_confirm2 = findViewById(R.id.btn_confirm2);
        btn_confirm3 = findViewById(R.id.btn_confirm3);
        btn_confirm4 = findViewById(R.id.btn_confirm4);
        btn_select_category = findViewById(R.id.btn_select_category);
        btn_delete_category = findViewById(R.id.btn_delete_category);
        btn_delete_subcategory = findViewById(R.id.btn_delete_subcategory);
        et_category = findViewById(R.id.et_category);
        et_subcategory = findViewById(R.id.et_subcategory);

        OnClick onClick = new OnClick();
        setListener(onClick);
    }

    private void setListener(OnClick onClick){
        btn_back.setOnClickListener(onClick);
        btn_confirm1.setOnClickListener(onClick);
        btn_confirm2.setOnClickListener(onClick);
        btn_confirm3.setOnClickListener(onClick);
        btn_confirm4.setOnClickListener(onClick);
        btn_select_category.setOnClickListener(onClick);
        btn_delete_category.setOnClickListener(onClick);
        btn_delete_subcategory.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_back:
                    finish();
                    break;
                case R.id.btn_confirm1:
                    category = et_category.getText().toString().trim();
                    break;
                case R.id.btn_confirm2:
                    subcategory = et_subcategory.getText().toString().trim();
                    break;
                case R.id.btn_confirm3:
                    break;
                case R.id.btn_confirm4:
                    break;
                case R.id.btn_select_category:
                    pvOptions = new OptionsPickerBuilder(ManageInCategoryActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public boolean onOptionsSelect(View view, int options1, int options2, int options3) {
                            return false;
                        }
                    })
                            .build();
                    pvOptions.setPicker(options1Items);
                    pvOptions.show();
                    break;
                case R.id.btn_delete_category:
                    pvOptions2 = new OptionsPickerBuilder(ManageInCategoryActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public boolean onOptionsSelect(View view, int options1, int options2, int options3) {
                            return false;
                        }
                    })
                            .build();
                    pvOptions2.setPicker(options1Items);
                    pvOptions2.show();
                    break;
                case R.id.btn_delete_subcategory:
                    pvOptions3 = new OptionsPickerBuilder(ManageInCategoryActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public boolean onOptionsSelect(View view, int options1, int options2, int options3) {
                            return false;
                        }
                    })
                            .build();
                    pvOptions3.setPicker(options1Items,options2Items);
                    pvOptions3.show();
                    break;
            }
        }
    }
}