package com.example.jizhangdemo.journalAccount;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import com.example.jizhangdemo.add.BookHelper;

public class Display {

    public static final int DATE = 0;
    public static final int EX_CATEGORY = 1;
    public static final int IN_CATEGORY = 2;
    public static final int TR_CATEGORY = 3;
    public static final int EX_SUBCATEGORY = 4;
    public static final int IN_SUBCATEGORY = 5;
    public static final int TR_SUBCATEGORY = 6;
    public static final int ACCOUNT = 7;
    public static final int MEMBER = 8;
    public static final int TRADER = 9;
    public static final int PROJECT = 10;
    public static final int WEEK = 1;
    public static final int MONTH = 2;
    public static final int QUARTER = 3;
    private BookHelper bookHelper; // 要展示的账本数据库
    private List<Transaction> transactions; // 全部的账目数据
    private Map<Integer, List<Transaction>> tByDate; // 按时间对账目划分
    private Map<Integer, List<Transaction>> expenseByCtg; // 按一级分类对支出账目划分
    private Map<Integer, List<Transaction>> incomeByCtg; // 按一级分类对收入账目划分
    private Map<Integer, List<Transaction>> transferByCtg; // 按一级分类对转账账目划分
    private Map<Integer, List<Transaction>> expenseBySctg; // 按二级分类对支出账目划分
    private Map<Integer, List<Transaction>> incomeBySctg; // 按二级分类对收入账目划分
    private Map<Integer, List<Transaction>> transferBySctg; // 按二级分类对转账账目划分
    private Map<Integer, List<Transaction>> tByAccount; // 按支付账户对账目划分
    private Map<Integer, List<Transaction>> tByMember; // 按成员对账目划分
    private Map<Integer, List<Transaction>> tByTrader; // 按商家对账目划分
    private Map<Integer, List<Transaction>> tByProject; // 按项目对账目划分


    private class Comp implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            if (o1 < o2)
                return 1;
            if (o1 > o2)
                return -1;
            return 0;
        }
    }

    // 从数据库中加载数据

    public Display(BookHelper bh) {
        this.bookHelper = bh;
        this.loadData();
        this.createMap();
    }

    private void loadData() {
        this.transactions = new LinkedList<>();
        Cursor c = bookHelper.displayAllTransactions(BookHelper.TIME, true); // 按时间从晚到早排序
        if (c.moveToFirst()) {
            do {
                Transaction t = new Transaction();
                t.id = c.getInt(c.getColumnIndex("id"));
                t.amount = c.getInt(c.getColumnIndex("amount"));
                t.category = c.getInt(c.getColumnIndex("category"));
                t.type = c.getInt(c.getColumnIndex("type"));
                t.cname = c.getString(c.getColumnIndex("cname"));
                t.subcategory = c.getInt(c.getColumnIndex("subcategory"));
                t.sname = c.getString(c.getColumnIndex("sname"));
                t.account = c.getInt(c.getColumnIndex("account"));
                t.aname = c.getString(c.getColumnIndex("aname"));
                t.outaccount = c.getInt(c.getColumnIndex("outaccount"));
                t.oaname = c.getString(c.getColumnIndex("oaname"));
                t.time = c.getInt(c.getColumnIndex("time"));
                t.member = c.getInt(c.getColumnIndex("member"));
                t.mname = c.getString(c.getColumnIndex("mname"));
                t.trader = c.getInt(c.getColumnIndex("trader"));
                t.rname = c.getString(c.getColumnIndex("rname"));
                t.project = c.getInt(c.getColumnIndex("project"));
                t.pname = c.getString(c.getColumnIndex("pname"));
                t.note = c.getString(c.getColumnIndex("note"));
                this.transactions.add(t);
            } while (c.moveToNext());
            Collections.reverse(transactions);
        }
    }


    // 构造函数，要传入待展示的账本数据库

    private void createMap() {

        tByDate = new TreeMap<>(new Comp());

        expenseByCtg = new TreeMap<>(new Comp());

        incomeByCtg = new TreeMap<>(new Comp());

        transferByCtg = new TreeMap<>(new Comp());

        expenseBySctg = new TreeMap<>(new Comp());

        incomeBySctg = new TreeMap<>(new Comp());

        transferBySctg = new TreeMap<>(new Comp());

        tByAccount = new TreeMap<>(new Comp());

        tByMember = new TreeMap<>(new Comp());

        tByTrader = new TreeMap<>(new Comp());

        tByProject = new TreeMap<>(new Comp());

        for (Transaction t : transactions) {

            // 按时间划分账目

            Date d = new Date((long) t.time * 60000);

            int year, month, monthPassed;

            Calendar c = Calendar.getInstance();

            c.setTime(d);

            year = c.get(Calendar.YEAR);

            month = c.get(Calendar.MONTH);

            monthPassed = (year - 1970) * 12 + month - Calendar.JANUARY; // 计算从1970年1月到现在经过了几个月

            if (tByDate.containsKey(monthPassed))

                tByDate.get(monthPassed).add(t);

            else {

                List<Transaction> tInMonth = new LinkedList<>();

                tInMonth.add(t);

                tByDate.put(monthPassed, tInMonth);

            }

            // 按一级分类和二级分类划分账目

            switch (t.type) {

                case Transaction.EXPENSE:

                    if (expenseByCtg.containsKey(t.category))

                        expenseByCtg.get(t.category).add(t);

                    else {

                        List<Transaction> expenseInCtg = new LinkedList<>();

                        expenseInCtg.add(t);

                        expenseByCtg.put(t.category, expenseInCtg);

                    }

                    if (expenseBySctg.containsKey(t.subcategory))

                        expenseBySctg.get(t.subcategory).add(t);

                    else {

                        List<Transaction> expenseInSctg = new LinkedList<>();

                        expenseInSctg.add(t);

                        expenseBySctg.put(t.subcategory, expenseInSctg);

                    }

                    break;

                case Transaction.INCOME:

                    if (incomeByCtg.containsKey(t.category))

                        incomeByCtg.get(t.category).add(t);

                    else {

                        List<Transaction> incomeInCtg = new LinkedList<>();

                        incomeInCtg.add(t);

                        incomeByCtg.put(t.category, incomeInCtg);

                    }

                    if (incomeBySctg.containsKey(t.subcategory))

                        incomeBySctg.get(t.subcategory).add(t);

                    else {

                        List<Transaction> incomeInSctg = new LinkedList<>();

                        incomeInSctg.add(t);

                        incomeBySctg.put(t.subcategory, incomeInSctg);

                    }

                    break;

                case Transaction.TRANSFER:

                    if (transferByCtg.containsKey(t.category))

                        transferByCtg.get(t.category).add(t);

                    else {

                        List<Transaction> transferInCtg = new LinkedList<>();

                        transferInCtg.add(t);

                        transferByCtg.put(t.category, transferInCtg);

                    }

                    if (transferBySctg.containsKey(t.subcategory))

                        transferBySctg.get(t.subcategory).add(t);

                    else {

                        List<Transaction> transferInSctg = new LinkedList<>();

                        transferInSctg.add(t);

                        transferBySctg.put(t.subcategory, transferInSctg);

                    }

                    break;

                default: // 无效账目

                    break;

            }

            // 按支付账户划分账目

            if (t.outaccount == Transaction.NONTRANSFER) {

                if (tByAccount.containsKey(t.account))

                    tByAccount.get(t.account).add(t);

                else {

                    List<Transaction> tOfAccount = new LinkedList<>();

                    tOfAccount.add(t);

                    tByAccount.put(t.account, tOfAccount);

                }

            } else {

                Transaction tout = t.copy(); // 转出账户

                Transaction tin = t.copy(); // 转入账户

                tout.amount = -tout.amount;

                tin.account = tout.outaccount;

                if (tByAccount.containsKey(tout.account))

                    tByAccount.get(tout.account).add(tout);

                else {

                    List<Transaction> tOfAccount = new LinkedList<>();

                    tOfAccount.add(tout);

                    tByAccount.put(tout.account, tOfAccount);

                }

                if (tByAccount.containsKey(tin.account))

                    tByAccount.get(tin.account).add(tin);

                else {

                    List<Transaction> tOfAccount = new LinkedList<>();

                    tOfAccount.add(tin);

                    tByAccount.put(tin.account, tOfAccount);

                }

            }

            // 按成员划分账目

            if (tByMember.containsKey(t.member))

                tByMember.get(t.member).add(t);

            else {

                List<Transaction> tOfMember = new LinkedList<>();

                tOfMember.add(t);

                tByMember.put(t.member, tOfMember);

            }

            // 按商家划分账目

            if (tByTrader.containsKey(t.trader))

                tByTrader.get(t.trader).add(t);

            else {

                List<Transaction> tOfTrader = new LinkedList<>();

                tOfTrader.add(t);

                tByTrader.put(t.trader, tOfTrader);

            }

            // 按项目划分账目

            if (tByProject.containsKey(t.project))

                tByProject.get(t.project).add(t);

            else {

                List<Transaction> tOfProject = new LinkedList<>();

                tOfProject.add(t);

                tByProject.put(t.project, tOfProject);

            }

        }

    }


    // 对数据库做了删除或修改后，请及时调用这个函数

    public void notifyDatasetChanged() {

        this.loadData();

        this.createMap();

    }


    // 获得统计信息

    public List<PieEntry> getStatsByCtg(Date startDate, Date endDate, int type) {
        List<PieEntry> eList = new ArrayList<>();
        Map<Integer, Integer> stats = new HashMap<>();
        Map<Integer, String> ctgs = new HashMap<>();
        float sum = 0;
        for (Transaction t : transactions) {
            Date d = new Date((long) t.time * 60000);
            if (d.before(startDate) || d.after(endDate))
                continue;
            switch (type) {
                case Transaction.EXPENSE:
                    if (t.outaccount == Transaction.NONTRANSFER && t.amount < 0) {
                        if (stats.containsKey(t.category)) {
                            int value = stats.get(t.category);
                            value += -t.amount;
                            stats.put(t.category, value);
                        } else {
                            stats.put(t.category, -t.amount);
                        }
                        sum += -t.amount;
                    }
                    break;
                case Transaction.INCOME:
                    if (t.outaccount == Transaction.NONTRANSFER && t.amount > 0) {
                        if (stats.containsKey(t.category)) {
                            int value = stats.get(t.category);
                            value += t.amount;
                            stats.put(t.category, value);
                        } else {
                            stats.put(t.category,t.amount);
                        }
                        sum += t.amount;
                    }
                    break;
                case Transaction.TRANSFER:
                    if (t.outaccount != Transaction.NONTRANSFER) {
                        if (stats.containsKey(t.category)) {
                            int value = stats.get(t.category);
                            value += t.amount;
                            stats.put(t.category, value);
                        } else {
                            stats.put(t.category,t.amount);
                        }
                        sum += t.amount;
                    }
                    break;
                default:
                    throw new IllegalStateException("Illegal value " + type);
            }
        }
        Cursor c = bookHelper.rawQuery("select category, name from Category", null);
        ctgs.put(0, "无分类");
        if (c.moveToFirst()) {
            do {
                int category = c.getInt(c.getColumnIndex("category"));
                String name = c.getString(c.getColumnIndex("name"));
                ctgs.put(category, name);
            } while (c.moveToNext());
        }
        int i = 1;
        for (int category : stats.keySet()) {
            String label = ctgs.containsKey(category) ? ctgs.get(category) : "未知分类" + i++;
            float value = (float) stats.get(category) / sum;
            eList.add(new PieEntry(value, label));
        }
        return eList;
    }

    public List<PieEntry> getStatsBySctg(Date startDate, Date endDate, int type) {
        List<PieEntry> eList = new ArrayList<>();
        Map<Integer, Integer> stats = new HashMap<>();
        Map<Integer, String> sctgs = new HashMap<>();
        float sum = 0;
        for (Transaction t : transactions) {
            Date d = new Date((long) t.time * 60000);
            if (d.before(startDate) || d.after(endDate))
                continue;
            switch (type) {
                case Transaction.EXPENSE:
                    if (t.outaccount == Transaction.NONTRANSFER && t.amount < 0) {
                        if (stats.containsKey(t.subcategory)) {
                            int value = stats.get(t.subcategory);
                            value += -t.amount;
                            stats.put(t.subcategory, value);
                        } else {
                            stats.put(t.subcategory, -t.amount);
                        }
                        sum += -t.amount;
                    }
                    break;
                case Transaction.INCOME:
                    if (t.outaccount == Transaction.NONTRANSFER && t.amount > 0) {
                        if (stats.containsKey(t.subcategory)) {
                            int value = stats.get(t.subcategory);
                            value += t.amount;
                            stats.put(t.subcategory, value);
                        } else {
                            stats.put(t.subcategory,t.amount);
                        }
                        sum += t.amount;
                    }
                    break;
                case Transaction.TRANSFER:
                    if (t.outaccount != Transaction.NONTRANSFER) {
                        if (stats.containsKey(t.subcategory)) {
                            int value = stats.get(t.subcategory);
                            value += t.amount;
                            stats.put(t.subcategory, value);
                        } else {
                            stats.put(t.subcategory,t.amount);
                        }
                        sum += t.amount;
                    }
                    break;
                default:
                    throw new IllegalStateException("Illegal value " + type);
            }
        }
        Cursor c = bookHelper.rawQuery("select subcategory, name from Subcategory", null);
        sctgs.put(0, "无分类");
        if (c.moveToFirst()) {
            do {
                int subcategory = c.getInt(c.getColumnIndex("subcategory"));
                String name = c.getString(c.getColumnIndex("name"));
                sctgs.put(subcategory, name);
            } while (c.moveToNext());
        }
        int i = 1;
        for (int subcategory : stats.keySet()) {
            String label = sctgs.containsKey(subcategory) ? sctgs.get(subcategory) : "未知分类" + i++;
            float value = (float) stats.get(subcategory) / sum;
            eList.add(new PieEntry(value, label));
        }
        return eList;
    }

    public List<PieEntry> getStatsByMember(Date startDate, Date endDate, int type) {
        List<PieEntry> eList = new ArrayList<>();
        Map<Integer, Integer> stats = new HashMap<>();
        Map<Integer, String> members = new HashMap<>();
        float sum = 0;
        for (Transaction t : transactions) {
            Date d = new Date((long) t.time * 60000);
            if (d.before(startDate) || d.after(endDate))
                continue;
            switch (type) {
                case Transaction.EXPENSE:
                    if (t.outaccount == Transaction.NONTRANSFER && t.amount < 0) {
                        if (stats.containsKey(t.member)) {
                            int value = stats.get(t.member);
                            value += -t.amount;
                            stats.put(t.member, value);
                        } else {
                            stats.put(t.member, -t.amount);
                        }
                        sum += -t.amount;
                    }
                    break;
                case Transaction.INCOME:
                    if (t.outaccount == Transaction.NONTRANSFER && t.amount > 0) {
                        if (stats.containsKey(t.member)) {
                            int value = stats.get(t.member);
                            value += t.amount;
                            stats.put(t.member, value);
                        } else {
                            stats.put(t.member,t.amount);
                        }
                        sum += t.amount;
                    }
                    break;
                case Transaction.TRANSFER:
                    if (t.outaccount != Transaction.NONTRANSFER) {
                        if (stats.containsKey(t.member)) {
                            int value = stats.get(t.member);
                            value += t.amount;
                            stats.put(t.member, value);
                        } else {
                            stats.put(t.member,t.amount);
                        }
                        sum += t.amount;
                    }
                    break;
                default:
                    throw new IllegalStateException("Illegal value " + type);
            }
        }
        Cursor c = bookHelper.rawQuery("select member, name from Member", null);
        members.put(0, "无成员");
        if (c.moveToFirst()) {
            do {
                int member = c.getInt(c.getColumnIndex("member"));
                String name = c.getString(c.getColumnIndex("name"));
                members.put(member, name);
            } while (c.moveToNext());
        }
        int i = 1;
        for (int member : stats.keySet()) {
            String label = members.containsKey(member) ? members.get(member) : "未知成员" + i++;
            float value = (float) stats.get(member) / sum;
            eList.add(new PieEntry(value, label));
        }
        return eList;
    }


    // 获取流水展示的数据，按年划分

    public Map<Integer, Map<Integer, List<Transaction>>> displayByYear(Date startDate, Date endDate) {
        Map<Integer, Map<Integer, List<Transaction>>> data = new TreeMap<>(new Comp());
        int startYear, startMonth, endYear, endMonth;
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        startYear = c.get(Calendar.YEAR);
        startMonth = c.get(Calendar.MONTH);
        c.setTime(endDate);
        endYear = c.get(Calendar.YEAR);
        endMonth = c.get(Calendar.MONTH);
        for (int monthsPassed : this.tByDate.keySet()) {
            int monthsPassedOfStartDate = (startYear - 1970) * 12 + startMonth - Calendar.JANUARY;
            int monthsPassedOfEndDate = (endYear - 1970) * 12 + endMonth - Calendar.JANUARY;
            if (monthsPassed >= monthsPassedOfStartDate && monthsPassed <= monthsPassedOfEndDate && tByDate.containsKey(monthsPassed)) {
                for (Transaction t : tByDate.get(monthsPassed)) {
                    Date d = new Date((long) t.time * 60000);
                    if (!d.before(startDate) && !d.after(endDate)) {
                        int year = monthsPassed / 12;
                        int month = monthsPassed % 12;
                        if (!data.containsKey(year)) {
                            Map<Integer, List<Transaction>> yearData = new TreeMap<>(new Comp());
                            List<Transaction> tList = new LinkedList<>();
                            tList.add(t);
                            yearData.put(month, tList);
                            data.put(year, yearData);
                        } else if (!data.get(year).containsKey(month)) {
                            List<Transaction> tList = new LinkedList<>();
                            tList.add(t);
                            data.get(year).put(month, tList);
                        } else {

                            data.get(year).get(month).add(t);

                        }

                    }

                }

            }

        }

        return data;

    }


    // 获取流水展示的数据，按季度、月、周、日、成员、商家、项目划分

    public Map<Integer, List<Transaction>> displayBy(Date startDate, Date endDate, int type) {
        Map<Integer, List<Transaction>> srcMap;
        Map<Integer, List<Transaction>> data;
        boolean special = false;
        switch (type) {
            case Display.DATE:
            case Display.WEEK:
            case Display.QUARTER:
                srcMap = this.tByDate;
                data = new TreeMap<>(new Comp());
                special = true;
                break;
            case Display.MONTH:
                srcMap = this.tByDate;
                data = new TreeMap<>(new Comp());
                break;
            case Display.MEMBER:
                srcMap = this.tByMember;
                data = new TreeMap<>();
                break;
            case Display.TRADER:
                srcMap = this.tByTrader;
                data = new TreeMap<>();
                break;
            case Display.PROJECT:
                srcMap = this.tByProject;
                data = new TreeMap<>();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        if (special) {
            for (int by : srcMap.keySet()) {
                for (Transaction t : srcMap.get(by)) {
                    Date d = new Date((long) t.time * 60000);
                    Calendar c = Calendar.getInstance();
                    c.setTime(d);
                    if (!d.before(startDate) && !d.after(endDate)) {
                        int key;
                        switch (type) {
                            case Display.DATE:
                                key = by * 100 + c.get(Calendar.DATE);
                                break;
                            case Display.WEEK:
                                key = c.get(Calendar.YEAR) * 100 + c.get(Calendar.WEEK_OF_YEAR);
                                break;
                            case Display.QUARTER:
                                key = by / 3;
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + type);
                        }
                        if (data.containsKey(key)) {
                            data.get(key).add(t);
                        } else {
                            List<Transaction> tList = new LinkedList<>();
                            tList.add(t);
                            data.put(key, tList);
                        }
                    }
                }
            }
        } else {
            for (int by : srcMap.keySet()) {
                for (Transaction t : srcMap.get(by)) {
                    Date d = new Date((long) t.time * 60000);
                    if (!d.before(startDate) && !d.after(endDate)) {
                        if (data.containsKey(by)) {
                            data.get(by).add(t);
                        } else {
                            List<Transaction> tList = new LinkedList<>();
                            tList.add(t);
                            data.put(by, tList);
                        }
                    }
                }
            }
        }
        return data;
    }


    // 获取流水展示的数据，按一级分类或二级分类以及分类的类型（收入、支出、转账）划分

    // sub表示是否按二级分类展示，type表示分类的类型，比如如果传入Transaction.EXPENSE，则仅返回支出分类的数据

    public Map<Integer, List<Transaction>> displayByCtg(Date startDate, Date endDate, boolean sub, int type) {

        Map<Integer, List<Transaction>> srcMap;

        if (sub) {

            switch (type) {

                case Transaction.EXPENSE:

                    srcMap = this.expenseBySctg;

                    break;

                case Transaction.INCOME:

                    srcMap = this.incomeBySctg;

                    break;

                case Transaction.TRANSFER:

                    srcMap = this.transferBySctg;

                    break;

                default:

                    throw new IllegalStateException("Unexpected value: " + type);

            }

        } else {

            switch (type) {

                case Transaction.EXPENSE:

                    srcMap = this.expenseByCtg;

                    break;

                case Transaction.INCOME:

                    srcMap = this.incomeByCtg;

                    break;

                case Transaction.TRANSFER:

                    srcMap = this.transferByCtg;

                    break;

                default:

                    throw new IllegalStateException("Unexpected value: " + type);

            }

        }

        Map<Integer, List<Transaction>> data = new TreeMap<>();

        for (int by : srcMap.keySet()) {

            for (Transaction t : srcMap.get(by)) {

                Date d = new Date((long) t.time * 60000);

                if (!d.before(startDate) && !d.after(endDate)) {

                    if (data.containsKey(by)) {

                        data.get(by).add(t);

                    } else {

                        List<Transaction> tList = new LinkedList<>();

                        tList.add(t);

                        data.put(by, tList);

                    }

                }

            }

        }

        return data;

    }


    // 获取流水展示的数据，按支付账户划分，可选择年度统计或月度统计

    // month表示是否月度统计，true为月度统计（键是1970年 1月至今经过的月数），false为年度统计（键是年份）

    public Map<Integer, Map<Integer, List<Transaction>>> displayByAccount(Date startDate, Date endDate, boolean month) {
        Map<Integer, Map<Integer, List<Transaction>>> data = new TreeMap<>();
        for (int account : this.tByAccount.keySet()) {
            Map<Integer, List<Transaction>> dateMap = new TreeMap<>(new Comp());
            for (Transaction t : tByAccount.get(account)) {
                Date d = new Date((long) t.time * 60000);
                if (d.before(startDate) || d.after(endDate))
                    continue;
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int yearPassed = c.get(Calendar.YEAR) - 1970;
                int monthPassed = c.get(Calendar.MONTH) - Calendar.JANUARY;
                int key;
                if (month) {
                    key = yearPassed * 12 + monthPassed;
                } else {
                    key = c.get(Calendar.YEAR);
                }
                if (dateMap.containsKey(key)) {
                    dateMap.get(key).add(t);
                } else {
                    List<Transaction> tList = new LinkedList<>();
                    tList.add(t);
                    dateMap.put(key, tList);
                }
            }
            data.put(account, dateMap);
        }
        return data;
    }

}
