package com.example.jizhangdemo.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.UserManage;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnOptionsSelectListener;

public class ManageAccountActivity extends AppCompatActivity {

    private EditText et_account,et_edit_account;
    private Button btn_select_account,btn_delete_account,btn_confirm1,btn_confirm2,btn_confirm3;
    private OptionsPickerView pvOptions,pvOptions2;
    private String add_account,edit_account,username;
    private int edit_account_num,delete_account_num;
    private Setting_message setting_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        username = UserManage.getInstance().getUserInfo(this).getUserName();
        et_account = findViewById(R.id.et_account);
        et_edit_account = findViewById(R.id.et_edit_account);
        btn_select_account = findViewById(R.id.btn_select_account);
        btn_delete_account = findViewById(R.id.btn_delete_account);
        btn_confirm1 = findViewById(R.id.btn_confirm1);
        btn_confirm2 = findViewById(R.id.btn_confirm2);
        btn_confirm3 = findViewById(R.id.btn_confirm3);

        LoadData();

        OnClick onClick = new OnClick();
        setListener(onClick);
    }
    private void setListener(OnClick onClick){
        btn_select_account.setOnClickListener(onClick);
        btn_delete_account.setOnClickListener(onClick);
        btn_confirm1.setOnClickListener(onClick);
        btn_confirm2.setOnClickListener(onClick);
        btn_confirm3.setOnClickListener(onClick);
    }
    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_select_account:
                    pvOptions = new OptionsPickerBuilder(ManageAccountActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public boolean onOptionsSelect(View view, int options1, int options2, int options3) {
                            String st = setting_message.Account.get(options1);
                            edit_account_num = options1 + 1;
                            btn_select_account.setText(st);
                            Toast.makeText(ManageAccountActivity.this,"选中了:"+st,Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                            .build();
                    pvOptions.setPicker(setting_message.Account);
                    pvOptions.show();
                    break;
                case R.id.btn_delete_account:
                    pvOptions2 = new OptionsPickerBuilder(ManageAccountActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public boolean onOptionsSelect(View view, int options1, int options2, int options3) {
                            String st = setting_message.Account.get(options1);
                            delete_account_num = options1 + 1;
                            btn_delete_account.setText(st);
                            Toast.makeText(ManageAccountActivity.this,"选中了:"+st,Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                            .build();
                    pvOptions2.setPicker(setting_message.Account);
                    pvOptions2.show();
                    break;
                case R.id.btn_confirm1:
                    add_account = et_account.getText().toString().trim();
                    if (TextUtils.isEmpty(add_account)){
                        Toast.makeText(ManageAccountActivity.this,"未输入账户",Toast.LENGTH_SHORT).show();
                    }else {
                        int i = Bookkeeping_setting.Add_Account(ManageAccountActivity.this,username,add_account);
                        Toast.makeText(ManageAccountActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        LoadData();
                        finish();
                    }
                    break;
                case R.id.btn_confirm2:
                    edit_account = et_edit_account.getText().toString().trim();
                    if (TextUtils.isEmpty(edit_account)){
                        Toast.makeText(ManageAccountActivity.this,"未输入账户",Toast.LENGTH_SHORT).show();
                    }else if(edit_account_num < 1){
                        Toast.makeText(ManageAccountActivity.this,"未选中账户",Toast.LENGTH_SHORT).show();
                    }else {
                        int i = Bookkeeping_setting.Change_Account(ManageAccountActivity.this,username,edit_account_num,edit_account);
                        Toast.makeText(ManageAccountActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                        LoadData();
                        finish();
                    }
                    break;
                case R.id.btn_confirm3:
                    if(delete_account_num < 1){
                        Toast.makeText(ManageAccountActivity.this,"未选中账户",Toast.LENGTH_SHORT).show();
                    }else {
                        int i = Bookkeeping_setting.Delete_Account(ManageAccountActivity.this,username,delete_account_num);
                        Toast.makeText(ManageAccountActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        LoadData();
                        finish();
                    }
                    break;
            }
        }
    }
    private void LoadData(){
        setting_message = Bookkeeping_setting.Return_setting_message(this,username);
        btn_select_account.setText("选择账户");
        btn_delete_account.setText("选择账户");
        edit_account_num = 0;
        delete_account_num = 0;
    }
}