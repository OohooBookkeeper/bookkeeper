package com.example.jizhangdemo.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.UserInfo;
import com.example.jizhangdemo.UserManage;
import com.example.jizhangdemo.login.NineSquare;
import com.example.jizhangdemo.setting.User_setting;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

public class SetGraphPasswordActivity extends AppCompatActivity implements NineSquare.OnPatternChangeListener{

    private NineSquare lqv;
    private TextView tv_hint;
    private String username;
    private String first,second;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_graph_password);
        lqv = findViewById(R.id.lock_pattern_view);
        tv_hint = findViewById(R.id.tv_hint);
        lqv.setOnPatternChangeListener(this);
        UserInfo userInfo = UserManage.getInstance().getUserInfo(this);
        username = userInfo.getUserName();
    }

    @Override
    public void onPatternChange(String patternPassword) {
        if(patternPassword==null) {
            new MaterialDialog.Builder(SetGraphPasswordActivity.this)
                    .iconRes(R.drawable.icon_warning)
                    .title("提示")
                    .content("请画至少5个点")
                    .positiveText("确定")
                    .show();
        }else {
            if (!flag){
                flag = true;
                first = patternPassword;
                tv_hint.setText("请再次输入相同的图案密码");
            }else {
                second = patternPassword;
                if (!TextUtils.equals(first,second)){
                    new MaterialDialog.Builder(SetGraphPasswordActivity.this)
                            .iconRes(R.drawable.icon_warning)
                            .title("提示")
                            .content("两次输入图案密码不同")
                            .positiveText("确定")
                            .show();
                    flag = false;
                    tv_hint.setText("请输入图案密码");
                }
                else {
                    User_setting.Setting_pattern_password(SetGraphPasswordActivity.this,username,first);
                    finish();
                }
            }
        }
    }

    @Override
    public void onPatternStarted(boolean isStarted) {

    }
}