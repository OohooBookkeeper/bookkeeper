package com.example.jizhangdemo.add;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jizhangdemo.R;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;
import com.xuexiang.xutil.data.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTransferFragment extends Fragment {

    private EditText add_transfer_et_money,add_transfer_et_remark;
    private Button add_transfer_btn_picker_time,add_transfer_btn_save,add_transfer_btn_member_enable;
    private MaterialSpinner add_transfer_spinner_account_out,add_transfer_spinner_account_in,add_transfer_spinner_member;
    private TextView add_transfer_tv_time;
    private String systemTime;
    private TimePickerView mTimePickerDialog;
    private String[] account = new String[]{"微信","支付宝","银行卡"};
    private String[] member = new String[]{"无","父亲","母亲","儿子"};
    private int account_out_pos,account_in_pos,member_pos;
    private boolean mWidgetEnable = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_transfer,container,false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add_transfer_et_money = view.findViewById(R.id.add_transfer_et_money);
        add_transfer_spinner_account_out = view.findViewById(R.id.add_transfer_spinner_account_out);
        add_transfer_spinner_account_in = view.findViewById(R.id.add_transfer_spinner_account_in);
        add_transfer_btn_picker_time = view.findViewById(R.id.add_transfer_btn_picker_time);
        add_transfer_et_remark = view.findViewById(R.id.add_transfer_et_remark);
        add_transfer_spinner_member = view.findViewById(R.id.add_transfer_spinner_member);
        add_transfer_tv_time = view.findViewById(R.id.add_transfer_tv_time);
        add_transfer_btn_save = view.findViewById(R.id.add_transfer_btn_save);
        add_transfer_btn_member_enable = view.findViewById(R.id.add_transfer_btn_member_enable);

        add_transfer_spinner_account_out.setItems(account);
        add_transfer_spinner_account_out.setOnItemSelectedListener((spinner,position,id,item)->account_out_pos = position);

        add_transfer_spinner_account_in.setItems(account);
        add_transfer_spinner_account_in.setOnItemSelectedListener((spinner,position,id,item)->account_in_pos = position);

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
                }else {
                    add_transfer_btn_member_enable.setText("不选成员");
                }
            }
        });
        add_transfer_spinner_member.setItems(member);
        add_transfer_spinner_member.setOnItemSelectedListener((spinner,position,id,item)->member_pos = position);
    }

    /**
     * 时间选择器
     */
    private void showTimePickerDialog() {
        if (mTimePickerDialog == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.string2Date(systemTime, DateUtils.yyyyMMddHHmm.get()));
            mTimePickerDialog = new TimePickerBuilder(getContext(), (date, v) -> add_transfer_tv_time.setText(DateUtils.date2String(date, DateUtils.yyyyMMddHHmm.get())))
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
}
