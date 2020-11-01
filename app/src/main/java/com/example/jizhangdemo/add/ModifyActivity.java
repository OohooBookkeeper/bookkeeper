package com.example.jizhangdemo.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.UserInfo;
import com.example.jizhangdemo.UserManage;
import com.example.jizhangdemo.fragment.SettingActivity;

public class ModifyActivity extends AppCompatActivity {

    Toolbar tb;
    FrameLayout ff;
    FragmentManager fm;
    FragmentTransaction ft;
    Bundle bundle;
    String username;
    private int id = -1, type, outaccount;

    private AddInFragment fragment1;
    private AddOutFragment fragment2;
    private AddTransferFragment fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        tb = findViewById(R.id.edit_tb);
        tb.inflateMenu(R.menu.menu_edit);
        ff = findViewById(R.id.frame_frag);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        initFragment();
        UserInfo userInfo = UserManage.getInstance().getUserInfo(this);
        username = userInfo.getUserName();
        bundle = new Bundle();
        bundle.putString("username", username);

        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Fragment fg;
                switch (item.getItemId()) {
                    case R.id.updateT:
                        fg = fm.findFragmentById(R.id.frame_frag);
                        if (fg instanceof AddInFragment) {
                            ((AddInFragment) fg).save();
                        } else if (fg instanceof AddOutFragment) {
                            ((AddOutFragment) fg).save();
                        } else if (fg instanceof AddTransferFragment) {
                            ((AddTransferFragment) fg).save();
                        }
                        Toast.makeText(ModifyActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        ModifyActivity.this.finish();
                        break;
                    case R.id.deleteT:
                        fg = fm.findFragmentById(R.id.frame_frag);
                        if (fg instanceof AddInFragment) {
                            ((AddInFragment) fg).deleteTransaction();
                        } else if (fg instanceof AddOutFragment) {
                            ((AddOutFragment) fg).deleteTransaction();
                        } else if (fg instanceof AddTransferFragment) {
                            ((AddTransferFragment) fg).deleteTransaction();
                        }
                        Toast.makeText(ModifyActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        ModifyActivity.this.finish();
                        break;
                    default:
                        Toast.makeText(ModifyActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        if (getIntent().getExtras() != null){
            id = getIntent().getExtras().getInt("id");
            type = getIntent().getExtras().getInt("type");
            outaccount = getIntent().getExtras().getInt("outaccount");
            bundle.putInt("id",id);
            bundle.putBoolean("isEdit",true);
        }
        if (id != -1) {
            if (outaccount != Transaction.NONTRANSFER) {
                ft.replace(R.id.frame_frag, fragment3);
                ((TextView) findViewById(R.id.txt_title)).setText("转账账目信息");
            } else if (type == Transaction.EXPENSE) {
                ft.replace(R.id.frame_frag, fragment2);
                ((TextView) findViewById(R.id.txt_title)).setText("支出账目信息");
            } else {
                ft.replace(R.id.frame_frag, fragment1);
                ((TextView) findViewById(R.id.txt_title)).setText("收入账目信息");
            }
            ft.commit();
        }
        putBundleInFragment();

    }

    private void initFragment(){
        fragment1 = new AddInFragment();
        fragment2 = new AddOutFragment();
        fragment3 = new AddTransferFragment();
    }
    private void putBundleInFragment(){
        fragment1.setArguments(bundle);
        fragment2.setArguments(bundle);
        fragment3.setArguments(bundle);
    }
}