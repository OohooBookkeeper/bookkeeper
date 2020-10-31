package com.example.jizhangdemo.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jizhangdemo.BottomBarActivity;
import com.example.jizhangdemo.R;
import com.xuexiang.xui.utils.ResUtils;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

public class RegisterActivity extends AppCompatActivity {

    private EditText et_register_username,et_register_password,et_register_password_again,et_register_answer;
    private String username,password,password_again,answer;
    private int pos;
    private MaterialSpinner spinner_password_question;
    private String[] date = new String[]{"你的父亲的名字","你的母亲的名字","你的生日"};
    private signup signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Button mBtnBack2 = findViewById(R.id.btn_back2);
        Button mBtnRegister = findViewById(R.id.btn_register);
        et_register_username = findViewById(R.id.et_register_username);
        et_register_password = findViewById(R.id.et_register_password);
        et_register_password_again = findViewById(R.id.et_register_password_again);
        et_register_answer = findViewById(R.id.et_register_answer);
        spinner_password_question = findViewById(R.id.spinner_password_question);

        /*
        mBtnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginMainActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });
         */

        //spinner_password_question.setItems(ResUtils.getStringArray(R.array.password_question));
        spinner_password_question.setItems(date);
        spinner_password_question.setOnItemSelectedListener((spinner,position,id,item)->pos = position);


        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = et_register_username.getText().toString().trim();
                password = et_register_password.getText().toString().trim();
                password_again = et_register_password_again.getText().toString().trim();
                answer = et_register_answer.getText().toString().trim();
                int error_code = signup.signup(RegisterActivity.this,username,password,password_again,pos,answer);
                if(TextUtils.isEmpty(username)){
                    new MaterialDialog.Builder(RegisterActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("请输入用户名")
                            .positiveText("确定")
                            .show();
                }else if(TextUtils.isEmpty(password)){
                    new MaterialDialog.Builder(RegisterActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("请输入密码")
                            .positiveText("确定")
                            .show();
                }else if (!TextUtils.equals(password,password_again)){
                    new MaterialDialog.Builder(RegisterActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("请输入相同的密码")
                            .positiveText("确定")
                            .show();
                }else if (TextUtils.isEmpty(answer)){
                    new MaterialDialog.Builder(RegisterActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("请输入密保问题答案")
                            .positiveText("确定")
                            .show();
                }else if (error_code == 4){
                    new MaterialDialog.Builder(RegisterActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("输入密码不符合规范")
                            .positiveText("确定")
                            .show();
                }else if(error_code == 1){
                    new MaterialDialog.Builder(RegisterActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("输入用户名不符合规范")
                            .positiveText("确定")
                            .show();
                }else if(error_code == 2){
                    new MaterialDialog.Builder(RegisterActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("输入用户名已存在")
                            .positiveText("确定")
                            .show();
                } else{
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginMainActivity.class);
                    RegisterActivity.this.finish();
                    startActivity(intent);
                }
            }
        });
    }
}