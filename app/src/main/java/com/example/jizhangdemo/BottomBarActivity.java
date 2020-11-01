package com.example.jizhangdemo;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.jizhangdemo.add.KeepAccountActivity;
import com.example.jizhangdemo.fragment.HomePageActivity;
import com.example.jizhangdemo.fragment.JournalAccountActivity;
import com.example.jizhangdemo.fragment.SettingActivity;
import com.example.jizhangdemo.fragment.StatisticsActivity;
import com.example.jizhangdemo.login.LoginMainActivity;
import com.example.jizhangdemo.setting.AboutUsActivity;
import com.example.jizhangdemo.setting.ManageAccountActivity;
import com.example.jizhangdemo.setting.ManageInCategoryActivity;
import com.example.jizhangdemo.setting.ManageMemberActivity;
import com.example.jizhangdemo.setting.ManageOutCategoryActivity;
import com.example.jizhangdemo.setting.VerifyPasswordActivity;
import com.google.android.material.navigation.NavigationView;

public class BottomBarActivity extends AppCompatActivity {

    private TextView bottom_bar_tv_1, bottom_bar_tv_2, bottom_bar_tv_4;
    private ImageView bottom_bar_iv_1, bottom_bar_iv_2, bottom_bar_iv_4;
    private RelativeLayout bottom_bar_1_btn, bottom_bar_2_btn, bottom_bar_4_btn;
    private HomePageActivity fragment1;
    private JournalAccountActivity fragment2;
    private StatisticsActivity fragment4;
    private SettingActivity fragment5;
    private NavigationView navView;
    private Toolbar tb;
    private DrawerLayout drawer;

    public void setSelectStatus(int index) {
        switch (index) {
            case 0:
                bottom_bar_iv_1.setImageResource(R.drawable.nav_01_pre);
                bottom_bar_iv_2.setImageResource(R.drawable.nav_02_nor);
                bottom_bar_iv_4.setImageResource(R.drawable.nav_04_nor);

                bottom_bar_tv_1.setTextColor(Color.parseColor("#0097F7"));
                bottom_bar_tv_2.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_4.setTextColor(Color.parseColor("#000000"));
                break;
            case 1:
                bottom_bar_iv_1.setImageResource(R.drawable.nav_01_nor);
                bottom_bar_iv_2.setImageResource(R.drawable.nav_02_pre);
                bottom_bar_iv_4.setImageResource(R.drawable.nav_04_nor);

                bottom_bar_tv_1.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_2.setTextColor(Color.parseColor("#0097F7"));
                bottom_bar_tv_4.setTextColor(Color.parseColor("#000000"));
                break;
            case 3:
                bottom_bar_iv_1.setImageResource(R.drawable.nav_01_nor);
                bottom_bar_iv_2.setImageResource(R.drawable.nav_02_nor);
                bottom_bar_iv_4.setImageResource(R.drawable.nav_04_pre);

                bottom_bar_tv_1.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_2.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_4.setTextColor(Color.parseColor("#0097F7"));
                break;
            case 4:
                bottom_bar_iv_1.setImageResource(R.drawable.nav_01_nor);
                bottom_bar_iv_2.setImageResource(R.drawable.nav_02_nor);
                bottom_bar_iv_4.setImageResource(R.drawable.nav_04_nor);

                bottom_bar_tv_1.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_2.setTextColor(Color.parseColor("#000000"));
                bottom_bar_tv_4.setTextColor(Color.parseColor("#000000"));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment current = getSupportFragmentManager().findFragmentById(R.id.fl_container);
        if (drawer.isDrawerOpen(Gravity.LEFT))
            drawer.closeDrawer(Gravity.LEFT);
        else if (current != null && !(current instanceof HomePageActivity)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment1).commitAllowingStateLoss();
            setSelectStatus(0);
        }
        else
            super.onBackPressed();
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

        bottom_bar_iv_1 = findViewById(R.id.bottom_bar_iv_1);
        bottom_bar_iv_2 = findViewById(R.id.bottom_bar_iv_2);
        bottom_bar_iv_4 = findViewById(R.id.bottom_bar_iv_4);

        bottom_bar_1_btn = findViewById(R.id.bottom_bar_1_btn);
        bottom_bar_2_btn = findViewById(R.id.bottom_bar_2_btn);
        bottom_bar_4_btn = findViewById(R.id.bottom_bar_4_btn);

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
        drawer = findViewById(R.id.drawer_home);
        tb = findViewById(R.id.tb_home);
        tb.setTitle("");
        setSupportActionBar(tb);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        navView = findViewById(R.id.navigation);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent t;
                Bundle bundle;
                switch (item.getItemId()) {
                    case R.id.ptnS:
                        bundle = new Bundle();
                        bundle.putInt("flag",1);
                        t = new Intent(BottomBarActivity.this, VerifyPasswordActivity.class).putExtras(bundle);
                        break;
                    case R.id.chgP:
                        bundle = new Bundle();
                        bundle.putInt("flag",2);
                        t = new Intent(BottomBarActivity.this, VerifyPasswordActivity.class).putExtras(bundle);
                        break;
                    case R.id.chgSP:
                        bundle = new Bundle();
                        bundle.putInt("flag",3);
                        t = new Intent(BottomBarActivity.this, VerifyPasswordActivity.class).putExtras(bundle);
                        break;
                    case R.id.inCS:
                        t = new Intent(BottomBarActivity.this, ManageInCategoryActivity.class);
                        break;
                    case R.id.exCS:
                        t = new Intent(BottomBarActivity.this, ManageOutCategoryActivity.class);
                        break;
                    case R.id.accS:
                        t = new Intent(BottomBarActivity.this, ManageAccountActivity.class);
                        break;
                    case R.id.memS:
                        t = new Intent(BottomBarActivity.this, ManageMemberActivity.class);
                        break;
                    case R.id.signOut:
                        UserManage.getInstance().deleteUserInfo(BottomBarActivity.this);
                        t = new Intent(BottomBarActivity.this, LoginMainActivity.class);
                        BottomBarActivity.this.finish();
                        break;
                    case R.id.AboutUs:
                        t = new Intent(BottomBarActivity.this, AboutUsActivity.class);
                        break;
                    default:
                        t = null;
                        break;
                }
                if (t != null)
                    BottomBarActivity.this.startActivity(t);
                return false;
            }
        });
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
            }
        }
    }
}