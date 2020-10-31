package com.example.jizhangdemo.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.UserManage;
import com.example.jizhangdemo.login.LoginMainActivity;
import com.example.jizhangdemo.setting.AboutUsActivity;
import com.example.jizhangdemo.setting.AccountSettingActivity;
import com.example.jizhangdemo.setting.SecuritySettingActivity;

public class SettingActivity extends Fragment {

    private Button mBtn_exit_login,mBtn_about_us,mBtn_security_setting,mBtn_account_setting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_setting,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtn_exit_login = view.findViewById(R.id.btn_exit_login);
        mBtn_about_us = view.findViewById(R.id.btn_about_us);
        mBtn_security_setting = view.findViewById(R.id.btn_security_setting);
        mBtn_account_setting = view.findViewById(R.id.btn_account_setting);

        mBtn_exit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManage.getInstance().deleteUserInfo(getActivity());
                Intent intent = new Intent(getActivity(), LoginMainActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        mBtn_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
            }
        });

        mBtn_security_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SecuritySettingActivity.class);
                startActivity(intent);
            }
        });

        mBtn_account_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AccountSettingActivity.class);
                startActivity(intent);
            }
        });
    }
}