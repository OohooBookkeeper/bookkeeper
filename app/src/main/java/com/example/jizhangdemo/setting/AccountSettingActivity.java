package com.example.jizhangdemo.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.jizhangdemo.R;

public class AccountSettingActivity extends AppCompatActivity {

    private ImageButton btn_back;
    private Button btn_manage_inCategory,btn_manage_outCategory,btn_manage_account,btn_manage_member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        btn_back = findViewById(R.id.btn_back);
        btn_manage_inCategory = findViewById(R.id.btn_manage_inCategory);
        btn_manage_outCategory = findViewById(R.id.btn_manage_outCategory);
        btn_manage_account = findViewById(R.id.btn_manage_account);
        btn_manage_member = findViewById(R.id.btn_manage_member);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_manage_inCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSettingActivity.this,ManageInCategoryActivity.class);
                startActivity(intent);
            }
        });

        btn_manage_outCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSettingActivity.this,ManageOutCategoryActivity.class);
                startActivity(intent);
            }
        });

        btn_manage_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSettingActivity.this,ManageAccountActivity.class);
                startActivity(intent);
            }
        });

        btn_manage_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSettingActivity.this,ManageMemberActivity.class);
                startActivity(intent);
            }
        });
    }
}