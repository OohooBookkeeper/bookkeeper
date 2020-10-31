package com.example.jizhangdemo.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jizhangdemo.BottomBarActivity;
import com.example.jizhangdemo.R;
import com.example.jizhangdemo.UserManage;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class GraphLoginActivity extends AppCompatActivity implements NineSquare.OnPatternChangeListener{

    private NineSquare lqv;
    private String username;
    int patt = 123456789;
    private Fast_signin fast_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_login);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        lqv = findViewById(R.id.lock_pattern_view);
        lqv.setOnPatternChangeListener(this);
    }

    @Override

    public void onPatternChange(String patternPassword) {
        if(patternPassword==null){
            new MaterialDialog.Builder(GraphLoginActivity.this)
                    .iconRes(R.drawable.icon_warning)
                    .title("提示")
                    .content("登录失败，请画至少5个点")
                    .positiveText("确定")
                    .show();
      }else if(fast_signin.Fast_signin(GraphLoginActivity.this,username,patternPassword) == 1){
           new MaterialDialog.Builder(GraphLoginActivity.this)
                    .iconRes(R.drawable.icon_warning)
                   .title("提示")
                    .content("登录失败，图形密码错误")
                   .positiveText("确定")
                    .show();
       }else {
            new MaterialDialog.Builder(GraphLoginActivity.this)
                    .iconRes(R.drawable.icon_tip)
                    .title("提示")
                    .content("登录成功")
                   .positiveText("确定")
                   .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Intent intent = new Intent(GraphLoginActivity.this,BottomBarActivity.class);
                            UserManage.getInstance().saveUserInfo(GraphLoginActivity.this,username,patternPassword);
                            GraphLoginActivity.this.finish();
                            startActivity(intent);
                        }
                    })
                    .show();
        }
    }

    @Override
    public void onPatternStarted(boolean isStarted) {

    }
}