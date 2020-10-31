package com.example.jizhangdemo.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jizhangdemo.R;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class ForgetPasswordActivity extends AppCompatActivity {

    private EditText et_find_password_input_username;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Button mBtnFindPasswordNext = findViewById(R.id.btn_find_password_next);
        et_find_password_input_username = findViewById(R.id.et_find_password_input_username);

        mBtnFindPasswordNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_find_password_input_username.getText().toString().trim();
                if(TextUtils.isEmpty(username)){
                    new MaterialDialog.Builder(ForgetPasswordActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("请输入用户名")
                            .positiveText("确定")
                            .show();
                }else {
                    Intent intent = new Intent(ForgetPasswordActivity.this,ForgetPasswordActivity2.class);
                    startActivity(intent);
                }
            }
        });
    }

}