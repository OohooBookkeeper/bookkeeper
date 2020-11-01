package com.example.jizhangdemo.add;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.fragment.SettingActivity;

public class ModifyActivity extends AppCompatActivity {

    Toolbar tb;
    FrameLayout ff;
    FragmentManager fm;
    FragmentTransaction ft;
    private Object SettingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        tb = findViewById(R.id.edit_tb);
        tb.inflateMenu(R.menu.menu_edit);
        ff = findViewById(R.id.frame_frag);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame_frag, new SettingActivity());
        ft.commit();

        tb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.updateT:
                        Toast.makeText(ModifyActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.deleteT:
                        Toast.makeText(ModifyActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(ModifyActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }
}