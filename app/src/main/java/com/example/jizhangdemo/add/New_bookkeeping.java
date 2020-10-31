package com.example.jizhangdemo.add;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.database.Cursor;
import android.icu.math.MathContext;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class New_bookkeeping extends AppCompatActivity {
    static BookHelper bookhelper;
    public static int New_bookkeeping_nontransfer(Context context, String username, BigDecimal Amount, int Category , int Subcategory , int Account, long Time, int Member, int Project, int Trader, String Note, int Expense_or_income ,String category_name, String subcategory_name, String account_name, String member_name){
        bookhelper = new BookHelper(context, username+".db");
        com.example.jizhangdemo.journalAccount.Transaction T = new com.example.jizhangdemo.journalAccount.Transaction();
        if(Expense_or_income == 0){
            Amount = Amount.negate();
        }
        if(Subcategory == -1){
            Subcategory = Transaction.UNSPECIFIED;
        }
        if(Note.equals("")){
            Note = null;
        }
        if(Project == -1){
            Project = Transaction.UNSPECIFIED;
        }
        if(Member == -1){
            Member = Transaction.UNSPECIFIED;
        }
        if(Trader == -1){
            Trader = Transaction.UNSPECIFIED;
        }
        int Time_minute = (int) (Time/60000);

        Cursor Subcategory_list_a = bookhelper.listAll(bookhelper.SUBCATEGORY);
        Cursor Category_list = bookhelper.listAll(bookhelper.CATEGORY);

        Subcategory_list_a.moveToFirst();
        Category_list.moveToFirst();

        List<Integer> Subcategory_list_b = new ArrayList<>();
        int i = Subcategory_list_a.getCount();
        int j = Category_list.getCount();

        List<Integer> Income_category = new ArrayList<>();
        List<Integer> Expense_category = new ArrayList<>();

        Category_list.moveToFirst();
        for(int m = 0;m<j;m++){
            if(Category_list.getInt(Category_list.getColumnIndex("type")) == bookhelper.EXPENSE){
                Expense_category.add(Category_list.getInt(Category_list.getColumnIndex("category")));
            }
            else{
                Income_category.add(Category_list.getInt(Category_list.getColumnIndex("category")));
            }
            Category_list.moveToNext();
        }

        int real_category;
        if(Expense_or_income == bookhelper.EXPENSE){
            real_category = Expense_category.get(Category - 1);
        }
        else{
            real_category = Income_category.get(Category - 1);
        }

        for(int x = 0;x<i;x++){
            if(Subcategory_list_a.getInt(Subcategory_list_a.getColumnIndex("category")) == real_category){
                Subcategory_list_b.add(Subcategory_list_a.getInt(Subcategory_list_a.getColumnIndex("subcategory")));
            }
            Subcategory_list_a.moveToNext();
        }

        int real_subcategory = Subcategory_list_b.get(Subcategory - 1);

        T.amount = Amount.movePointRight(2).intValue();
        T.category = real_category;
        T.subcategory = real_subcategory;
        T.account = Account;
        T.outaccount = Transaction.NONTRANSFER;
        T.time = Time_minute;
        T.member = Member;
        T.trader = Trader;
        T.project = Project;
        T.note = Note;
        T.aname = account_name;
        T.type = Expense_or_income;
        T.mname = member_name;
        T.sname = subcategory_name;
        T.cname = category_name;
        T.oaname = null;
        bookhelper.addTransaction(T);
        return 0;
    }

    public static int New_bookkeeping_transfer(Context context,String username,BigDecimal Amount,int Account,int Outaccount,int Member,long Time,String Note, String account_name, String member_name,String outaccount_name){
        bookhelper = new BookHelper(context, username+".db");
        com.example.jizhangdemo.journalAccount.Transaction T = new com.example.jizhangdemo.journalAccount.Transaction();
        if(Note.equals("")){
            Note = null;
        }
        if(Member == -1){
            Member = Transaction.UNSPECIFIED;
        }
        int Time_minute = (int) (Time/60000);
        T.amount = Amount.movePointRight(2).intValue();
        T.category = Transaction.UNSPECIFIED;
        T.subcategory =Transaction.UNSPECIFIED;
        T.account = Account;
        T.outaccount = Outaccount;
        T.time = Time_minute;
        T.member = Member;
        T.trader = Transaction.UNSPECIFIED;
        T.project = Transaction.UNSPECIFIED;
        T.note = Note;
        T.oaname = outaccount_name;
        T.cname = null;
        T.sname = null;
        T.mname = member_name;
        T.aname = account_name;
        T.type = Transaction.TRANSFER;
        bookhelper.addTransaction(T);
        return 0;
    }

}
