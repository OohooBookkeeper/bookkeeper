package com.example.jizhangdemo.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jizhangdemo.BottomBarActivity;
import com.example.jizhangdemo.R;
import com.example.jizhangdemo.UserManage;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import java.util.prefs.Preferences;

public class LoginMainActivity extends AppCompatActivity {

    private EditText et_login_main_username;
    private String username;
    private Fast_signin fast_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UserManage.getInstance().hasUserInfo(this)){
            Intent intent = new Intent(LoginMainActivity.this,BottomBarActivity.class);
            startActivity(intent);
            LoginMainActivity.this.finish();
        }
        setContentView(R.layout.activity_login_main);
        Button mBtnGraphLogin = findViewById(R.id.btn_graph_login);
        Button mBtnPasswordLogin = findViewById(R.id.btn_password_login);
        Button mBtnRegister = findViewById(R.id.btn_choose_register);
        et_login_main_username = findViewById(R.id.et_login_main_username);
        mBtnGraphLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_login_main_username.getText().toString().trim();
                if (TextUtils.isEmpty(username)){
                    new MaterialDialog.Builder(LoginMainActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("请输入用户名")
                            .positiveText("确定")
                            .show();
                }else if(fast_signin.Username_is_ok(LoginMainActivity.this,username) == 1){
                    new MaterialDialog.Builder(LoginMainActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("用户名不存在")
                            .positiveText("确定")
                            .show();
                }else if(fast_signin.Username_is_ok(LoginMainActivity.this,username) == 2){
                    new MaterialDialog.Builder(LoginMainActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("图形密码不存在")
                            .positiveText("确定")
                            .show();
                } else {
                    Intent intent = new Intent(LoginMainActivity.this, GraphLoginActivity.class);
                    intent.putExtra("username",username);
                    startActivity(intent);
                }
            }
        });
        mBtnPasswordLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMainActivity.this,PasswordLoginActivity.class);
                startActivity(intent);
            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginMainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}