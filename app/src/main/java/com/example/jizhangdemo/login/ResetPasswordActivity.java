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

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText et_reset_password;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        Button mBtnResetPasswordFinish = findViewById(R.id.btn_reset_password_finish);
        et_reset_password = findViewById(R.id.et_reset_password);

        mBtnResetPasswordFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = et_reset_password.getText().toString().trim();
                if(TextUtils.isEmpty(password)){
                    new MaterialDialog.Builder(ResetPasswordActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("请输入密码")
                            .positiveText("确定")
                            .show();
                }else {
                    Intent intent = new Intent(ResetPasswordActivity.this,LoginMainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}