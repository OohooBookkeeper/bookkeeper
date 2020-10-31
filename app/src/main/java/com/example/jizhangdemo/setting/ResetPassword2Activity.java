package com.example.jizhangdemo.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.login.ResetPasswordActivity;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class ResetPassword2Activity extends AppCompatActivity {

    private EditText et_password,et_password_again;
    private String password,password_again;
    private Button btn_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password2);
        et_password = findViewById(R.id.et_reset_password);
        et_password_again = findViewById(R.id.et_reset_password_again);
        btn_finish = findViewById(R.id.btn_finish);

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = et_password.getText().toString().trim();
                password_again = et_password_again.getText().toString().trim();
                if(TextUtils.isEmpty(password)) {
                    new MaterialDialog.Builder(ResetPassword2Activity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("请输入密码")
                            .positiveText("确定")
                            .show();
                }else if (!TextUtils.equals(password,password_again)){
                    new MaterialDialog.Builder(ResetPassword2Activity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("两次输入密码不相同")
                            .positiveText("确定")
                            .show();
                }else {
                    finish();
                }
            }
        });
    }
}