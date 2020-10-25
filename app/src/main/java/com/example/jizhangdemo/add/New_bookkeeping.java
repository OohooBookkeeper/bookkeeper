package com.example.jizhangdemo.add;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.database.Cursor;

import java.math.BigDecimal;

public class New_bookkeeping extends AppCompatActivity {
    static BookHelper bookhelper;
    public static int New_bookkeeping_nontransfer(Context context, BigDecimal Amount, int Category , int Subcategory , int Account, long Time, int Member, int Project, int Trader, String Note, int Expense_or_income){
        bookhelper = new BookHelper(context, "book.db");
        Cursor transaction = bookhelper.listAll(bookhelper.TRANSACTION);
        Transaction T = new Transaction();
        if(Expense_or_income == 0){
            BigDecimal amount = Amount.negate();
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
        int Time_minute = (int) Time/60000;
        T.amount = Amount;
        T.category = Category;
        T.subcategory =Subcategory;
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
    public static int New_bookkeeping_transfer(Context context,BigDecimal Amount,int Account,int Outaccount,long Time,String Note){
        bookhelper = new BookHelper(context, "book.db");
        Cursor transaction = bookhelper.listAll(bookhelper.TRANSACTION);
        Transaction T = new Transaction();
        if(Note.equals("")){
            Note = null;
        }
        int Time_minute = (int) Time/60000;
        T.amount = Amount;
        T.category = Transaction.UNSPECIFIED;
        T.subcategory =Transaction.UNSPECIFIED;
        T.account = Account;
        T.outaccount = Outaccount;
        T.time = Time_minute;
        T.member = Transaction.UNSPECIFIED;
        T.trader = Transaction.UNSPECIFIED;
        T.project = Transaction.UNSPECIFIED;
        T.note = Note;
        bookhelper.addTransaction(T);
        return 0;
    }
}
