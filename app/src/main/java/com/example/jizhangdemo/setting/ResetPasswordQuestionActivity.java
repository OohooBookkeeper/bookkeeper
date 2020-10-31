package com.example.jizhangdemo.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jizhangdemo.R;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

public class ResetPasswordQuestionActivity extends AppCompatActivity {

    private ImageButton btn_back;
    private Button btn_finish;
    private EditText et_answer;
    private TextView question;
    private String answer;
    private String[] data = new String[]{"你的父亲的名字","你的母亲的名字","你的生日"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_question);
        btn_back = findViewById(R.id.btn_back);
        btn_finish = findViewById(R.id.btn_finish);
        et_answer = findViewById(R.id.et_answer);
        question = findViewById(R.id.tv_question);

        question.setText(data[0]);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer = et_answer.getText().toString().trim();
                if (TextUtils.isEmpty(answer)){
                    new MaterialDialog.Builder(ResetPasswordQuestionActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("输入答案为空")
                            .positiveText("确定")
                            .show();
                }else {
                    finish();
                }
            }
        });
    }
}