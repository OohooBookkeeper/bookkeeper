package com.example.jizhangdemo.add;

import android.content.Context;
import android.provider.Settings;
import android.widget.Toast;

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

    private Context c;
    private List<String> title;
    private List<Fragment> frag;

    public MyVPAdapter(Context c, FragmentManager fm, List<String> title, List<Fragment> frag) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.c = c;
        this.title = title;
        this.frag = frag;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        try {
            return frag.get(position);
        } catch (Exception e) {
            Toast.makeText(c, "Error on tab loading: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        try {
            return title.get(position);
        } catch (Exception e) {
            Toast.makeText(c, "Error on tab loading: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
