package com.example.oohoobkk;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private AccountHelper accountHelper;
    EditText pat;
    TextView result;
    EditText tUsrn;
    EditText tPwd;
    Spinner sQuest;
    EditText tAns;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch paten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accountHelper = new AccountHelper(this, "account.db", null, 1);
        pat = (EditText) findViewById(R.id.txt_pat);
        result = (TextView) findViewById(R.id.txt_res);
        Button bAdd = (Button) findViewById(R.id.btn_add);
        Button bList = (Button) findViewById(R.id.btn_list);
        tUsrn = (EditText) findViewById(R.id.txt_usrn);
        tPwd = (EditText) findViewById(R.id.txt_pwd);
        sQuest = (Spinner) findViewById(R.id.spinner_q);
        tAns = (EditText) findViewById(R.id.txt_ans);
        paten = (Switch) findViewById(R.id.switch_p);
        paten.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pat.setEnabled(isChecked);
            }
        });
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountHelper.signup(tUsrn.getText().toString(),
                        tPwd.getText().toString(),
                        Integer.parseInt(sQuest.getSelectedItem().toString()),
                        tAns.getText().toString(),
                        paten.isChecked() ? 1 : 0,
                        pat.isEnabled() ? Integer.parseInt(pat.getText().toString()) : 0
                        );
            }
        });
        bList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder s = new StringBuilder();
                Cursor c = accountHelper.listall();
                if (c.moveToFirst()) {
                    do {
                        String usr = c.getString(c.getColumnIndex("username"));
                        String pwd = c.getString(c.getColumnIndex("password"));
                        int qst = c.getInt(c.getColumnIndex("question"));
                        String ans = c.getString(c.getColumnIndex("answer"));
                        int ptnen = c.getInt(c.getColumnIndex("patternenabled"));
                        int ptn = c.getInt(c.getColumnIndex("pattern"));
                        s.append(usr).append(" ").append(pwd).append(" ").append(qst).append(" ");
                        s.append(ans).append(" ").append(ptnen).append(" ").append(ptn).append("\n");
                    } while (c.moveToNext());
                    result.setText(s.toString());
                }
            }
        });
    }
}