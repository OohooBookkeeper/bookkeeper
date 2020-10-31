package com.example.jizhangdemo.add;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.jizhangdemo.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class TabAddActivity extends AppCompatActivity {

    TabLayout tl;
    ViewPager vp;
    MyVPAdapter vpAdapter;
    Toolbar tb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_keep_account);
        tl = findViewById(R.id.tablayout);
        vp = findViewById(R.id.viewpager);
        tb = findViewById(R.id.toolbar);
        vpAdapter = new MyVPAdapter(getSupportFragmentManager());
        vp.setAdapter(vpAdapter);
        tl.setTabMode(TabLayout.MODE_FIXED);
        tl.setupWithViewPager(vp);
        tb.inflateMenu(R.menu.menu_add);
        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.addT:
                        Toast.makeText(TabAddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(TabAddActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }
}
