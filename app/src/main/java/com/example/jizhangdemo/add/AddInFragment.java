package com.example.jizhangdemo.add;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jizhangdemo.R;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;
import com.xuexiang.xui.widget.picker.widget.OptionsPickerView;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.OptionsPickerBuilder;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;
import com.xuexiang.xutil.data.DateUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddInFragment extends Fragment {

    private EditText add_in_et_money,add_in_et_remark;
    private Button add_in_btn_picker_classification,add_in_btn_picker_time,add_in_btn_save,add_in_btn_member_enable;
    private MaterialSpinner add_in_spinner_account,add_in_spinner_member;
    private List<FirstCategory> options1Items = new ArrayList<>();
    private List<List<String>> options2Items = new ArrayList<>();
    private boolean mHasLoaded = false;
    private TextView add_in_tv_classification,add_in_tv_time;
    private String tx,systemTime,remark,username,category,subcategory;
    private TimePickerView mTimePickerDialog;
    private String[] account = new String[]{"微信","支付宝","银行卡"};
    private String[] member = new String[]{"无","父亲","母亲","儿子"};
    private int account_pos,member_pos,firstCategory_pos,secondCategory_pos;
    private boolean mWidgetEnable = true,isEdit;
    private Message message;
    private long time;
    private BigDecimal money;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_in,container,false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
            isEdit = bundle.getBoolean("isEdit");
        }
        if (isEdit){
            firstCategory_pos = bundle.getInt("category");
            secondCategory_pos = bundle.getInt("subcategory");
            time = (long)bundle.getInt("time") * 60000;
            account_pos = bundle.getInt("account");
            money = new BigDecimal(bundle.getInt("amount")).movePointLeft(2);
            remark = bundle.getString("remark");
            member_pos = bundle.getInt("member");
        }
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        message = Return_message.Return(getActivity(),1,username);

        init(view);

        add_in_btn_picker_classification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView();
            }
        });

        add_in_spinner_account.setOnItemSelectedListener((spinner,position,id,item)->account_pos = position + 1);

        add_in_btn_picker_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date(System.currentTimeMillis());
                systemTime = sd.format(date);
                try {
                    showTimePickerDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        add_in_btn_member_enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWidgetEnable = !mWidgetEnable;
                add_in_spinner_member.setEnabled(mWidgetEnable);
                if (mWidgetEnable == false){
                    add_in_btn_member_enable.setText("可选成员");
                    add_in_spinner_member.setSelectedIndex(0);
                    member_pos = -1;
                }else {
                    add_in_btn_member_enable.setText("不选成员");
                }
            }
        });

        add_in_spinner_member.setOnItemSelectedListener((spinner,position,id,item)->member_pos = position);

        add_in_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(add_in_et_money.getText().toString())){
                    Toast.makeText(getActivity(),"未输入金额",Toast.LENGTH_SHORT).show();
                }else {
                    money = new BigDecimal(add_in_et_money.getText().toString());
                    money = money.setScale(2);
                    remark = add_in_et_remark.getText().toString();
                    if (New_bookkeeping.New_bookkeeping_nontransfer(getActivity(),username,money,firstCategory_pos,secondCategory_pos,
                            account_pos,time,member_pos,-1,-1,remark,1) == 0){
                        Toast.makeText(getActivity(),"保存成功",Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }else {
                        Toast.makeText(getActivity(),"保存失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getComponents(View view){
        add_in_et_money = view.findViewById(R.id.add_in_et_money);
        add_in_btn_picker_classification = view.findViewById(R.id.add_in_btn_picker_classification);
        add_in_spinner_account = view.findViewById(R.id.add_in_spinner_account);
        add_in_btn_picker_time = view.findViewById(R.id.add_in_btn_picker_time);
        add_in_et_remark = view.findViewById(R.id.add_in_et_remark);
        add_in_spinner_member = view.findViewById(R.id.add_in_spinner_member);
        add_in_tv_classification = view.findViewById(R.id.add_in_tv_classification);
        add_in_tv_time = view.findViewById(R.id.add_in_tv_time);
        add_in_btn_save = view.findViewById(R.id.add_in_btn_save);
        add_in_btn_member_enable = view.findViewById(R.id.add_in_btn_member_enable);
    }
    /**
     * 分类选择器
     */
    private void InitList(){
        if (message.All_category != null)
            LoadDate(message.All_category);
    }

    private void LoadDate(List<FirstCategory> firstCategories){
        options1Items = firstCategories;
        for (FirstCategory firstCategory : firstCategories){
            List<String> secondCategory = new ArrayList<>();
            secondCategory.addAll(firstCategory.getCategory());
            options2Items.add(secondCategory);
        }
        mHasLoaded = true;
    }

    private void showPickerView(){
        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(),(v,options1,options2,options3)->{
            if (options2Items.get(options1).get(options2) == null){
                tx = options1Items.get(options1).getPickerViewText();
            }else {
                tx = options1Items.get(options1).getPickerViewText() +"-" +
                        options2Items.get(options1).get(options2);
            }
            firstCategory_pos = options1 + 1;
            secondCategory_pos = options2 + 1;
            add_in_tv_classification.setText(tx);
            return false;
        })
                .setTitleText("分类选择")
                .setDividerColor(Color.BLACK)
                //切换选项时，还原到第一项
                .isRestoreItem(true)
                //设置选中项文字颜色
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .isDialog(true)
                .setSelectOptions(firstCategory_pos, secondCategory_pos)
                .build();
        try {
            pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
            pvOptions.show();
        } catch (IndexOutOfBoundsException e) {
            Toast.makeText(getActivity(), "The category list is empty!", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 时间选择器
     */
    private void showTimePickerDialog() {
        if (mTimePickerDialog == null) {
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(add_in_tv_time.getText().toString()));
            }catch (ParseException e) {
                e.printStackTrace();
            }
            mTimePickerDialog = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                @Override
                public void onTimeSelected(Date date, View v) {
                    add_in_tv_time.setText(DateUtils.date2String(date, DateUtils.yyyyMMddHHmm.get()));
                    time = DateUtils.date2Millis(date);
                }
            })
                    .setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                    .setType(true,true,true,true,true,false)
                    .setTitleText("时间选择")
                    .isDialog(true)
                    .setOutSideCancelable(false)
                    .setDate(calendar)
                    .build();
        }
        mTimePickerDialog.show();
    }

    private void init(View view){
        getComponents(view);
        InitList();
        add_in_spinner_account.setItems(message.Account_name);
        add_in_spinner_member.setItems(message.Member_name);
        if (isEdit){
            add_in_et_money.setText(money.toPlainString());
            if (options2Items.get(firstCategory_pos - 1).get(secondCategory_pos - 1) == null){
                add_in_tv_classification.setText(options1Items.get(firstCategory_pos - 1).getPickerViewText());
            }else {
                add_in_tv_classification.setText(options1Items.get(firstCategory_pos - 1).getPickerViewText() + "-"
                        + options2Items.get(firstCategory_pos-1).get(secondCategory_pos - 1));
            }
            if (account_pos > 0){
                add_in_spinner_account.setSelectedIndex(account_pos - 1);
            }
            add_in_tv_time.setText(DateUtils.date2String(new Date(time), DateUtils.yyyyMMddHHmm.get()));
            if (remark != null){
                add_in_et_remark.setText(remark);
            }
            if (member_pos > 0){
                add_in_spinner_member.setSelectedIndex(member_pos - 1);
            }
        }else {
            add_in_tv_time.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis())));
            time = DateUtils.date2Millis(new Date(System.currentTimeMillis()));
            account_pos = 1;
        }
    }
}
