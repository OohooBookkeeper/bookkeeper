package com.example.jizhangdemo.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.UserInfo;
import com.example.jizhangdemo.UserManage;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class VerifyPasswordActivity extends AppCompatActivity {

    private Button btn_next;
    private EditText et_password;
    private int flag;
    private String password,username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_password);
        btn_next = findViewById(R.id.btn_next);
        et_password = findViewById(R.id.et_password);
        flag = getIntent().getExtras().getInt("flag");
        UserInfo userInfo = UserManage.getInstance().getUserInfo(this);
        username = userInfo.getUserName();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = et_password.getText().toString().trim();
                if (TextUtils.isEmpty(password)){
                    new MaterialDialog.Builder(VerifyPasswordActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("输入密码为空")
                            .positiveText("确定")
                            .show();
                }else {
                    if (flag == 1){
                        Intent intent = new Intent(VerifyPasswordActivity.this,SetGraphPasswordActivity.class);
                        startActivity(intent);
                        finish();
                    }else if (flag == 2){
                        Intent intent = new Intent(VerifyPasswordActivity.this,ResetPassword2Activity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(VerifyPasswordActivity.this,ResetPasswordQuestionActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }
}