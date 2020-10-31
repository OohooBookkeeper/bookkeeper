package com.example.jizhangdemo.add;

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
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;
import com.xuexiang.xutil.data.DateUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTransferFragment extends Fragment {

    private EditText add_transfer_et_money,add_transfer_et_remark;
    private Button add_transfer_btn_picker_time,add_transfer_btn_save,add_transfer_btn_member_enable;
    private MaterialSpinner add_transfer_spinner_account_out,add_transfer_spinner_account_in,add_transfer_spinner_member;
    private TextView add_transfer_tv_time;
    private String systemTime,username,remark;
    private TimePickerView mTimePickerDialog;
    private String[] account = new String[]{"微信","支付宝","银行卡"};
    private String[] member = new String[]{"无","父亲","母亲","儿子"};
    private int account_out_pos = 1,account_in_pos = 1,member_pos;
    private boolean mWidgetEnable = true;
    private Message message;
    private BigDecimal money;
    private long time;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_transfer,container,false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            username = bundle.getString("username");
        }
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        message = Return_message.Return(getActivity(),2,username);
        add_transfer_et_money = view.findViewById(R.id.add_transfer_et_money);
        add_transfer_spinner_account_out = view.findViewById(R.id.add_transfer_spinner_account_out);
        add_transfer_spinner_account_in = view.findViewById(R.id.add_transfer_spinner_account_in);
        add_transfer_btn_picker_time = view.findViewById(R.id.add_transfer_btn_picker_time);
        add_transfer_et_remark = view.findViewById(R.id.add_transfer_et_remark);
        add_transfer_spinner_member = view.findViewById(R.id.add_transfer_spinner_member);
        add_transfer_tv_time = view.findViewById(R.id.add_transfer_tv_time);
        add_transfer_btn_save = view.findViewById(R.id.add_transfer_btn_save);
        add_transfer_btn_member_enable = view.findViewById(R.id.add_transfer_btn_member_enable);

        add_transfer_spinner_account_out.setItems(message.Account_name);
        add_transfer_spinner_account_out.setOnItemSelectedListener((spinner,position,id,item)->account_out_pos = position + 1);

        add_transfer_spinner_account_in.setItems(message.Account_name);
        add_transfer_spinner_account_in.setOnItemSelectedListener((spinner,position,id,item)->account_in_pos = position + 1);

        add_transfer_btn_picker_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date(System.currentTimeMillis());
                systemTime = sd.format(date);
                showTimePickerDialog();
            }
        });

        add_transfer_btn_member_enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWidgetEnable = !mWidgetEnable;
                add_transfer_spinner_member.setEnabled(mWidgetEnable);
                if (mWidgetEnable == false){
                    add_transfer_btn_member_enable.setText("可选成员");
                    add_transfer_spinner_member.setSelectedIndex(0);
                    member_pos = -1;
                }else {
                    add_transfer_btn_member_enable.setText("不选成员");
                }
            }
        });
        add_transfer_spinner_member.setItems(message.Member_name);
        add_transfer_spinner_member.setOnItemSelectedListener((spinner,position,id,item)->member_pos = position);

        add_transfer_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(add_transfer_et_money.getText().toString())){
                    Toast.makeText(getActivity(),"未输入金额",Toast.LENGTH_SHORT).show();
                }else {
                    money = new BigDecimal(add_transfer_et_money.getText().toString());
                    money = money.setScale(2);
                    remark = add_transfer_et_remark.getText().toString();
                    if (New_bookkeeping.New_bookkeeping_transfer(getActivity(),username,money,
                            account_out_pos,account_in_pos,member_pos,time,remark) == 0){
                        Toast.makeText(getActivity(),"保存成功",Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }else {
                        Toast.makeText(getActivity(),"保存失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * 时间选择器
     */
    private void showTimePickerDialog() {
        if (mTimePickerDialog == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.string2Date(systemTime, DateUtils.yyyyMMddHHmm.get()));
            mTimePickerDialog = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                @Override
                public void onTimeSelected(Date date, View v) {
                    add_transfer_tv_time.setText(DateUtils.date2String(date, DateUtils.yyyyMMddHHmm.get()));
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
    public static AddTransferFragment newInstance(String text){
        AddTransferFragment fragment = new AddTransferFragment();
        Bundle bundle = new Bundle();
        bundle.putString("username",text);
        fragment.setArguments(bundle);
        return fragment;
    }
}
