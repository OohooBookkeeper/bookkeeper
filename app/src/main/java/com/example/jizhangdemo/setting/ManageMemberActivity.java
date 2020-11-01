package com.example.jizhangdemo.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.UserManage;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnOptionsSelectListener;

public class ManageMemberActivity extends AppCompatActivity {
    private EditText et_member,et_edit_member;
    private Button btn_select_member,btn_delete_member,btn_confirm1,btn_confirm2,btn_confirm3;
    private OptionsPickerView pvOptions,pvOptions2;
    private String add_member,edit_member,username;
    private int edit_member_num,delete_member_num;
    private Setting_message setting_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_member);
        username = UserManage.getInstance().getUserInfo(this).getUserName();
        et_member = findViewById(R.id.et_member);
        et_edit_member = findViewById(R.id.et_edit_member);
        btn_select_member = findViewById(R.id.btn_select_member);
        btn_delete_member = findViewById(R.id.btn_delete_member);
        btn_confirm1 = findViewById(R.id.btn_confirm1);
        btn_confirm2 = findViewById(R.id.btn_confirm2);
        btn_confirm3 = findViewById(R.id.btn_confirm3);

        LoadData();

        OnClick onClick = new OnClick();
        setListener(onClick);
    }
    private void setListener(OnClick onClick){
        btn_select_member.setOnClickListener(onClick);
        btn_delete_member.setOnClickListener(onClick);
        btn_confirm1.setOnClickListener(onClick);
        btn_confirm2.setOnClickListener(onClick);
        btn_confirm3.setOnClickListener(onClick);
    }
    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_select_member:
                    pvOptions = new OptionsPickerBuilder(ManageMemberActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public boolean onOptionsSelect(View view, int options1, int options2, int options3) {
                            String st = setting_message.Member.get(options1);
                            edit_member_num = options1 + 1;
                            btn_select_member.setText(st);
                            Toast.makeText(ManageMemberActivity.this,"选中了:"+st,Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                            .build();
                    pvOptions.setPicker(setting_message.Member);
                    pvOptions.show();
                    break;
                case R.id.btn_delete_member:
                    pvOptions2 = new OptionsPickerBuilder(ManageMemberActivity.this, new OnOptionsSelectListener() {
                        @Override
                        public boolean onOptionsSelect(View view, int options1, int options2, int options3) {
                            String st = setting_message.Member.get(options1);
                            delete_member_num = options1 + 1;
                            btn_delete_member.setText(st);
                            Toast.makeText(ManageMemberActivity.this,"选中了:"+st,Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    })
                            .build();
                    pvOptions2.setPicker(setting_message.Member);
                    pvOptions2.show();
                    break;
                case R.id.btn_confirm1:
                    add_member = et_member.getText().toString().trim();
                    if (TextUtils.isEmpty(add_member)){
                        Toast.makeText(ManageMemberActivity.this,"未输入账户",Toast.LENGTH_SHORT).show();
                    }else {
                        int i = Bookkeeping_setting.Add_Member(ManageMemberActivity.this,username,add_member);
                        Toast.makeText(ManageMemberActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        LoadData();
                        finish();
                    }
                    break;
                case R.id.btn_confirm2:
                    edit_member = et_edit_member.getText().toString().trim();
                    if (TextUtils.isEmpty(edit_member)){
                        Toast.makeText(ManageMemberActivity.this,"未输入账户",Toast.LENGTH_SHORT).show();
                    }else if(edit_member_num < 1){
                        Toast.makeText(ManageMemberActivity.this,"未选中账户",Toast.LENGTH_SHORT).show();
                    }else {
                        int i = Bookkeeping_setting.Change_Member(ManageMemberActivity.this,username,edit_member_num,edit_member);
                        Toast.makeText(ManageMemberActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                        LoadData();
                        finish();
                    }
                    break;
                case R.id.btn_confirm3:
                    if(delete_member_num < 1){
                        Toast.makeText(ManageMemberActivity.this,"未选中账户",Toast.LENGTH_SHORT).show();
                    }else {
                        int i = Bookkeeping_setting.Delete_Member(ManageMemberActivity.this,username,delete_member_num);
                        Toast.makeText(ManageMemberActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                        LoadData();
                        finish();
                    }
                    break;
            }
        }
    }
    private void LoadData(){
        setting_message = Bookkeeping_setting.Return_setting_message(this,username);
        btn_select_member.setText("选择成员");
        btn_delete_member.setText("选择成员");
        edit_member_num= 0;
        delete_member_num = 0;
    }
}