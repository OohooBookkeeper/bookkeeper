package com.example.jizhangdemo.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.UserManage;
import com.example.jizhangdemo.add.FirstCategory;
import com.example.jizhangdemo.add.Message;
import com.example.jizhangdemo.add.Return_message;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnOptionsSelectListener;

import java.util.ArrayList;
import java.util.List;

public class ManageInCategoryActivity extends AppCompatActivity {

    private Button btn_confirm1,btn_confirm2,btn_confirm3,btn_confirm4,btn_confirm5,btn_confirm6,btn_select_category,
            btn_delete_category, btn_delete_subcategory,btn_select_category_edit,btn_select_category_edit2,btn_select_subcategory_edit;
    private EditText et_category,et_subcategory,et_edit_category,et_edit_subcategory;
    private String category,subcategory,edit_category,edit_subcategory,username;
    private OptionsPickerView pvOptions,pvOptions2,pvOptions3,pvOptions4,pvOptions5,pvOptions6;
    private List<FirstCategory> options1Items = new ArrayList<>();
    private List<List<String>> options2Items = new ArrayList<>();
    private Message message;
    private Setting_message setting_message;
    private int add_subcategory,delete_category,delete_select_category,delete_subcategory,edit_category_num,
            edit_select_category_num,edit_subcategory_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_in_category);
        username = UserManage.getInstance().getUserInfo(this).getUserName();
        btn_confirm1 = findViewById(R.id.btn_confirm1);
        btn_confirm2 = findViewById(R.id.btn_confirm2);
        btn_confirm3 = findViewById(R.id.btn_confirm3);
        btn_confirm4 = findViewById(R.id.btn_confirm4);
        btn_confirm5 = findViewById(R.id.btn_confirm5);
        btn_confirm6 = findViewById(R.id.btn_confirm6);
        btn_select_category = findViewById(R.id.btn_select_category);
        btn_delete_category = findViewById(R.id.btn_delete_category);
        btn_delete_subcategory = findViewById(R.id.btn_delete_subcategory);
        btn_select_category_edit = findViewById(R.id.btn_select_category_edit);
        btn_select_subcategory_edit = findViewById(R.id.btn_select_subcategory_edit);
        et_category = findViewById(R.id.et_category);
        et_subcategory = findViewById(R.id.et_subcategory);
        et_edit_category = findViewById(R.id.et_edit_category);
        et_edit_subcategory = findViewById(R.id.et_edit_subcategory);

        LoadData();

        OnClick onClick = new OnClick();
        setListener(onClick);
    }

    private void setListener(OnClick onClick){
        btn_confirm1.setOnClickListener(onClick);
        btn_confirm2.setOnClickListener(onClick);
        btn_confirm3.setOnClickListener(onClick);
        btn_confirm4.setOnClickListener(onClick);
        btn_confirm5.setOnClickListener(onClick);
        btn_confirm6.setOnClickListener(onClick);
        btn_select_category.setOnClickListener(onClick);
        btn_delete_category.setOnClickListener(onClick);
        btn_delete_subcategory.setOnClickListener(onClick);
        btn_select_category_edit.setOnClickListener(onClick);
        btn_select_subcategory_edit.setOnClickListener(onClick);
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
                    if (TextUtils.isEmpty(category)){
                        Toast.makeText(ManageInCategoryActivity.this,"未输入分类名",Toast.LENGTH_SHORT).show();
                    }
                    else if (Bookkeeping_setting.Add_Category(ManageInCategoryActivity.this,username,1,category) == 0){
                        Toast.makeText(ManageInCategoryActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        LoadData();
                        finish();
                    }
                    break;
                case R.id.btn_confirm2:
                    subcategory = et_subcategory.getText().toString().trim();
                    if (TextUtils.isEmpty(subcategory)){
                        Toast.makeText(ManageInCategoryActivity.this,"未输入分类名",Toast.LENGTH_SHORT).show();
                    }else if (add_subcategory < 1){
                        Toast.makeText(ManageInCategoryActivity.this,"未选择分类",Toast.LENGTH_SHORT).show();
                    }
                    else if (Bookkeeping_setting.Add_income_Subcategory(ManageInCategoryActivity.this,username,add_subcategory,subcategory) == 0){
                        Toast.makeText(ManageInCategoryActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        LoadData();
                        finish();
                    }
                    break;
                case R.id.btn_confirm3:
                    if (delete_category < 1){
                        Toast.makeText(ManageInCategoryActivity.this,"未选择分类",Toast.LENGTH_SHORT).show();
                    }else if (Bookkeeping_setting.Delete_income_Category(ManageInCategoryActivity.this,username,delete_category) == 0){
                        Toast.makeText(ManageInCategoryActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        LoadData();
                        finish();
                    }
                    break;
                case R.id.btn_confirm4:
                    if (delete_subcategory < 1){
                        Toast.makeText(ManageInCategoryActivity.this,"未选择分类",Toast.LENGTH_SHORT).show();
                    }
                    else if (Bookkeeping_setting.Delete_income_Subcategory(ManageInCategoryActivity.this,username,delete_subcategory,delete_select_category) == 0){
                        Toast.makeText(ManageInCategoryActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        LoadData();
                        finish();
                    }
                    break;
                case R.id.btn_confirm5:
                    edit_category = et_edit_category.getText().toString().trim();
                    if (TextUtils.isEmpty(edit_category)){
                        Toast.makeText(ManageInCategoryActivity.this,"未输入分类名",Toast.LENGTH_SHORT).show();
                    }else if (edit_category_num < 1){
                        Toast.makeText(ManageInCategoryActivity.this,"未选择分类",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int i = Bookkeeping_setting.Change_Income_Category(ManageInCategoryActivity.this,username,edit_category_num,1,edit_category);
                        Toast.makeText(ManageInCategoryActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                        LoadData();
                        finish();
                    }
                    break;
                case R.id.btn_confirm6:
                    edit_subcategory = et_edit_subcategory.getText().toString().trim();
                    if (TextUtils.isEmpty(edit_subcategory)){
                        Toast.makeText(ManageInCategoryActivity.this,"未输入分类名",Toast.LENGTH_SHORT).show();
                    }else if (edit_subcategory_num < 1){
                        Toast.makeText(ManageInCategoryActivity.this,"未选择分类",Toast.LENGTH_SHORT).show();
                    }
                    else if (Bookkeeping_setting.Change_income_Subcategory(ManageInCategoryActivity.this,username,edit_subcategory_num,edit_select_category_num,edit_subcategory) == 0){
                        Toast.makeText(ManageInCategoryActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                        LoadData();
                        finish();
                    }
                    break;
                case R.id.btn_select_category:
                    pvOptions = new OptionsPickerBuilder(ManageInCategoryActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public boolean onOptionsSelect(View view, int options1, int options2, int options3) {
                            String st = options1Items.get(options1).name;
                            add_subcategory = options1 + 1;
                            btn_select_category.setText(st);
                            Toast.makeText(ManageInCategoryActivity.this,"选中了:"+st,Toast.LENGTH_SHORT).show();
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
                            String st = options1Items.get(options1).name;
                            delete_category = options1 + 1;
                            btn_delete_category.setText(st);
                            Toast.makeText(ManageInCategoryActivity.this,"选中了:"+st,Toast.LENGTH_SHORT).show();
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
                            String st;
                            if (options2Items.get(options1).isEmpty()){
                                st = options1Items.get(options1).getPickerViewText();
                            }else {
                                st = options1Items.get(options1).getPickerViewText() +"-" +
                                        options2Items.get(options1).get(options2);
                            }
                            delete_select_category = options1 + 1;
                            delete_subcategory = options2 + 1;
                            btn_delete_subcategory.setText(st);
                            Toast.makeText(ManageInCategoryActivity.this,"选中了:"+st,Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                            .build();
                    pvOptions3.setPicker(options1Items,options2Items);
                    pvOptions3.show();
                    break;
                case R.id.btn_select_category_edit:
                    pvOptions4 = new OptionsPickerBuilder(ManageInCategoryActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public boolean onOptionsSelect(View view, int options1, int options2, int options3) {
                            String st = options1Items.get(options1).name;
                            edit_category_num = options1 + 1;
                            btn_select_category_edit.setText(st);
                            Toast.makeText(ManageInCategoryActivity.this,"选中了:"+st,Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                            .build();
                    pvOptions4.setPicker(options1Items);
                    pvOptions4.show();
                    break;
                case R.id.btn_select_subcategory_edit:
                    pvOptions5 = new OptionsPickerBuilder(ManageInCategoryActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public boolean onOptionsSelect(View view, int options1, int options2, int options3) {
                            String st;
                            if (options2Items.get(options1).get(options2) == null){
                                st = options1Items.get(options1).getPickerViewText();
                            }else {
                                st = options1Items.get(options1).getPickerViewText() +"-" +
                                        options2Items.get(options1).get(options2);
                            }
                            edit_select_category_num = options1 + 1;
                            edit_subcategory_num = options2 + 1;
                            btn_select_subcategory_edit.setText(st);
                            Toast.makeText(ManageInCategoryActivity.this,"选中了:"+st,Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                            .build();
                    pvOptions5.setPicker(options1Items,options2Items);
                    pvOptions5.show();
                    break;
            }
        }
    }
    private void LoadData(){
        message = Return_message.Return(ManageInCategoryActivity.this,1,username);
        options1Items = message.All_category;
        for (FirstCategory firstCategory : message.All_category){
            //options1Item.add(firstCategory.getName());
            List<String> secondCategory = new ArrayList<>();
            secondCategory.addAll(firstCategory.getCategory());
            options2Items.add(secondCategory);
        }
        btn_select_category.setText("选择一级分类");
        btn_delete_category.setText("选择一级分类");
        btn_delete_subcategory.setText("选择二级分类");
        btn_select_category_edit.setText("选择一级分类");
        btn_select_subcategory_edit.setText("选择二级分类");
        et_category.setText("");
        et_subcategory.setText("");
        et_edit_category.setText("");
        et_edit_subcategory.setText("");
        add_subcategory = 0;
        delete_category = 0;
        delete_select_category = 0;
        delete_subcategory = 0;
        edit_category_num = 0;
        edit_select_category_num = 0;
        edit_subcategory_num = 0;
    }
}