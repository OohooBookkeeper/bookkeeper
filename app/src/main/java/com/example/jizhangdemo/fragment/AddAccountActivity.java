package com.example.jizhangdemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.add.AddInFragment;
import com.example.jizhangdemo.add.AddOutFragment;
import com.example.jizhangdemo.add.AddTemplateFragment;
import com.example.jizhangdemo.add.AddTransferFragment;

public class AddAccountActivity extends Fragment {

    private TextView add_top_bar_in,add_top_bar_out,add_top_bar_transfer,add_top_bar_template;
    private RelativeLayout add_top_bar_1_btn,add_top_bar_2_btn,add_top_bar_3_btn,add_top_bar_4_btn;
    private AddInFragment fragment1;
    private AddOutFragment fragment2;
    private AddTransferFragment fragment3;
    private AddTemplateFragment fragment4;
    private int Tag = 0;

    public void setSelectStatus(int index){
        switch (index){
            case 0:
                add_top_bar_in.setTextColor(Color.parseColor("#0000CD"));
                add_top_bar_out.setTextColor(Color.parseColor("#000000"));
                add_top_bar_transfer.setTextColor(Color.parseColor("#000000"));
                add_top_bar_template.setTextColor(Color.parseColor("#000000"));
                break;
            case 1:
                add_top_bar_in.setTextColor(Color.parseColor("#000000"));
                add_top_bar_out.setTextColor(Color.parseColor("#0000CD"));
                add_top_bar_transfer.setTextColor(Color.parseColor("#000000"));
                add_top_bar_template.setTextColor(Color.parseColor("#000000"));
                break;
            case 2:
                add_top_bar_in.setTextColor(Color.parseColor("#000000"));
                add_top_bar_out.setTextColor(Color.parseColor("#000000"));
                add_top_bar_transfer.setTextColor(Color.parseColor("#0000CD"));
                add_top_bar_template.setTextColor(Color.parseColor("#000000"));
                break;
            case 3:
                add_top_bar_in.setTextColor(Color.parseColor("#000000"));
                add_top_bar_out.setTextColor(Color.parseColor("#000000"));
                add_top_bar_transfer.setTextColor(Color.parseColor("#000000"));
                add_top_bar_template.setTextColor(Color.parseColor("#0000CD"));
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_account,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add_top_bar_in = view.findViewById(R.id.add_top_bar_in);
        add_top_bar_out = view.findViewById(R.id.add_top_bar_out);
        add_top_bar_transfer = view.findViewById(R.id.add_top_bar_transfer);
        add_top_bar_template = view.findViewById(R.id.add_top_bar_template);

        add_top_bar_1_btn = view.findViewById(R.id.add_top_bar_1_btn);
        add_top_bar_2_btn = view.findViewById(R.id.add_top_bar_2_btn);
        add_top_bar_3_btn = view.findViewById(R.id.add_top_bar_3_btn);
        add_top_bar_4_btn = view.findViewById(R.id.add_top_bar_4_btn);

        fragment1 = new AddInFragment();
        fragment2 = new AddOutFragment();
        fragment3 = new AddTransferFragment();
        fragment4 = new AddTemplateFragment();


        OnClick onClick = new OnClick();
        add_top_bar_1_btn.setOnClickListener(onClick);
        add_top_bar_2_btn.setOnClickListener(onClick);
        add_top_bar_3_btn.setOnClickListener(onClick);
        add_top_bar_4_btn.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (Tag == 0){
                Tag = 1;
                switch (v.getId()){
                    case R.id.add_top_bar_1_btn:
                        getFragmentManager().beginTransaction().add(R.id.add_fl_container,fragment1).commitAllowingStateLoss();
                        setSelectStatus(0);
                        break;
                    case R.id.add_top_bar_2_btn:
                        getFragmentManager().beginTransaction().add(R.id.add_fl_container,fragment2).commitAllowingStateLoss();
                        setSelectStatus(1);
                        break;
                    case R.id.add_top_bar_3_btn:
                        getFragmentManager().beginTransaction().add(R.id.add_fl_container,fragment3).commitAllowingStateLoss();
                        setSelectStatus(2);
                        break;
                    case R.id.add_top_bar_4_btn:
                        getFragmentManager().beginTransaction().add(R.id.add_fl_container,fragment4).commitAllowingStateLoss();
                        setSelectStatus(3);
                        break;
                }
            }else if(Tag == 1){
                switch (v.getId()){
                    case R.id.add_top_bar_1_btn:
                        getFragmentManager().beginTransaction().replace(R.id.add_fl_container,fragment1).commitAllowingStateLoss();
                        setSelectStatus(0);
                        break;
                    case R.id.add_top_bar_2_btn:
                        getFragmentManager().beginTransaction().replace(R.id.add_fl_container,fragment2).commitAllowingStateLoss();
                        setSelectStatus(1);
                        break;
                    case R.id.add_top_bar_3_btn:
                        getFragmentManager().beginTransaction().replace(R.id.add_fl_container,fragment3).commitAllowingStateLoss();
                        setSelectStatus(2);
                        break;
                    case R.id.add_top_bar_4_btn:
                        getFragmentManager().beginTransaction().replace(R.id.add_fl_container,fragment4).commitAllowingStateLoss();
                        setSelectStatus(3);
                        break;
                }
            }
        }
    }

}