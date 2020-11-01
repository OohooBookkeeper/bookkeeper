package com.example.jizhangdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.add.BookHelper;
import com.example.jizhangdemo.add.KeepAccountActivity;
import com.example.jizhangdemo.add.ModifyActivity;
import com.example.jizhangdemo.add.TabAddActivity;
import com.example.jizhangdemo.journalAccount.Display;
import com.example.jizhangdemo.journalAccount.ExpandableListChildAdapter;
import com.example.jizhangdemo.journalAccount.LineElementChild;
import com.example.jizhangdemo.journalAccount.Transaction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.xuexiang.xui.utils.WidgetUtils;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class HomePageActivity extends Fragment {

    private TextView tv_month, tv_month_out, tv_month_in, tDated, tDatew, tDatem, tDatey, tdi, tdo, twi, two, tmi, tmo, tyi, tyo;
    private Button btn_refresh;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private List<List<Transaction>> data_double;
    private BookHelper bkhp;
    private Display dis;
    private String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_page, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
        }
        bkhp = new BookHelper(getActivity(), username + ".db");
        dis = new Display(bkhp);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        tDated = view.findViewById(R.id.tdated);
        tDatew = view.findViewById(R.id.tdatew);
        tDatem = view.findViewById(R.id.tdatem);
        tDatey = view.findViewById(R.id.tdatey);
        tdi = view.findViewById(R.id.tdi);
        twi = view.findViewById(R.id.twi);
        tmi = view.findViewById(R.id.tmi);
        tyi = view.findViewById(R.id.tyi);
        tdo = view.findViewById(R.id.tdo);
        two = view.findViewById(R.id.two);
        tmo = view.findViewById(R.id.tmo);
        tyo = view.findViewById(R.id.tyo);

        LoadData();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TabAddActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadData();
    }

    public void setToFirstMinute(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
    }

    public void setToLastMinute(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 0);
    }

    private void LoadData() {
        List<Transaction> tList;
        int key;
        dis.notifyDatasetChanged();
        data_double = new LinkedList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        setToFirstMinute(c);
        setToLastMinute(c2);
        Map<Integer, List<Transaction>> dataByDate = dis.displayBy(c.getTime(), c2.getTime(), Display.DATE);
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c2.set(Calendar.MONTH, Calendar.DECEMBER);
        c2.set(Calendar.DATE, 31);
        Map<Integer, List<Transaction>> dataByDate2 = dis.displayBy(new Date(0), c2.getTime(), Display.DATE);
        int expense, income;
        Set<Integer> d = dataByDate.keySet();
        Date now = new Date();
        Date from;
        Date to;
        c.setTime(now);
        SimpleDateFormat fmt = new SimpleDateFormat();
        fmt.applyPattern("yyyy-MM-dd");
        tDated.setText(fmt.format(now));
        expense = 0;
        income = 0;
        key = (c.get(Calendar.YEAR) - 1970) * 1200 + c.get(Calendar.MONTH) * 100 + c.get(Calendar.DATE);
        if (dataByDate2.containsKey(key)) {
            tList = dataByDate2.get(key);
            for (Transaction t : tList) {
                if (t.outaccount == Transaction.NONTRANSFER) {
                    if (t.amount > 0)
                        income += t.amount;
                    else
                        expense += t.amount;
                }
            }
        }
        tdi.setText((new BigDecimal(income)).movePointLeft(2).toString());
        tdo.setText((new BigDecimal(expense)).movePointLeft(2).toString());
        c.setTime(now);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        setToFirstMinute(c);
        from = c.getTime();
        c.setTime(now);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        setToLastMinute(c);
        to = c.getTime();
        tDatew.setText(fmt.format(from) + "到" + fmt.format(to));
        expense = 0;
        income = 0;
        for (int dateKey : dataByDate2.keySet()) {
            for (Transaction t : Objects.requireNonNull(dataByDate2.get(dateKey))) {
                Date ddd = new Date((long)t.time * 60000);
                if (ddd.before(from) || ddd.after(to))
                    continue;
                if (t.outaccount == Transaction.NONTRANSFER) {
                    if (t.amount > 0)
                        income += t.amount;
                    else
                        expense += t.amount;
                }
            }
        }
        twi.setText((new BigDecimal(income)).movePointLeft(2).toString());
        two.setText((new BigDecimal(expense)).movePointLeft(2).toString());
        c.setTime(now);
        c.set(Calendar.DAY_OF_MONTH, 1);
        setToFirstMinute(c);
        from = c.getTime();
        c.clear();
        c.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        c.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
        c.roll(Calendar.DAY_OF_MONTH, -1);
        setToLastMinute(c);
        to = c.getTime();
        tDatem.setText(fmt.format(from) + "到" + fmt.format(to));
        expense = 0;
        income = 0;
        for (int dateKey : dataByDate2.keySet()) {
            for (Transaction t : Objects.requireNonNull(dataByDate2.get(dateKey))) {
                Date ddd = new Date((long)t.time * 60000);
                if (ddd.before(from) || ddd.after(to))
                    continue;
                if (t.outaccount == Transaction.NONTRANSFER) {
                    if (t.amount > 0)
                        income += t.amount;
                    else
                        expense += t.amount;
                }
            }
        }
        tmi.setText((new BigDecimal(income)).movePointLeft(2).toString());
        tmo.setText((new BigDecimal(expense)).movePointLeft(2).toString());
        c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR));
        setToFirstMinute(c);
        from = c.getTime();
        c.clear();
        c.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        c.roll(Calendar.DAY_OF_YEAR, -1);
        setToLastMinute(c);
        to = c.getTime();
        tDatey.setText(fmt.format(from) + "到" + fmt.format(to));
        expense = 0;
        income = 0;
        for (int dateKey : dataByDate2.keySet()) {
            for (Transaction t : Objects.requireNonNull(dataByDate2.get(dateKey))) {
                Date ddd = new Date((long)t.time * 60000);
                if (ddd.before(from) || ddd.after(to))
                    continue;
                if (t.outaccount == Transaction.NONTRANSFER) {
                    if (t.amount > 0)
                        income += t.amount;
                    else
                        expense += t.amount;
                }
            }
        }
        tyi.setText((new BigDecimal(income)).movePointLeft(2).toString());
        tyo.setText((new BigDecimal(expense)).movePointLeft(2).toString());
        for (int key2 : d) {
            data_double.add(dataByDate.get(key2));
        }
        List<LineElementChild> llec= new LinkedList<>();
        for (List<Transaction> lt :data_double){
            if (lt != null && !lt.isEmpty()){
                List<View> lv = new LinkedList<>();
                for (Transaction t:lt){
                    if (t != null){
                        lv.add(BillToViewAdapter(t));
                    }
                }
                LineElementChild lec = new LineElementChild("今日交易","",
                        "","",lv);
                llec.add(lec);
            }
        }
        WidgetUtils.initRecyclerView(recyclerView);
        recyclerView.setAdapter(new ExpandableListChildAdapter(recyclerView,llec,getActivity()));
    }
    public View BillToViewAdapter(Transaction t){
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View view = mInflater.inflate(R.layout.adapter_bill,null);
        if (t == null){
            return null;
        }else {
            TextView tv_day = view.findViewById(R.id.tv_day);
            TextView tv_day_of_week = view.findViewById(R.id.tv_day_of_week);
            TextView tv_secondCategory = view.findViewById(R.id.tv_second_category);
            TextView tv_time = view.findViewById(R.id.tv_time);
            TextView tv_account = view.findViewById(R.id.tv_account);
            TextView tv_money = view.findViewById(R.id.tv_money);

            Date d = new Date((long) t.time * 60000);
            SimpleDateFormat f = new SimpleDateFormat();
            f.applyPattern("dd");
            tv_day.setText(f.format(d));
            f.applyPattern("E");
            tv_day_of_week.setText(f.format(d));
            if (t.outaccount != Transaction.NONTRANSFER){
                tv_secondCategory.setText("转账");
            } else if (t.sname == null) {
                tv_secondCategory.setText("无分类");
            } else {
                tv_secondCategory.setText(t.sname);
            }

            f.applyPattern("HH:mm");
            tv_time.setText(f.format(d));
            if (t.outaccount == Transaction.NONTRANSFER){
                if (t.aname == null) {
                    tv_account.setText("无账户");
                } else {
                    tv_account.setText(t.aname);
                }
            } else {
                StringBuilder str = new StringBuilder();
                if (t.aname == null) {
                    str.append("无账户");
                } else {
                    str.append(t.aname);
                }
                str.append(" → ");
                if (t.oaname == null) {
                    str.append("无账户");
                } else {
                    str.append(t.oaname);
                }
                tv_account.setText(str.toString());
            }

            BigDecimal bd = new BigDecimal(t.amount);
            bd = bd.movePointLeft(2);
            tv_money.setText(bd.toString());
            if (t.outaccount == Transaction.NONTRANSFER){
                tv_money.setTextColor(Color.parseColor("#FFA500"));
            }else if (t.amount > 0){
                tv_money.setTextColor(Color.parseColor("#20BD27"));
            }else {
                tv_money.setTextColor(Color.parseColor("#DF1111"));
            }
        }
        return view;
    }
    public BigDecimal getLineElementChildIn(List<Transaction> lb){
        BigDecimal in = new BigDecimal(0);
        for (Transaction t:lb){
            if (t.outaccount == Transaction.NONTRANSFER) {
                if (t.amount > 0) {
                    BigDecimal am = new BigDecimal(t.amount);
                    am = am.movePointLeft(2);
                    in = in.add(am);
                }
            }
        }
        return in;
    }

    public BigDecimal getLineElementChildOut(List<Transaction> lb){
        BigDecimal out = new BigDecimal(0);
        for (Transaction t:lb){
            if (t.outaccount == Transaction.NONTRANSFER) {
                if (t.amount < 0) {
                    BigDecimal am = (new BigDecimal(t.amount)).negate();
                    am = am.movePointLeft(2);
                    out = out.add(am);
                }
            }
        }
        return out;
    }
}