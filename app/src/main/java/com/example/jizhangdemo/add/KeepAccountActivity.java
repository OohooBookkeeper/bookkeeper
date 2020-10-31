package com.example.jizhangdemo.add;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.UserInfo;
import com.example.jizhangdemo.UserManage;
import com.example.jizhangdemo.journalAccount.Transaction;

public class KeepAccountActivity extends AppCompatActivity {

    private TextView add_top_bar_in, add_top_bar_out, add_top_bar_transfer, add_top_bar_template;
    private RelativeLayout add_top_bar_1_btn, add_top_bar_2_btn, add_top_bar_3_btn, add_top_bar_4_btn;
    private AddInFragment fragment1;
    private AddOutFragment fragment2;
    private AddTransferFragment fragment3;
    private AddTemplateFragment fragment4;
    private String username;
    private int id = -1;
    private com.example.jizhangdemo.journalAccount.Transaction t;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep_account);
        UserInfo userInfo = UserManage.getInstance().getUserInfo(this);
        username = userInfo.getUserName();
        bundle = new Bundle();
        bundle.putString("username",username);

        if (getIntent().getExtras() != null){
            id = getIntent().getExtras().getInt("id");
        }

        init();

        OnClick onClick = new OnClick();
        add_top_bar_1_btn.setOnClickListener(onClick);
        add_top_bar_2_btn.setOnClickListener(onClick);
        add_top_bar_3_btn.setOnClickListener(onClick);
        add_top_bar_4_btn.setOnClickListener(onClick);
    }

    public void setSelectStatus(int index) {
        switch (index) {
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

    private void getComponents(){
        add_top_bar_in = findViewById(R.id.add_top_bar_in);
        add_top_bar_out = findViewById(R.id.add_top_bar_out);
        add_top_bar_transfer = findViewById(R.id.add_top_bar_transfer);
        add_top_bar_template = findViewById(R.id.add_top_bar_template);

        add_top_bar_1_btn = findViewById(R.id.add_top_bar_1_btn);
        add_top_bar_2_btn = findViewById(R.id.add_top_bar_2_btn);
        add_top_bar_3_btn = findViewById(R.id.add_top_bar_3_btn);
        add_top_bar_4_btn = findViewById(R.id.add_top_bar_4_btn);
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

    private void putDataInBundle(Transaction t){
        if (t.outaccount == Transaction.NONTRANSFER){
            bundle.putInt("category",t.category);
            bundle.putInt("subcategory",t.subcategory);
        }else {
            bundle.putInt("outaccount",t.outaccount);
        }
        bundle.putInt("time",t.time);
        bundle.putInt("amount",t.amount);
        bundle.putInt("account",t.account);
        bundle.putString("remark",t.note);
        bundle.putInt("member",t.member);
        bundle.putBoolean("isEdit",true);
    }

    private class OnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_top_bar_1_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.add_fl_container, fragment1).commitAllowingStateLoss();
                    setSelectStatus(0);
                    break;
                case R.id.add_top_bar_2_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.add_fl_container, fragment2).commitAllowingStateLoss();
                    setSelectStatus(1);
                    break;
                case R.id.add_top_bar_3_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.add_fl_container, fragment3).commitAllowingStateLoss();
                    setSelectStatus(2);
                    break;
                case R.id.add_top_bar_4_btn:
                    getSupportFragmentManager().beginTransaction().replace(R.id.add_fl_container, fragment4).commitAllowingStateLoss();
                    setSelectStatus(3);
                    break;
            }
        }
    }

    private void init(){
        getComponents();
        initFragment();
        if (id == -1){
            putBundleInFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.add_fl_container,fragment1).commitAllowingStateLoss();
            setSelectStatus(0);
        }else {
            switch (t.type){
                case Transaction.INCOME:
                    getSupportFragmentManager().beginTransaction().add(R.id.add_fl_container,fragment1).commitAllowingStateLoss();
                    setSelectStatus(0);
                    putDataInBundle(t);
                    fragment1.setArguments(bundle);
                    break;
                case Transaction.EXPENSE:
                    getSupportFragmentManager().beginTransaction().add(R.id.add_fl_container,fragment2).commitAllowingStateLoss();
                    setSelectStatus(1);
                    putDataInBundle(t);
                    fragment2.setArguments(bundle);
                    break;
                case Transaction.TRANSFER:
                    getSupportFragmentManager().beginTransaction().add(R.id.add_fl_container,fragment3).commitAllowingStateLoss();
                    setSelectStatus(2);
                    putDataInBundle(t);
                    fragment3.setArguments(bundle);
                    break;
            }
        }
    }
}