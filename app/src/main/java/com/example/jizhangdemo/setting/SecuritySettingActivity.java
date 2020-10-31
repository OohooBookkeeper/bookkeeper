package com.example.jizhangdemo.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.jizhangdemo.R;

public class SecuritySettingActivity extends AppCompatActivity {

    private Button btn_set_graph_password,btn_reset_password,btn_reset_password_question;
    private ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_setting);
        btn_set_graph_password = findViewById(R.id.btn_set_graph_password);
        btn_reset_password = findViewById(R.id.btn_reset_password);
        btn_reset_password_question = findViewById(R.id.btn_reset_password_question);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_set_graph_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("flag",1);
                Intent intent = new Intent(SecuritySettingActivity.this,VerifyPasswordActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("flag",2);
                Intent intent = new Intent(SecuritySettingActivity.this,VerifyPasswordActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });
        btn_reset_password_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("flag",3);
                Intent intent = new Intent(SecuritySettingActivity.this,VerifyPasswordActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}