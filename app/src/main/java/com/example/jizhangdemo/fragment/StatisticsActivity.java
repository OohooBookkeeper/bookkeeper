package com.example.jizhangdemo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jizhangdemo.R;
import com.example.jizhangdemo.add.BookHelper;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.xuexiang.xui.utils.ColorUtils;
import com.xuexiang.xui.widget.picker.widget.TimePickerView;
import com.xuexiang.xui.widget.picker.widget.builder.TimePickerBuilder;
import com.xuexiang.xui.widget.picker.widget.listener.OnTimeSelectListener;
import com.xuexiang.xui.widget.spinner.materialspinner.MaterialSpinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.lang.Math.abs;

public class StatisticsActivity extends Fragment implements OnChartValueSelectedListener {

    private MaterialSpinner spinner_select_in_or_out,spinner_select_how_to_show;
    private Button btn_DateStart,btn_DateEnd;
    private String[] in_or_out = new String[]{"支出","收入"};
    private String[] how_to_show = new String[]{"一级分类","二级分类","成员"};
    private int select_in_or_out,searchType;
    private PieChart mPieChart;
    private List<PieEntry> mGraphData;
    private Date DateStart,DateEnd;
    private com.example.jizhangdemo.journalAccount.Display display;
    private BookHelper bkhp;
    private String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_statistics,container,false);
        Bundle bundle = this.getArguments();
        if (bundle != null){
            username = bundle.getString("username");
        }
        bkhp = new BookHelper(getActivity(), username+".db");
        display = new com.example.jizhangdemo.journalAccount.Display(bkhp);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner_select_in_or_out = view.findViewById(R.id.spinner_select_in_or_out);
        spinner_select_how_to_show = view.findViewById(R.id.spinner_select_how_to_show);
        btn_DateEnd = view.findViewById(R.id.btn_chart_DateEnd);
        btn_DateStart = view.findViewById(R.id.btn_chart_DateStart);
        mPieChart = view.findViewById(R.id.chart);

        InitSpinner();
        init_time_picker();
        initChartStyle();
        if (mGraphData != null && !mGraphData.isEmpty()){
            initPieChart();
        }else {
            Toast.makeText(getActivity(),"无有效数据",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadData();
    }

    private void InitSpinner(){
        spinner_select_in_or_out.setItems(in_or_out);
        spinner_select_how_to_show.setItems(how_to_show);

        spinner_select_in_or_out.setSelectedIndex(0);
        select_in_or_out = 0;

        spinner_select_how_to_show.setSelectedIndex(0);
        searchType = 1;

        spinner_select_in_or_out.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                select_in_or_out = position;
                LoadData();
            }
        });

        spinner_select_how_to_show.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                searchType = position + 1;
                LoadData();
            }
        });
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

    protected void initChartStyle() {
        //使用百分百显示
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        //设置拖拽的阻尼，0为立即停止
        mPieChart.setDragDecelerationFrictionCoef(0.95f);

        //设置初始值
        searchType = 1;
        select_in_or_out = 0;
        display.notifyDatasetChanged();
        mGraphData = display.getStatsByCtg(DateStart,DateEnd,select_in_or_out);

        //设置图标中心文字
        mPieChart.setCenterTextSize(20);
        mPieChart.setCenterText(new SpannableString("总计\n"));

        mPieChart.setDrawCenterText(true);
        //设置图标中心空白，空心
        mPieChart.setDrawHoleEnabled(true);
        //设置空心圆的弧度百分比，最大100
        mPieChart.setHoleRadius(50f);
        mPieChart.setHoleColor(Color.WHITE);
        //设置透明弧的样式
        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);
        mPieChart.setTransparentCircleRadius(61f);

        //设置可以旋转
        mPieChart.setRotationAngle(0);
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        mPieChart.setOnChartValueSelectedListener(this);
    }

    private void initPieChart(){
        PieDataSet dataSet = new PieDataSet(mGraphData,"");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        int newColor= ColorUtils.getRandomColor();
        while(ColorUtils.isColorDark(newColor)){
            newColor=ColorUtils.getRandomColor();
        }
        for (int i=0;i<=mGraphData.size();i++){
            colors.add(newColor);
            newColor += 80;
        }
        dataSet.setColors(colors);

        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(mPieChart));
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);

        Description description = new Description();
        description.setText("");
        mPieChart.setDescription(description);
        mPieChart.setData(pieData);
        mPieChart.invalidate();
    }

    private void LoadData(){
        display.notifyDatasetChanged();
        switch (searchType){
            case 1:
                mGraphData = display.getStatsByCtg(DateStart,DateEnd,select_in_or_out);
                break;
            case 2:
                mGraphData = display.getStatsBySctg(DateStart,DateEnd,select_in_or_out);
                break;
            case 3:
                mGraphData = display.getStatsByMember(DateStart,DateEnd,select_in_or_out);
                break;
            default:
                Toast.makeText(getActivity(),"非法排序依据！",Toast.LENGTH_SHORT).show();
        }
        if(!mGraphData.isEmpty()){
            mPieChart.setCenterText(new SpannableString("总计\n"));
            initPieChart();
        }
        else{
            Toast.makeText(getActivity(),"无符号数据",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}