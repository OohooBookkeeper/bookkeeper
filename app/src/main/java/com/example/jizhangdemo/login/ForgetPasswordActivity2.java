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

import androidx.appcompat.app.AppCompatActivity;

import com.example.jizhangdemo.R;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class ForgetPasswordActivity2 extends AppCompatActivity {

    private EditText et_find_password_input_answer;
    private String answer,pos;
    private Spinner spinner_find_password_question;
    private String[] date = new String[]{"你的父亲的名字","你的母亲的名字","你的生日"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password2);
        Button mBtnFindPasswordConfirm = findViewById(R.id.btn_find_password_confirm);
        et_find_password_input_answer = findViewById(R.id.et_find_password_input_answer);
        spinner_find_password_question = findViewById(R.id.spinner_find_password_question);

        @SuppressLint("ResourceType") final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,date);
        spinner_find_password_question.setAdapter(adapter);
        spinner_find_password_question.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                }else {
                    Intent intent = new Intent(ForgetPasswordActivity2.this,ResetPasswordActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}