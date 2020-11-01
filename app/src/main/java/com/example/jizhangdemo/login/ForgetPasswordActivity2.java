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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jizhangdemo.R;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class ForgetPasswordActivity2 extends AppCompatActivity {

    private EditText et_find_password_input_answer;
    private String username,answer;
    private TextView tv_question;
    private String[] date = new String[]{"你的父亲的名字","你的母亲的名字","你的生日"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password2);
        username = getIntent().getExtras().getString("username");
        Button mBtnFindPasswordConfirm = findViewById(R.id.btn_find_password_confirm);
        et_find_password_input_answer = findViewById(R.id.et_find_password_input_answer);
        tv_question = findViewById(R.id.tv_question);

        int problem_num = Find_password.Return_problem_num(ForgetPasswordActivity2.this,username);

        tv_question.setText(date[problem_num]);
        mBtnFindPasswordConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = et_find_password_input_answer.getText().toString().trim();
                if (TextUtils.isEmpty(answer)){
                    new MaterialDialog.Builder(ForgetPasswordActivity2.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("请输入密保问题答案")
                            .positiveText("确定")
                            .show();
                }
                else if(Find_password.Answer_is_correct(ForgetPasswordActivity2.this,username,answer) == 1){
                    new MaterialDialog.Builder(ForgetPasswordActivity2.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("密保问题答案错误")
                            .positiveText("确定")
                            .show();
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString("username",username);
                    Intent intent = new Intent(ForgetPasswordActivity2.this,ResetPasswordActivity.class).putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}