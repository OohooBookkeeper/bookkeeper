package com.example.oohoobkk;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private BookHelper bookHelper;
    EditText tID;
    EditText tAmount;
    EditText tCtg;
    EditText tSctg;
    EditText tAcc;
    EditText tOacc;
    EditText tTime;
    EditText tMem;
    EditText tTrader;
    EditText tProject;
    EditText tNote;
    TextView tRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookHelper = new BookHelper(this, "book.db");
        Button bAdd = findViewById(R.id.btn_add);
        Button bList = findViewById(R.id.btn_list);
        Button bDelete = findViewById(R.id.btn_delete);
        Button bUpdate = findViewById(R.id.btn_update);
        Button bListTime = findViewById(R.id.btn_listtime);
        tID = findViewById(R.id.txt_id);
        tAmount = findViewById(R.id.txt_amount);
        tCtg = findViewById(R.id.txt_ctg);
        tSctg = findViewById(R.id.txt_sctg);
        tAcc = findViewById(R.id.txt_acc);
        tOacc = findViewById(R.id.txt_oacc);
        tTime = findViewById(R.id.txt_time);
        tMem = findViewById(R.id.txt_mem);
        tTrader = findViewById(R.id.txt_tr);
        tProject = findViewById(R.id.txt_prj);
        tNote = findViewById(R.id.txt_note);
        tRes = findViewById(R.id.txt_res);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction t = new Transaction();
                t.amount = Integer.parseInt(tAmount.getText().toString());
                t.category = Integer.parseInt(tCtg.getText().toString());
                t.subcategory = Integer.parseInt(tSctg.getText().toString());
                t.account = Integer.parseInt(tAcc.getText().toString());
                t.outaccount = Integer.parseInt(tOacc.getText().toString());
                t.member = Integer.parseInt(tMem.getText().toString());
                t.trader = Integer.parseInt(tTrader.getText().toString());
                t.project = Integer.parseInt(tProject.getText().toString());
                t.note = tNote.getText().toString();
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                Date time = null;
                try {
                    time = f.parse(tTime.getText().toString());
                } catch (ParseException e) {
                    Toast.makeText(MainActivity.this, "Parse error!", Toast.LENGTH_SHORT).show();
                } finally {
                    if (time != null)
                        t.time = (int) (time.getTime() / 60000);
                    else
                        t.time = 0;
                    bookHelper.addTransaction(t);
                }
            }
        });
        bList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat f = SimpleDateFormat.getDateTimeInstance();
                tRes.setText("");
                Display dis = new Display(MainActivity.this.bookHelper);
                Map<Integer, Map<Integer, List<Transaction>>> map = dis.displayByYear(new Date(0), new Date());
                for (Map<Integer, List<Transaction>> monthMaps : map.values()) {
                    for (List<Transaction> tList : monthMaps.values()) {
                        for (Transaction t : tList) {
                            Date d = new Date();
                            d.setTime((long) t.time * 60000);
                            StringBuilder str = new StringBuilder();
                            str.append(t.id);
                            str.append(", ");
                            str.append(t.amount);
                            str.append(", ");
                            str.append(t.category);
                            str.append(", ");
                            str.append(t.cname == null ? "NULL" : t.cname);
                            str.append(", ");
                            str.append(t.subcategory);
                            str.append(", ");
                            str.append(t.sname == null ? "NULL" : t.sname);
                            str.append(", ");
                            str.append(t.account);
                            str.append(", ");
                            str.append(t.aname == null ? "NULL" : t.aname);
                            str.append(", ");
                            str.append(t.outaccount);
                            str.append(", ");
                            str.append(t.oaname == null ? "NULL" : t.oaname);
                            str.append(", ");
                            str.append(t.type);
                            str.append(", ");
                            str.append(d.toString());
                            str.append(", ");
                            str.append(t.member);
                            str.append(", ");
                            str.append(t.mname);
                            str.append(", ");
                            str.append(t.trader);
                            str.append(", ");
                            str.append(t.rname);
                            str.append(", ");
                            str.append(t.project);
                            str.append(", ");
                            str.append(t.pname);
                            str.append(", ");
                            str.append(t.note);
                            str.append("\n");
                            tRes.append(str.toString());
                        }
                    }
                }
                /*
                Cursor c = bookHelper.displayAllTransactions(BookHelper.NULL, false);
                tRes.setText("");
                if (c.moveToFirst()) {
                    do {
                        int id = c.getInt(c.getColumnIndex("id"));
                        int amount = c.getInt(c.getColumnIndex("amount"));
                        int ctg = c.getInt(c.getColumnIndex("category"));
                        int sctg = c.getInt(c.getColumnIndex("subcategory"));
                        int account = c.getInt(c.getColumnIndex("account"));
                        int outaccount = c.getInt(c.getColumnIndex("outaccount"));
                        int time = c.getInt(c.getColumnIndex("time"));
                        int member = c.getInt(c.getColumnIndex("member"));
                        int trader = c.getInt(c.getColumnIndex("trader"));
                        int project = c.getInt(c.getColumnIndex("project"));
                        String note = c.getString(c.getColumnIndex("note"));
                        String cname = c.getString(c.getColumnIndex("cname"));
                        String sname = c.getString(c.getColumnIndex("sname"));
                        String aname = c.getString(c.getColumnIndex("aname"));
                        String oaname = c.getString(c.getColumnIndex("oaname"));
                        String mname = c.getString(c.getColumnIndex("mname"));
                        String rname = c.getString(c.getColumnIndex("rname"));
                        String pname = c.getString(c.getColumnIndex("pname"));
                        String[] s = {String.valueOf(id), String.valueOf(amount), String.valueOf(ctg),
                                    cname, String.valueOf(sctg), sname, String.valueOf(account), aname,
                                    String.valueOf(outaccount), oaname, String.valueOf(time),
                                    String.valueOf(member), mname, String.valueOf(trader), rname,
                                    String.valueOf(project), pname, note};
                        tRes.append(Arrays.toString(s));
                        tRes.append("\n");
                    } while (c.moveToNext());
                }
                 */
            }
        });

        bListTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText tSt = findViewById(R.id.txt_st);
                EditText tEt = findViewById(R.id.txt_et);
                int st, et;
                try {
                    st = Integer.parseInt(tSt.getText().toString());
                    et = Integer.parseInt(tEt.getText().toString());
                    Cursor c = bookHelper.displayAllTransactions(st, et, BookHelper.NULL, false);
                    Toast.makeText(MainActivity.this, String.valueOf(c.getCount()), Toast.LENGTH_SHORT).show();
                    tRes.setText("");
                    if (c.moveToFirst()) {
                        do {
                            int id = c.getInt(c.getColumnIndex("id"));
                            int amount = c.getInt(c.getColumnIndex("amount"));
                            int ctg = c.getInt(c.getColumnIndex("category"));
                            int sctg = c.getInt(c.getColumnIndex("subcategory"));
                            int account = c.getInt(c.getColumnIndex("account"));
                            int outaccount = c.getInt(c.getColumnIndex("outaccount"));
                            int time = c.getInt(c.getColumnIndex("time"));
                            int member = c.getInt(c.getColumnIndex("member"));
                            int trader = c.getInt(c.getColumnIndex("trader"));
                            int project = c.getInt(c.getColumnIndex("project"));
                            String note = c.getString(c.getColumnIndex("note"));
                            String cname = c.getString(c.getColumnIndex("cname"));
                            String sname = c.getString(c.getColumnIndex("sname"));
                            String aname = c.getString(c.getColumnIndex("aname"));
                            String oaname = c.getString(c.getColumnIndex("oaname"));
                            String mname = c.getString(c.getColumnIndex("mname"));
                            String rname = c.getString(c.getColumnIndex("rname"));
                            String pname = c.getString(c.getColumnIndex("pname"));
                            String[] s = {String.valueOf(id), String.valueOf(amount), String.valueOf(ctg),
                                    cname, String.valueOf(sctg), sname, String.valueOf(account), aname,
                                    String.valueOf(outaccount), oaname, String.valueOf(time),
                                    String.valueOf(member), mname, String.valueOf(trader), rname,
                                    String.valueOf(project), pname, note};
                            tRes.append(Arrays.toString(s));
                            tRes.append("\n");
                        } while (c.moveToNext());
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Time should not be empty!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Unknown exception!", Toast.LENGTH_LONG).show();
                }
            }
        });

        bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookHelper.deleteTransaction(Integer.parseInt(tID.getText().toString()));
            }
        });
        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ID = Integer.parseInt(tID.getText().toString());
                Transaction t = new Transaction();
                t.amount = Integer.parseInt(tAmount.getText().toString());
                t.category = Integer.parseInt(tCtg.getText().toString());
                t.subcategory = Integer.parseInt(tSctg.getText().toString());
                t.account = Integer.parseInt(tAcc.getText().toString());
                t.outaccount = Integer.parseInt(tOacc.getText().toString());
                t.member = Integer.parseInt(tMem.getText().toString());
                t.trader = Integer.parseInt(tTrader.getText().toString());
                t.project = Integer.parseInt(tProject.getText().toString());
                t.note = tNote.getText().toString();
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                Date time = null;
                try {
                    time = f.parse(tTime.getText().toString());
                } catch (ParseException e) {
                    Toast.makeText(MainActivity.this, "Parse error!", Toast.LENGTH_SHORT).show();
                } finally {
                    if (time != null)
                        t.time = (int) (time.getTime() / 60000);
                    else
                        t.time = 0;
                    bookHelper.updateTransaction(ID, t);
                }
            }
        });
        Cursor c = bookHelper.listAllSubcategory();
        tRes.setText("");
        if (c.moveToFirst()) {
            do {
                int category = c.getInt(c.getColumnIndex("category"));
                String cname = c.getString(c.getColumnIndex("cname"));
                int subcategory = c.getInt(c.getColumnIndex("subcategory"));
                String sname = c.getString(c.getColumnIndex("sname"));
                String[] s = {String.valueOf(category), cname, String.valueOf(subcategory), sname};
                tRes.append(Arrays.toString(s));
                tRes.append("\n");
            } while (c.moveToNext());
        }
    }

}