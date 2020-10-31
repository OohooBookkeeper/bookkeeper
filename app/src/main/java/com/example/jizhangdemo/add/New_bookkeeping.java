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
    public static int New_bookkeeping_nontransfer(Context context, String username,BigDecimal Amount, int Category , int Subcategory , int Account, long Time, int Member, int Project, int Trader, String Note, int Expense_or_income){
        bookhelper = new BookHelper(context, username+".db");
        Transaction T = new Transaction();
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

        T.amount = Amount.movePointRight(2).intValue();
        Cursor Subcategory_a = bookhelper.listAll(bookhelper.SUBCATEGORY);
        Cursor Category_a = bookhelper.listAll(bookhelper.CATEGORY);
        Subcategory_a.moveToFirst();
        Category_a.moveToFirst();
        List<Integer> Subcategory_b = new ArrayList<>();
        int i = Subcategory_a.getCount();
        int j = Category_a.getCount();
        int k = 0;

        for(int y = 0;y<j;y++){
            if(Category_a.getInt(Category_a.getColumnIndex("type")) == bookhelper.INCOME){
                k++;
            }
            Category_a.moveToNext();
        }
        Log.d("test_k",String.valueOf(k));
        for(int x = 0;x<i;x++){
            if(Expense_or_income == bookhelper.EXPENSE){
                if(Subcategory_a.getInt(Subcategory_a.getColumnIndex("category")) == (Category + k)){
                    Subcategory_b.add(Subcategory_a.getInt(Subcategory_a.getColumnIndex("subcategory")));
                }
            }
            else{
                if(Subcategory_a.getInt(Subcategory_a.getColumnIndex("category")) == Category){
                    Subcategory_b.add(Subcategory_a.getInt(Subcategory_a.getColumnIndex("subcategory")));
                }
            }
            Subcategory_a.moveToNext();
        }
        int subcategory = Subcategory_b.get (Subcategory - 1);
        if(Expense_or_income == bookhelper.EXPENSE){
            T.category = Category + k;
        }
        else{
            T.category = Category;
        }
        T.subcategory =subcategory;
        T.account = Account;
        T.outaccount = Transaction.NONTRANSFER;
        T.time = Time_minute;
        T.member = Member;
        T.trader = Trader;
        T.project = Project;
        T.note = Note;
        bookhelper.addTransaction(T);
        return 0;
    }

    public static int New_bookkeeping_transfer(Context context,String username,BigDecimal Amount,int Account,int Outaccount,int Member,long Time,String Note){
        bookhelper = new BookHelper(context, username+".db");
        Transaction T = new Transaction();
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
        bookhelper.addTransaction(T);
        return 0;
    }
}
