package com.example.jizhangdemo.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jizhangdemo.BottomBarActivity;
import com.example.jizhangdemo.R;
import com.example.jizhangdemo.UserManage;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class PasswordLoginActivity extends AppCompatActivity {

    private EditText et_password_login_username,et_password_login_password;
    private String username,password;
    private signin signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_login);
        Button mBtnBack = findViewById(R.id.btn_back);
        Button mBtnLogin = findViewById(R.id.btn_login);
        Button mBtnOtherLogin = findViewById(R.id.btn_other_login);
        Button mBtnForgetPassword = findViewById(R.id.btn_forget_password);
        et_password_login_username = findViewById(R.id.et_password_login_username);
        et_password_login_password = findViewById(R.id.et_password_login_password);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordLoginActivity.this.finish();
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_password_login_username.getText().toString().trim();
                password = et_password_login_password.getText().toString().trim();
                int error_code = signin.signin(PasswordLoginActivity.this,username,password);
                if(TextUtils.isEmpty(username)){
                    new MaterialDialog.Builder(PasswordLoginActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("请输入用户名")
                            .positiveText("确定")
                            .show();
                }else if(TextUtils.isEmpty(password)){
                    new MaterialDialog.Builder(PasswordLoginActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("请输入密码")
                            .positiveText("确定")
                            .show();
                }else if (error_code == 1){
                    new MaterialDialog.Builder(PasswordLoginActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("用户名不存在")
                            .positiveText("确定")
                            .show();
                }else if (error_code == 2){
                    new MaterialDialog.Builder(PasswordLoginActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("密码错误")
                            .positiveText("确定")
                            .show();
                } else {
                    Intent intent = new Intent(PasswordLoginActivity.this, BottomBarActivity.class);
                    UserManage.getInstance().saveUserInfo(PasswordLoginActivity.this,username,password);
                    Toast.makeText(PasswordLoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    PasswordLoginActivity.this.finish();
                }
            }
        });
        mBtnOtherLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordLoginActivity.this.finish();
            }
        });
        mBtnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordLoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}