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
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.UserInfo;
import com.example.jizhangdemo.UserManage;
import com.example.jizhangdemo.fragment.HomePageActivity;
import com.example.jizhangdemo.fragment.JournalAccountActivity;
import com.example.jizhangdemo.fragment.SettingActivity;
import com.example.jizhangdemo.fragment.StatisticsActivity;
import com.example.jizhangdemo.journalAccount.Transaction;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TabAddActivity extends AppCompatActivity {

    TabLayout tl;
    ViewPager vp;
    MyVPAdapter vpAdapter;
    Toolbar tb;
    String username;
    Bundle bundle;
    private AddInFragment fragment1;
    private AddOutFragment fragment2;
    private AddTransferFragment fragment3;
    private AddTemplateFragment fragment4;
    private int id = -1,type,outaccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_keep_account);
        UserInfo userInfo = UserManage.getInstance().getUserInfo(this);
        username = userInfo.getUserName();
        bundle = new Bundle();
        bundle.putString("username",username);
        if (getIntent().getExtras() != null){
            id = getIntent().getExtras().getInt("id");
            type = getIntent().getExtras().getInt("type");
            outaccount = getIntent().getExtras().getInt("outaccount");
            bundle.putInt("id",id);
            bundle.putBoolean("isEdit",true);
        }
        tl = findViewById(R.id.tablayout);
        vp = findViewById(R.id.viewpager);
        tb = findViewById(R.id.toolbar);
        initFragment();
        List<Fragment> frags = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        frags.add(fragment1);
        frags.add(fragment2);
        frags.add(fragment3);
        frags.add(fragment4);
        titles.add("收入");
        titles.add("支出");
        titles.add("转账");
        titles.add("模板");
        vpAdapter = new MyVPAdapter(this, getSupportFragmentManager(), titles, frags);
        vp.setAdapter(vpAdapter);
        tl.setTabMode(TabLayout.MODE_FIXED);
        tl.setupWithViewPager(vp);
        tb.inflateMenu(R.menu.menu_add);
        putBundleInFragment();
        if (id != -1) {
            if (outaccount == Transaction.NONTRANSFER) {
                switch (type){
                    case Transaction.INCOME:
                        vp.setCurrentItem(0);
                        break;
                    case Transaction.EXPENSE:
                        vp.setCurrentItem(1);
                        break;
                }
            } else {
                vp.setCurrentItem(2);
            }
        }
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
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    private void initFragment(){
        fragment1 = new AddInFragment();
        fragment2 = new AddOutFragment();
        fragment3 = new AddTransferFragment();
        fragment4 = new AddTemplateFragment();

    }
    private void putBundleInFragment(){
        fragment1.setArguments(bundle);
        fragment2.setArguments(bundle);
        fragment3.setArguments(bundle);
        fragment4.setArguments(bundle);
    }
}
