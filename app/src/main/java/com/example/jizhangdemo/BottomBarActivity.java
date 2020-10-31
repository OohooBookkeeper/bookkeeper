package com.example.jizhangdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jizhangdemo.add.KeepAccountActivity;
import com.example.jizhangdemo.fragment.HomePageActivity;
import com.example.jizhangdemo.fragment.JournalAccountActivity;
import com.example.jizhangdemo.fragment.SettingActivity;
import com.example.jizhangdemo.fragment.StatisticsActivity;

public class BottomBarActivity extends AppCompatActivity {

    private TextView bottom_bar_tv_1, bottom_bar_tv_2, bottom_bar_tv_3, bottom_bar_tv_4, bottom_bar_tv_5;
    private ImageView bottom_bar_iv_1, bottom_bar_iv_2, bottom_bar_iv_3, bottom_bar_iv_4, bottom_bar_iv_5;
    private RelativeLayout bottom_bar_1_btn, bottom_bar_2_btn, bottom_bar_3_btn, bottom_bar_4_btn, bottom_bar_5_btn;
    private HomePageActivity fragment1;
    private JournalAccountActivity fragment2;
    private StatisticsActivity fragment4;
    private SettingActivity fragment5;

    public void setSelectStatus(int index) {
        switch (index) {
            case 0:
                bottom_bar_iv_1.setImageResource(R.drawable.nav_01_pre);
                bottom_bar_iv_2.setImageResource(R.drawable.nav_02_nor);
                bottom_bar_iv_4.setImageResource(R.drawable.nav_04_nor);
                bottom_bar_iv_5.setImageResource(R.drawable.nav_05_nor);

                bottom_bar_tv_1.setTextColor(Color.parseColor("#0097F7"));
                bottom_bar_tv_2.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_4.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_5.setTextColor(Color.parseColor("#000000"));
                break;
            case 1:
                bottom_bar_iv_1.setImageResource(R.drawable.nav_01_nor);
                bottom_bar_iv_2.setImageResource(R.drawable.nav_02_pre);
                bottom_bar_iv_4.setImageResource(R.drawable.nav_04_nor);
                bottom_bar_iv_5.setImageResource(R.drawable.nav_05_nor);

                bottom_bar_tv_1.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_2.setTextColor(Color.parseColor("#0097F7"));
                bottom_bar_tv_4.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_5.setTextColor(Color.parseColor("#000000"));
                break;
            case 3:
                bottom_bar_iv_1.setImageResource(R.drawable.nav_01_nor);
                bottom_bar_iv_2.setImageResource(R.drawable.nav_02_nor);
                bottom_bar_iv_4.setImageResource(R.drawable.nav_04_pre);
                bottom_bar_iv_5.setImageResource(R.drawable.nav_05_nor);

                bottom_bar_tv_1.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_2.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_4.setTextColor(Color.parseColor("#0097F7"));
                bottom_bar_tv_5.setTextColor(Color.parseColor("#000000"));
                break;
            case 4:
                bottom_bar_iv_1.setImageResource(R.drawable.nav_01_nor);
                bottom_bar_iv_2.setImageResource(R.drawable.nav_02_nor);
                bottom_bar_iv_4.setImageResource(R.drawable.nav_04_nor);
                bottom_bar_iv_5.setImageResource(R.drawable.nav_05_pre);

                bottom_bar_tv_1.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_2.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_4.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_5.setTextColor(Color.parseColor("#0097F7"));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        UserInfo userInfo = UserManage.getInstance().getUserInfo(this);
        String username = userInfo.getUserName();
        Bundle bundle = new Bundle();
        bundle.putString("username", username);

        bottom_bar_tv_1 = findViewById(R.id.bottom_bar_tv_1);
        bottom_bar_tv_2 = findViewById(R.id.bottom_bar_tv_2);
        bottom_bar_tv_4 = findViewById(R.id.bottom_bar_tv_4);
        bottom_bar_tv_5 = findViewById(R.id.bottom_bar_tv_5);

        bottom_bar_iv_1 = findViewById(R.id.bottom_bar_iv_1);
        bottom_bar_iv_2 = findViewById(R.id.bottom_bar_iv_2);
        bottom_bar_iv_4 = findViewById(R.id.bottom_bar_iv_4);
        bottom_bar_iv_5 = findViewById(R.id.bottom_bar_iv_5);

        bottom_bar_1_btn = findViewById(R.id.bottom_bar_1_btn);
        bottom_bar_2_btn = findViewById(R.id.bottom_bar_2_btn);
        bottom_bar_4_btn = findViewById(R.id.bottom_bar_4_btn);
        bottom_bar_5_btn = findViewById(R.id.bottom_bar_5_btn);

        fragment1 = new HomePageActivity();
        fragment2 = new JournalAccountActivity();
        fragment4 = new StatisticsActivity();
        fragment5 = new SettingActivity();

        fragment1.setArguments(bundle);
        fragment2.setArguments(bundle);
        fragment4.setArguments(bundle);
        fragment5.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, fragment1).commitAllowingStateLoss();
        setSelectStatus(0);

        OnClick onClick = new OnClick();
        bottom_bar_1_btn.setOnClickListener(onClick);
        bottom_bar_2_btn.setOnClickListener(onClick);
        bottom_bar_4_btn.setOnClickListener(onClick);
        bottom_bar_5_btn.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bottom_bar_1_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment1).commitAllowingStateLoss();
                    setSelectStatus(0);
                    break;
                case R.id.bottom_bar_2_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment2).commitAllowingStateLoss();
                    setSelectStatus(1);
                    break;
                case R.id.bottom_bar_4_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment4).commitAllowingStateLoss();
                    setSelectStatus(3);
                    break;
                case R.id.bottom_bar_5_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment5).commitAllowingStateLoss();
                    setSelectStatus(4);
                    break;
            }
        }
    }
}