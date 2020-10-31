package com.example.jizhangdemo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.transition.NoTransition;
import com.example.jizhangdemo.R;
import com.example.jizhangdemo.UserManage;
import com.example.jizhangdemo.add.BookHelper;
import com.example.jizhangdemo.add.KeepAccountActivity;
import com.example.jizhangdemo.journalAccount.Bill;
import com.example.jizhangdemo.journalAccount.Display;
import com.example.jizhangdemo.journalAccount.ExpandableListAdapter;
import com.example.jizhangdemo.journalAccount.ExpandableListChildAdapter;
import com.example.jizhangdemo.journalAccount.LineElement;
import com.example.jizhangdemo.journalAccount.LineElementChild;
import com.example.jizhangdemo.journalAccount.Transaction;
import com.github.abel533.echarts.series.force.Link;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xui.widget.spinner.DropDownMenu;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JournalAccountActivity extends Fragment {

    private Button btn_DateStart,btn_DateEnd;
    private TextView tv_num_sum,tv_num_out,tv_num_in;
    private TimePickerView mTimePickerDialog1,mTimePickerDialog2;
    private MaterialSpinner spinner_select_time,spinner_select_category,spinner_select_account;
    private String[] time = new String[]{"日期","年","季","月","周","日"};
    private String[] category = new String[]{"分类","一级分类","二级分类"};
    private String[] account = new String[]{"账户","账户年度","账户月度"};
    private int searchType,time_pos,category_pos,account_pos;
    private String systemTime,username;
    private Date DateStart,DateEnd;
    private RecyclerView recyclerView;
    private boolean flag_account = false;
    private List<List<List<Transaction>>> data_triple;
    private List<List<Transaction>> data_double;
    private BookHelper bkhp;
    private Display dis;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_journal_account,container,false);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            username = bundle.getString("username");
        }
        bkhp = new BookHelper(getActivity(), username+".db");
        dis = new Display(bkhp);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_DateStart = view.findViewById(R.id.btn_DateStart);
        btn_DateEnd = view.findViewById(R.id.btn_DateEnd);
        tv_num_sum = view.findViewById(R.id.tv_num_sum);
        tv_num_out = view.findViewById(R.id.tv_num_out);
        tv_num_in = view.findViewById(R.id.tv_num_in);
        spinner_select_time = view.findViewById(R.id.spinner_select_time);
        spinner_select_category = view.findViewById(R.id.spinner_select_category);
        spinner_select_account = view.findViewById(R.id.spinner_select_account);
        recyclerView = view.findViewById(R.id.recycler_view);

        init_time_picker();
        init_spinner_select();
        LoadData();
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadData();
    }
    /**
     * 初始化时间选择器
     */
    private void init_time_picker(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        DateEnd = calendar.getTime();
        btn_DateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView mDatePicker = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelected(Date date, View v) {
                        DateEnd.setTime(date.getTime() + 86340000);
                        btn_DateEnd.setText(new SimpleDateFormat("yyyy年MM月dd日").format(date));
                        LoadData();
                    }
                })
                        .setType(true,true,true,false,false,false)
                        .setDate(calendar)
                        .setTitleText("结束日期选择")
                        .build();
                mDatePicker.show();
            }
        });
        calendar.add(Calendar.DATE,-365);
        DateStart = calendar.getTime();
        btn_DateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView mDatePicker = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelected(Date date, View v) {
                        DateStart = date;
                        btn_DateStart.setText(new SimpleDateFormat("yyyy年MM月dd日").format(date));
                        LoadData();
                    }
                })
                        .setType(true,true,true,false,false,false)
                        .setDate(calendar)
                        .setTitleText("起始日期选择")
                        .build();
                mDatePicker.show();
            }
        });
        btn_DateStart.setText(new SimpleDateFormat("yyyy年MM月dd日").format(calendar.getTime()));
        btn_DateEnd.setText(new SimpleDateFormat("yyyy年MM月dd日").format(new Date(System.currentTimeMillis())));
    }

    /**
     * 初始化下拉框
     */
    private void init_spinner_select(){
        spinner_select_time.setItems(time);
        spinner_select_category.setItems(category);
        spinner_select_account.setItems(account);

        spinner_select_time.setSelectedIndex(1);
        time_pos = 1;
        searchType = 1;
        spinner_select_category.setSelectedItem(0);
        category_pos = 0;
        spinner_select_account.setSelectedIndex(0);
        account_pos = 0;

        spinner_select_time.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                spinner_select_category.setSelectedItem(0);
                category_pos = 0;
                spinner_select_account.setSelectedIndex(0);
                account_pos = 0;
                searchType = position;
                time_pos = position;
                if (time_pos == 0 && category_pos == 0 && account_pos == 0){
                    spinner_select_time.setSelectedIndex(1);
                    searchType = 1;
                    time_pos = 1;
                }
                LoadData();
            }
        });

        spinner_select_category.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                spinner_select_time.setSelectedItem(0);
                time_pos = 0;
                spinner_select_account.setSelectedIndex(0);
                account_pos = 0;
                searchType = position + 5;
                category_pos = position;
                if (time_pos == 0 && category_pos == 0 && account_pos == 0){
                    spinner_select_time.setSelectedIndex(1);
                    searchType = 1;
                    time_pos = 1;
                }
                LoadData();
            }
        });

        spinner_select_account.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                spinner_select_time.setSelectedItem(0);
                time_pos = 0;
                spinner_select_category.setSelectedIndex(0);
                category_pos = 0;
                searchType = position + 7;
                account_pos = position;
                if (time_pos == 0 && category_pos == 0 && account_pos == 0){
                    spinner_select_time.setSelectedIndex(1);
                    searchType = 1;
                    time_pos = 1;
                }
                LoadData();
            }
        });
    }

    private void LoadData(){
        dis.notifyDatasetChanged();
        data_triple = new LinkedList<>();
        data_double = new LinkedList<>();

        Set<Integer> accounts;
        switch (searchType) {
            case 1: // Year
                Map<Integer, Map<Integer, List<Transaction>>> dataByYear = dis.displayByYear(DateStart, DateEnd);
                Set<Integer> years = dataByYear.keySet();
                for (int year : years) {
                    Map<Integer, List<Transaction>> dataInAYear = dataByYear.get(year);
                    List<List<Transaction>> monthList = new LinkedList<>();
                    for (int month : dataInAYear.keySet()) {
                        monthList.add(dataInAYear.get(month));
                    }
                    data_triple.add(monthList);
                }
                break;
            case 2:
                Map<Integer, List<Transaction>> dataByQuarter = dis.displayBy(DateStart, DateEnd, Display.QUARTER);
                Set<Integer> q = dataByQuarter.keySet();
                for (int key : q) {
                    data_double.add(dataByQuarter.get(key));
                }
                break;
            case 3:
                Map<Integer, List<Transaction>> dataByMonth = dis.displayBy(DateStart, DateEnd, Display.MONTH);
                Set<Integer> m = dataByMonth.keySet();
                for (int key : m) {
                    data_double.add(dataByMonth.get(key));
                }
                break;
            case 4:
                Map<Integer, List<Transaction>> dataByWeek = dis.displayBy(DateStart, DateEnd, Display.WEEK);
                Set<Integer> w = dataByWeek.keySet();
                for (int key : w) {
                    data_double.add(dataByWeek.get(key));
                }
                break;
            case 5:
                Map<Integer, List<Transaction>> dataByDate = dis.displayBy(DateStart, DateEnd, Display.DATE);
                Set<Integer> d = dataByDate.keySet();
                for (int key : d) {
                    data_double.add(dataByDate.get(key));
                }
                break;
            case 6:
                Map<Integer, List<Transaction>> dataExpenseByCtg = dis.displayByCtg(DateStart, DateEnd, false, Transaction.EXPENSE);
                Map<Integer, List<Transaction>> dataIncomeByCtg = dis.displayByCtg(DateStart, DateEnd, false, Transaction.INCOME);
                Map<Integer, List<Transaction>> dataTransferByCtg = dis.displayByCtg(DateStart, DateEnd, false, Transaction.TRANSFER);
                Set<Integer> exCategories = dataExpenseByCtg.keySet();
                List<List<Transaction>> exCtgList = new LinkedList<>();
                for (int category : exCategories) {
                    List<Transaction> tList = dataExpenseByCtg.get(category);
                    exCtgList.add(tList);
                }
                data_triple.add(exCtgList);
                Set<Integer> inCategories = dataIncomeByCtg.keySet();
                List<List<Transaction>> inCtgList = new LinkedList<>();
                for (int category : inCategories) {
                    List<Transaction> tList = dataIncomeByCtg.get(category);
                    inCtgList.add(tList);
                }
                data_triple.add(inCtgList);
                Set<Integer> tfCategories = dataTransferByCtg.keySet();
                List<List<Transaction>> tfCtgList = new LinkedList<>();
                for (int category : tfCategories) {
                    List<Transaction> tList = dataTransferByCtg.get(category);
                    tfCtgList.add(tList);
                }
                data_triple.add(tfCtgList);
                break;
            case 7:
                Map<Integer, List<Transaction>> dataExpenseBySctg = dis.displayByCtg(DateStart, DateEnd, true, Transaction.EXPENSE);
                Map<Integer, List<Transaction>> dataIncomeBySctg = dis.displayByCtg(DateStart, DateEnd, true, Transaction.INCOME);
                Map<Integer, List<Transaction>> dataTransferBySctg = dis.displayByCtg(DateStart, DateEnd, true, Transaction.TRANSFER);
                Set<Integer> exSubcategories = dataExpenseBySctg.keySet();
                List<List<Transaction>> exSctgList = new LinkedList<>();
                for (int subcategory : exSubcategories) {
                    List<Transaction> tList = dataExpenseBySctg.get(subcategory);
                    exSctgList.add(tList);
                }
                data_triple.add(exSctgList);
                Set<Integer> inSubcategories = dataIncomeBySctg.keySet();
                List<List<Transaction>> inSctgList = new LinkedList<>();
                for (int subcategory : inSubcategories) {
                    List<Transaction> tList = dataIncomeBySctg.get(subcategory);
                    inSctgList.add(tList);
                }
                data_triple.add(inSctgList);
                Set<Integer> tfSubcategories = dataTransferBySctg.keySet();
                List<List<Transaction>> tfSctgList = new LinkedList<>();
                for (int subcategory : tfSubcategories) {
                    List<Transaction> tList = dataTransferBySctg.get(subcategory);
                    tfSctgList.add(tList);
                }
                data_triple.add(tfSctgList);
                break;
            case 8:
                Map<Integer, Map<Integer, List<Transaction>>> dataByAccountYear = dis.displayByAccount(DateStart, DateEnd, false);
                accounts = dataByAccountYear.keySet();
                for (int account : accounts) {
                    Map<Integer, List<Transaction>> dataInAYear = dataByAccountYear.get(account);
                    List<List<Transaction>> yearList = new LinkedList<>();
                    for (int year : dataInAYear.keySet()) {
                        yearList.add(dataInAYear.get(year));
                    }
                    data_triple.add(yearList);
                }
                break;
            case 9:
                Map<Integer, Map<Integer, List<Transaction>>> dataByAccountMonth = dis.displayByAccount(DateStart, DateEnd, true);
                accounts = dataByAccountMonth.keySet();
                for (int account : accounts) {
                    Map<Integer, List<Transaction>> dataInAMonth = dataByAccountMonth.get(account);
                    List<List<Transaction>> monthList = new LinkedList<>();
                    for (int year : dataInAMonth.keySet()) {
                        monthList.add(dataInAMonth.get(year));
                    }
                    data_triple.add(monthList);
                }
                break;
            default:
                Toast.makeText(getActivity(), "非法排序依据！", Toast.LENGTH_SHORT).show();
                return;
        }
        if (searchType == 1 || searchType == 6 || searchType == 7 || searchType == 8 || searchType == 9){
            tv_num_sum.setText(String.valueOf(getSumIn(data_triple).subtract(getSumOut(data_triple))));
            tv_num_in.setText(String.valueOf(getSumIn(data_triple)));
            tv_num_out.setText(String.valueOf(getSumOut(data_triple)));
            List<LineElement> lle = new LinkedList<>();
            for (List<List<Transaction>> llt:data_triple){
                if (llt != null && !llt.isEmpty()){
                    List<LineElementChild> llec= new LinkedList<>();
                    for (List<Transaction> lt :llt){
                        if (lt != null && !lt.isEmpty()){
                            List<View> lv = new LinkedList<>();
                            for (Transaction t:lt){
                                if (t != null){
                                    lv.add(BillToViewAdapter(t));
                                }
                            }
                            LineElementChild lec = new LineElementChild(getLineElementChildTitle(lt),getLineElementChildIn(lt).toPlainString(),
                                    getLineElementChildOut(lt).toPlainString(),"",lv);
                            llec.add(lec);
                        }
                    }
                    LineElement le = new LineElement(getLineElementTitle(llt),getLineElementSum(llt).toPlainString(),
                            getLineElementIn(llt).toPlainString(),getLineElementOut(llt).toPlainString(),llec);
                    lle.add(le);
                }
            }
            WidgetUtils.initRecyclerView(recyclerView);
            recyclerView.setAdapter(new ExpandableListAdapter(recyclerView,lle,getActivity()));
        } else if (searchType == 2 || searchType == 3 || searchType == 4 || searchType == 5){
            tv_num_sum.setText(String.valueOf(getLineElementIn(data_double).subtract(getLineElementOut(data_double))));
            tv_num_in.setText(String.valueOf(getLineElementIn(data_double)));
            tv_num_out.setText(String.valueOf(getLineElementOut(data_double)));
            List<LineElementChild> llec= new LinkedList<>();
            for (List<Transaction> lt :data_double){
                if (lt != null && !lt.isEmpty()){
                    List<View> lv = new LinkedList<>();
                    for (Transaction t:lt){
                        if (t != null){
                            lv.add(BillToViewAdapter(t));
                        }
                    }
                    LineElementChild lec = new LineElementChild(getLineElementChildTitle(lt),getLineElementChildIn(lt).toPlainString(),
                            getLineElementChildOut(lt).toPlainString(),(getLineElementChildIn(lt).subtract(getLineElementChildOut(lt))).toPlainString(),lv);
                    llec.add(lec);
                }
            }
            WidgetUtils.initRecyclerView(recyclerView);
            recyclerView.setAdapter(new ExpandableListChildAdapter(recyclerView,llec,getActivity()));
        }
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
            ImageView btn_edit = view.findViewById(R.id.btn_edit);

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
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",t.id);
                    Intent intent = new Intent(getActivity(), KeepAccountActivity.class).putExtras(bundle);
                    getActivity().startActivity(intent);
                }
            });
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
            }else {
                if (searchType == 8 || searchType == 9){
                    if (t.amount > 0) {
                        BigDecimal am = new BigDecimal(t.amount);
                        am = am.movePointLeft(2);
                        in = in.add(am);
                    }
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
            }else {
                if (searchType == 8 || searchType == 9){
                    if (t.amount < 0) {
                        BigDecimal am = (new BigDecimal(t.amount)).negate();
                        am = am.movePointLeft(2);
                        out = out.add(am);
                    }
                }
            }
        }
        return out;
    }

    public BigDecimal getLineElementIn(List<List<Transaction>> llt){
        BigDecimal in = new BigDecimal(0);
        for (List<Transaction> lt : llt){
            in = in.add(getLineElementChildIn(lt));
        }
        return in;
    }

    public BigDecimal getLineElementOut(List<List<Transaction>> llt){
        BigDecimal out = new BigDecimal(0);
        for (List<Transaction> lt : llt){
            out = out.add(getLineElementChildOut(lt));
        }
        return out;
    }

    public BigDecimal getLineElementSum(List<List<Transaction>> llt){
        BigDecimal sum = new BigDecimal(0);
        sum = getLineElementIn(llt).subtract(getLineElementOut(llt));
        return sum;
    }

    public BigDecimal getSumIn(List<List<List<Transaction>>> lllt){
        BigDecimal in = new BigDecimal(0);
        for (List<List<Transaction>> llt:lllt){
            in = in.add(getLineElementIn(llt));
        }
        return in;
    }

    public BigDecimal getSumOut(List<List<List<Transaction>>> lllt){
        BigDecimal out = new BigDecimal(0);
        for (List<List<Transaction>> llt:lllt){
            out = out.add(getLineElementOut(llt));
        }
        return out;
    }

    public String getLineElementChildTitle(List<Transaction> lt){
        if (lt.get(0) != null){
            Date d = new Date((long) lt.get(0).time * 60000);
            SimpleDateFormat f = new SimpleDateFormat();
            switch (searchType){
                case 1:
                    f.applyPattern("M月");
                    return f.format(d);
                case 2:
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    return year + "Q" + (month/3+1);
                case 3:
                    f.applyPattern("yyyy年M月");
                    return f.format(d);
                case 4:
                    f.applyPattern("yyyy'W'w");
                    return f.format(d);
                case 5:
                    f.applyPattern("yyyy-MM-dd");
                    return f.format(d);
                case 6:
                    if (lt.get(0).cname == null){
                        return "无分类";
                    }
                    return lt.get(0).cname;
                case 7:
                    if (lt.get(0).sname == null){
                        return "无二级分类";
                    }
                    return lt.get(0).sname;
                case 8:
                    f.applyPattern("yyyy年");
                    return f.format(d);
                case 9:
                    f.applyPattern("yyyy年M月");
                    return f.format(d);
            }
        }
        return "error";
    }
    public String getLineElementTitle(List<List<Transaction>> llt){
        if (llt != null){
            if (llt.get(0) != null){
                if (llt.get(0).get(0) != null){
                    Date d = new Date((long) llt.get(0).get(0).time * 60000);
                    SimpleDateFormat f = new SimpleDateFormat();
                    switch (searchType){
                        case 1:
                            f.applyPattern("yyyy年");
                            return f.format(d);
                        case 6: case 7:
                            if (llt.get(0).get(0).outaccount == Transaction.NONTRANSFER) {
                                if (llt.get(0).get(0).type == Transaction.INCOME){
                                    return "收入";
                                }
                                if (llt.get(0).get(0).type == Transaction.EXPENSE){
                                    return "支出";
                                }
                            }
                            return "转账";
                        case 8: case 9:
                            if (llt.get(0).get(0).aname == null){
                                return "无账户";
                            }else if (llt.get(0).get(0).outaccount == Transaction.NONTRANSFER){
                                return llt.get(0).get(0).aname;
                            }else if (llt.get(0).get(0).amount > 0){
                                return llt.get(0).get(0).oaname;
                            }
                            return llt.get(0).get(0).aname;
                    }
                }
            }
        }
        return "error";
    }
}