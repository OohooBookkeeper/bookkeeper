package com.example.jizhangdemo.add;

import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.jizhangdemo.fragment.HomePageActivity;
import com.example.jizhangdemo.fragment.JournalAccountActivity;
import com.example.jizhangdemo.fragment.SettingActivity;
import com.example.jizhangdemo.fragment.StatisticsActivity;

import java.util.List;

public class MyVPAdapter extends FragmentStatePagerAdapter {

    public MyVPAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new HomePageActivity();
            case 1:
                return new JournalAccountActivity();
            case 2:
                return new StatisticsActivity();
            case 3:
                return new SettingActivity();
            default:
                throw new IllegalStateException("Illegal state!");
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return "收入";
            case 1:
                return "支出";
            case 2:
                return "转账";
            case 3:
                return "模板";
            default:
                throw new IllegalStateException("Illegal state!");
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
