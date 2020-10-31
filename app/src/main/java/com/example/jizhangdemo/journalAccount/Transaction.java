package com.example.jizhangdemo.journalAccount;

import java.util.Date;

public class Transaction {
    public static final int UNSPECIFIED = 0; // 赋值给subcategory, member, trader或project，表示这些属性未指定

    public static final int NONTRANSFER = 0; // 赋值给outaccount，表示这笔账目不是转账账目

    public static final int EXPENSE = 0; // 分类为支出类型

    public static final int INCOME = 1; // 分类为收入类型

    public static final int TRANSFER = 2; // 分类为转账类型

    public int id;

    public int amount;

    public int category;

    public int type;

    public int subcategory;

    public int account;

    public int outaccount;

    public int time;

    public int member;

    public int trader;

    public int project;

    public String note;

    public String cname;

    public String sname;

    public String aname;

    public String oaname;

    public String mname;

    public String rname;

    public String pname;

    public Transaction copy() {

        Transaction t = new Transaction();

        t.id = this.id;

        t.amount = this.amount;

        t.category = this.category;

        t.type = this.type;

        t.subcategory = this.subcategory;

        t.account = this.account;

        t.outaccount = this.outaccount;

        t.time = this.time;

        t.member = this.member;

        t.trader = this.trader;

        t.project = this.project;

        t.note = this.note;

        t.cname = this.cname;

        t.sname = this.sname;

        t.aname = this.aname;

        t.oaname = this.oaname;

        t.mname = this.mname;

        t.rname = this.rname;

        t.pname = this.pname;

        return t;

    }

    public String toString() {
        return (new Date((long) this.time * 60000)).toString();
    }
}
