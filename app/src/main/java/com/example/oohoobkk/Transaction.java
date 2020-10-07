package com.example.oohoobkk;

// 账目类
public class Transaction {
    public static final int UNSPECIFIED = 0; // 赋值给member, trader或project，表示这些属性未指定
    public static final int NONTRANSFER = 0; // 赋值给outaccount，表示这笔账目不是转账账目
    public int amount;
    public int category;
    public int subcategory;
    public int account;
    public int outaccount;
    public int time;
    public int member;
    public int trader;
    public int project;
    public String note;
}
