package com.example.jizhangdemo.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jizhangdemo.R;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText et_reset_password,et_reset_password_again;
    private String password,password_again,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        username = getIntent().getExtras().getString("username");
        Button mBtnResetPasswordFinish = findViewById(R.id.btn_reset_password_finish);
        et_reset_password = findViewById(R.id.et_reset_password);
        et_reset_password_again = findViewById(R.id.et_reset_password_again);

        mBtnResetPasswordFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = et_reset_password.getText().toString().trim();
                password_again = et_reset_password_again.getText().toString().trim();
                if(TextUtils.isEmpty(password)){
                    new MaterialDialog.Builder(ResetPasswordActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("请输入密码")
                            .positiveText("确定")
                            .show();
                } else if (!TextUtils.equals(password,password_again)){
                    new MaterialDialog.Builder(ResetPasswordActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("两次输入密码不同")
                            .positiveText("确定")
                            .show();
                }
                else if(Find_password.Change_password(ResetPasswordActivity.this,username,password,password_again) == 2){
                    new MaterialDialog.Builder(ResetPasswordActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("密码不符合规范")
                            .positiveText("确定")
                            .show();
                }
                else {
                    Toast.makeText(ResetPasswordActivity.this,"设置成功",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ResetPasswordActivity.this,LoginMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
