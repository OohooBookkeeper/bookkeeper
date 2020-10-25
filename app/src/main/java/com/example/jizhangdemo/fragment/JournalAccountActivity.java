package com.example.jizhangdemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.UserManage;
import com.example.jizhangdemo.journalAccount.Bill;
import com.example.jizhangdemo.journalAccount.ExpandableListAdapter;
import com.example.jizhangdemo.journalAccount.ExpandableListChildAdapter;
import com.example.jizhangdemo.journalAccount.LineElement;
import com.example.jizhangdemo.journalAccount.LineElementChild;
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

public class JournalAccountActivity extends Fragment {

    private Button btn_DateStart,btn_DateEnd,btn_select_account;
    private TextView tv_num_sum,tv_num_out,tv_num_in;
    private TimePickerView mTimePickerDialog1,mTimePickerDialog2;
    private MaterialSpinner spinner_select_time,spinner_select_category;
    private String[] time = new String[]{"日期","年","季","月","周","日"};
    private String[] category = new String[]{"分类","一级分类","二级分类"};
    private int searchType,time_pos,category_pos;
    private String systemTime,username;
    private Date DateStart,DateEnd;
    private RecyclerView recyclerView;
    private boolean flag_account = false;
    private List<List<List<Bill>>> data;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_journal_account,container,false);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            username = bundle.getString("username");
        }
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
        btn_select_account = view.findViewById(R.id.btn_select_account);
        recyclerView = view.findViewById(R.id.recycler_view);

        init_time_picker();
        init_spinner_select();
        WidgetUtils.initRecyclerView(recyclerView);
        //recyclerView.setAdapter(new ExpandableListAdapter(recyclerView, Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18")));
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
                        DateEnd = date;
                        btn_DateEnd.setText(new SimpleDateFormat("yyyy年MM月dd日").format(date));
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

        spinner_select_time.setSelectedIndex(1);
        time_pos = 1;
        searchType = 1;
        spinner_select_category.setSelectedItem(0);
        category_pos = 0;

        spinner_select_time.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                spinner_select_category.setSelectedItem(0);
                category_pos = 0;
                flag_account = false;
                btn_select_account.setTextColor(Color.parseColor("#000000"));
                searchType = position;
                time_pos = position;
                if (time_pos == 0 && category_pos == 0 && flag_account == false){
                    spinner_select_time.setSelectedIndex(1);
                    searchType = 1;
                    time_pos = 1;
                }
            }
        });

        spinner_select_category.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                spinner_select_time.setSelectedItem(0);
                time_pos = 0;
                flag_account = false;
                btn_select_account.setTextColor(Color.parseColor("#000000"));
                searchType = position + 5;
                category_pos = position;
                if (time_pos == 0 && category_pos == 0 && flag_account == false){
                    spinner_select_time.setSelectedIndex(1);
                    searchType = 1;
                    time_pos = 1;
                }
            }
        });

        btn_select_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag_account == false){
                    flag_account = true;
                    spinner_select_time.setSelectedItem(0);
                    time_pos = 0;
                    spinner_select_category.setSelectedItem(0);
                    category_pos = 0;
                    searchType = 8;
                    btn_select_account.setTextColor(Color.parseColor("#1E90FF"));
                }
                else {
                    flag_account = false;
                    spinner_select_time.setSelectedIndex(1);
                    time_pos = 1;
                    searchType = 1;
                    btn_select_account.setTextColor(Color.parseColor("#000000"));
                }
            }
        });
    }

    private void LoadData(){
        List<LineElement> lle = new LinkedList<>();
        for (List<List<Bill>> llb:data){
            List<List<View>> llv = new LinkedList<>();
            for (List<Bill> lb :llb){
                List<View> lv = new LinkedList<>();
                for (Bill b:lb){
                    lv.add(BillToViewAdapter(b));
                }
                //LineElementChild lec = new LineElementChild();
            }
        }
    }

    public View BillToViewAdapter(Bill bill){
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View view = mInflater.inflate(R.layout.adapter_bill,null);
        if (bill == null){
            return null;
        }else {
            TextView tv_day = view.findViewById(R.id.tv_day);
            TextView tv_day_of_week = view.findViewById(R.id.tv_day_of_week);
            TextView tv_secondCategory = view.findViewById(R.id.tv_second_category);
            TextView tv_time = view.findViewById(R.id.tv_time);
            TextView tv_account = view.findViewById(R.id.tv_account);
            TextView tv_remark = view.findViewById(R.id.tv_remark);
            TextView tv_money = view.findViewById(R.id.tv_money);

            tv_day.setText(bill.getDay());
            tv_day_of_week.setText(bill.getDayOfWeek());
            tv_secondCategory.setText(bill.getSecondCategory());
            tv_time.setText(bill.getTime());
            tv_account.setText(bill.getAccount());
            tv_remark.setText(bill.getRemark());
            tv_money.setText(bill.getMoney().toString());
        }
        return view;
    }

    private BigDecimal getLineElementChildIn(List<Bill> lb){
        BigDecimal in = new BigDecimal("0");
        for (Bill b:lb){
            if (b.getType() == 0){
                in = in.add(b.getMoney());
            }
        }
        return in;
    }
}