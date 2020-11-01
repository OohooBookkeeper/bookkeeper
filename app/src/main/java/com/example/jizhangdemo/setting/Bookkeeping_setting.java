package com.example.jizhangdemo.setting;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.example.jizhangdemo.add.BookHelper;
import com.example.jizhangdemo.journalAccount.Transaction;

public class Bookkeeping_setting extends Activity {
    static BookHelper bookhelper;

    public static Setting_message Return_setting_message(Context context, String username) {
        bookhelper = new BookHelper(context, username + ".db");
        Setting_message Sm = new Setting_message();
        Sm.Category = new ArrayList<>();
        Sm.Subcategory = new ArrayList<>();
        Sm.Member = new ArrayList<>();
        Sm.Account = new ArrayList<>();
        Sm.Template_nontransfer = new ArrayList<>();
        Sm.Template_transfer = new ArrayList<>();

        Cursor category = bookhelper.listAll(bookhelper.CATEGORY);
        if(category.moveToFirst()){
            do{
                Category C = new Category();
                C.category = category.getInt(category.getColumnIndex("category"));
                C.type = category.getInt(category.getColumnIndex("type"));
                C.name = category.getString(category.getColumnIndex("name"));
                Sm.Category.add(C);
            }while (category.moveToNext());
        }

        Cursor subcategory = bookhelper.listAll(bookhelper.SUBCATEGORY);
        if(subcategory.moveToFirst()){
            do{
                Subcategory S = new Subcategory();
                S.subcategory = subcategory.getInt(subcategory.getColumnIndex("subcategory"));
                S.category = subcategory.getInt(subcategory.getColumnIndex("category"));
                S.name = subcategory.getString(subcategory.getColumnIndex("name"));
                Sm.Subcategory.add(S);
            }while (subcategory.moveToNext());
        }

        Cursor member = bookhelper.listAll(bookhelper.MEMBER);
        if(member.moveToFirst()){
            do{
                Sm.Member.add(member.getString(member.getColumnIndex("name")));
            }while (member.moveToNext());
        }

        Cursor account = bookhelper.listAll(bookhelper.ACCOUNT);
        if(account.moveToFirst()){
            do{
                Sm.Account.add(account.getString(account.getColumnIndex("name")));
            }while (account.moveToNext());
        }

        Cursor template = bookhelper.listAll(bookhelper.TEMPLATE);
        if(template.moveToFirst()){
            do{
                Template T = new Template();
                T.id = template.getInt(template.getColumnIndex("id"));
                T.account = template.getInt(template.getColumnIndex("account"));
                T.category = template.getInt(template.getColumnIndex("category"));
                T.subcategory = template.getInt(template.getColumnIndex("subcategory"));
                T.outaccount = template.getInt(template.getColumnIndex("outaccount"));
                T.member = template.getInt(template.getColumnIndex("member"));
                T.note = template.getString(template.getColumnIndex("note"));
                if(template.getInt(template.getColumnIndex("outaccount")) == 0){
                    Sm.Template_nontransfer.add(T);
                }
                else{
                    Sm.Template_transfer.add(T);
                }
            }while (template.moveToNext());
        }
        return Sm;
    }

    public static Transaction Return_transaction(Context context, String username, int id){
        bookhelper = new BookHelper(context, username + ".db");
        Transaction T = new Transaction();
        Cursor transaction = bookhelper.displayAllTransactions(BookHelper.NULL, false);
        if (transaction.moveToFirst()) {
            do {
                if (id == transaction.getInt(transaction.getColumnIndex("id"))) {
                    T.id = transaction.getInt(transaction.getColumnIndex("id"));
                    T.category = transaction.getInt(transaction.getColumnIndex("category"));
                    T.subcategory = transaction.getInt(transaction.getColumnIndex("subcategory"));
                    T.time = transaction.getInt(transaction.getColumnIndex("time"));
                    T.trader = transaction.getInt(transaction.getColumnIndex("trader"));
                    T.project = transaction.getInt(transaction.getColumnIndex("project"));
                    T.member = transaction.getInt(transaction.getColumnIndex("member"));
                    T.amount = transaction.getInt(transaction.getColumnIndex("amount"));
                    T.account = transaction.getInt(transaction.getColumnIndex("account"));
                    T.outaccount = transaction.getInt(transaction.getColumnIndex("outaccount"));
                    T.note = transaction.getString(transaction.getColumnIndex("note"));
                    T.cname = transaction.getString(transaction.getColumnIndex("cname"));
                    T.sname = transaction.getString(transaction.getColumnIndex("sname"));
                    T.mname = transaction.getString(transaction.getColumnIndex("mname"));
                    T.aname = transaction.getString(transaction.getColumnIndex("aname"));
                    T.type = transaction.getInt(transaction.getColumnIndex("type"));
                    T.oaname = transaction.getString(transaction.getColumnIndex("oaname"));
                    break;
                }
            } while (transaction.moveToNext());
        }
        return T;
    }

    public static int New_template_nontransfer(Context context, String username, int Category, int Subcategory, int Account, int Member, String Note, int Expense_or_income){
        bookhelper = new BookHelper(context,username + ".db");
        int error_code = -1;
        Transaction T = new Transaction();
        if (Subcategory == -1) {
            Subcategory = Transaction.UNSPECIFIED;
        }
        if (Note.equals("")) {
            Note = null;
        }


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

        T.category = real_category;
        T.subcategory = real_subcategory;
        T.account = Account;
        T.outaccount = Transaction.NONTRANSFER;
        T.member = Member;
        T.note = Note;
        bookhelper.addTemplate(T);

        error_code = 0;
        return error_code;
    }


    public static int New_template_transfer(Context context, String username, int Account, int Outaccount, int Member, String Note){
        bookhelper = new BookHelper(context, username+".db");
        Transaction T = new Transaction();
        if(Note.equals("")){
            Note = null;
        }
        if(Member == -1){
            Member = Transaction.UNSPECIFIED;
        }
        T.category = Transaction.UNSPECIFIED;
        T.subcategory =Transaction.UNSPECIFIED;
        T.account = Account;
        T.outaccount = Outaccount;
        T.member = Member;
        T.note = Note;
        bookhelper.addTemplate(T);
        return 0;
    }

    public static int Change_template_nontransfer(Context context, String username, int id, int Category, int Subcategory, int Account,int Member, String Note, int Expense_or_income){
        bookhelper = new BookHelper(context,username + ".db");
        int error_code = -1;
        Transaction T = new Transaction();
        if (Subcategory == -1) {
            Subcategory = Transaction.UNSPECIFIED;
        }
        if (Note.equals("")) {
            Note = null;
        }

        if (Member == -1) {
            Member = Transaction.UNSPECIFIED;
        }



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

        T.category = real_category;
        T.subcategory = real_subcategory;
        T.account = Account;
        T.outaccount = Transaction.NONTRANSFER;
        T.member = Member;
        T.note = Note;
        bookhelper.updateTemplate(id,T);
        error_code = 0;
        return error_code;
    }

    public static int Change_template_transfer(Context context, String username, int id, int Account, int Outaccount, int Member, String Note){
        bookhelper = new BookHelper(context, username+".db");
        Transaction T = new Transaction();
        if(Note.equals("")){
            Note = null;
        }
        if(Member == -1){
            Member = Transaction.UNSPECIFIED;
        }
        T.category = Transaction.UNSPECIFIED;
        T.subcategory =Transaction.UNSPECIFIED;
        T.account = Account;
        T.outaccount = Outaccount;
        T.member = Member;
        T.note = Note;
        bookhelper.updateTemplate(id,T);
        return 0;
    }

    public static int Delete_template(Context context, String username, int id){
        bookhelper = new BookHelper(context,username+".db");
        bookhelper.deleteTemplate(id);
        return 0;
    }

    public static int Change_bookkeeping_nontransfer(Context context, String username, int id, BigDecimal Amount, int Category, int Subcategory, int Account, long Time, int Member, int Project, int Trader, String Note, int Expense_or_income, String category_name, String subcategory_name, String account_name, String member_name) {
        bookhelper = new BookHelper(context, username + ".db");
        int error_code = -1;
        Transaction T = new Transaction();
        if (Expense_or_income == 0) {
            Amount = Amount.negate();
        }
        if (Subcategory == -1) {
            Subcategory = Transaction.UNSPECIFIED;
        }
        if (Note.equals("")) {
            Note = null;
        }
        if (Project == -1) {
            Project = Transaction.UNSPECIFIED;
        }
        if (Member == -1) {
            Member = Transaction.UNSPECIFIED;
        }
        if (Trader == -1) {
            Trader = Transaction.UNSPECIFIED;
        }
        int Time_minute = (int) (Time / 60000);

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
        T.oaname = null;
        T.aname = account_name;
        T.mname = member_name;
        T.sname = subcategory_name;
        T.type = Expense_or_income;
        T.cname = category_name;
        bookhelper.updateTransaction(id, T);
        error_code = 0;
        return error_code;
    }

    public static int Change_bookkeeping_transfer(Context context, String username, int id, BigDecimal Amount, int Account, int Outaccount, int Member, long Time, String Note,String account_name, String member_name,String outaccount_name){
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
        T.type = Transaction.TRANSFER;
        T.cname = null;
        T.sname = null;
        T.mname = member_name;
        T.oaname = outaccount_name;
        T.aname = account_name;
        bookhelper.updateTransaction(id,T);
        return 0;
    }

    public static int Delete_bookkeeping(Context context, String username, int id){
        bookhelper = new BookHelper(context,username+".db");
        bookhelper.deleteTransaction(id);
        return 0;
    }

    public static int Add_Category(Context context, String username, int type,String name){
        int error_code = -1;
        if((type != 1)&&(type != 0)){
            error_code = 1;
            return error_code;
        }
        bookhelper = new BookHelper(context , username+".db");
        bookhelper.addCategory(type,name);
        error_code = 0;
        return error_code;
    }

    public static int Add_expense_Subcategory(Context context, String username, int category_position,String name){
        int error_code = -1;
        bookhelper = new BookHelper(context , username+".db");
        Cursor Category = bookhelper.listAll(bookhelper.CATEGORY);
        Category.moveToFirst();
        List<Integer> Expense_category = new ArrayList<>();
        int category;
        int i = Category.getCount();
        for(int x = 0;x<i;x++){
            if(Category.getInt(Category.getColumnIndex("type")) == 0){
                Expense_category.add(Category.getInt(Category.getColumnIndex("category")));
            }
            Category.moveToNext();
        }
        category = Expense_category.get(category_position - 1);
        bookhelper.addSubcategory(category,name);
        error_code = 0;
        return error_code;
    }

    public static int Add_income_Subcategory(Context context, String username, int category_position,String name){
        int error_code = -1;
        bookhelper = new BookHelper(context , username+".db");
        Cursor Category = bookhelper.listAll(bookhelper.CATEGORY);
        Category.moveToFirst();
        List<Integer> Income_category = new ArrayList<>();
        int category;
        int i = Category.getCount();
        for(int x = 0;x<i;x++){
            if(Category.getInt(Category.getColumnIndex("type")) == 1){
                Income_category.add(Category.getInt(Category.getColumnIndex("category")));
            }
            Category.moveToNext();
        }
        category = Income_category.get(category_position - 1);
        bookhelper.addSubcategory(category,name);
        error_code = 0;
        return error_code;
    }

    public static int Add_Account(Context context, String username, String name){
        int error_code = -1;
        bookhelper = new BookHelper(context,username+".db");
        bookhelper.addAccount(name);
        error_code = 0;
        return error_code;
    }

    public static int Add_Member(Context context, String username, String name){
        int error_code = -1;
        bookhelper = new BookHelper(context,username+".db");
        bookhelper.addMember(name);
        error_code = 0;
        return error_code;
    }



    public static int Change_Expense_Category(Context context, String username, int position, int type, String name){
        int error_code = -1;
        bookhelper = new BookHelper(context , username+".db");
        int category;
        Cursor Category = bookhelper.listAll(bookhelper.CATEGORY);
        Category.moveToFirst();
        List<Integer> Expense_category = new ArrayList<>();
        int i = Category.getCount();
        for(int x = 0;x<i;x++){
            if(Category.getInt(Category.getColumnIndex("type")) == 0){
                Expense_category.add(Category.getInt(Category.getColumnIndex("category")));
            }
            Category.moveToNext();
        }
        category = Expense_category.get(position - 1);
        bookhelper.updateCategory(category,type,name);
        error_code = 0;
        return error_code;
    }

    public static int Change_Income_Category(Context context, String username, int position, int type, String name){
        int error_code = -1;
        bookhelper = new BookHelper(context , username+".db");
        int category;
        Cursor Category = bookhelper.listAll(bookhelper.CATEGORY);
        Category.moveToFirst();
        List<Integer> Income_category = new ArrayList<>();
        int i = Category.getCount();
        for(int x = 0;x<i;x++){
            if(Category.getInt(Category.getColumnIndex("type")) == 1){
                Income_category.add(Category.getInt(Category.getColumnIndex("category")));
            }
            Category.moveToNext();
        }
        category = Income_category.get(position - 1);

        bookhelper.updateCategory(category,type,name);
        error_code = 0;
        return error_code;
    }

    public static int Change_expense_Subcategory(Context context, String username, int Subcategory_position, int Category_position, String name){
        int error_code = -1;
        bookhelper = new BookHelper(context,username+".db");
        int subcategory,category;
        Cursor Category = bookhelper.listAll(bookhelper.CATEGORY);
        List<Integer> Expense_category = new ArrayList<>();
        Category.moveToFirst();
        int i = Category.getCount();
        for(int x = 0;x<i;x++){
            if(Category.getInt(Category.getColumnIndex("type")) == 0){
                Expense_category.add(Category.getInt(Category.getColumnIndex("category")));
            }
            Category.moveToNext();
        }
        category = Expense_category.get(Category_position - 1);

        Cursor Subcategory = bookhelper.listAll(bookhelper.SUBCATEGORY);
        List<Integer> subcategory_list = new ArrayList<>();
        Subcategory.moveToFirst();
        int j = Subcategory.getCount();
        for(int y = 0;y<j;y++){
            if(Subcategory.getInt(Subcategory.getColumnIndex("category")) == category){
                subcategory_list.add(Subcategory.getInt(Subcategory.getColumnIndex("subcategory")));
            }
            Subcategory.moveToNext();
        }
        subcategory = subcategory_list.get(Subcategory_position - 1);
        bookhelper.updateSubcategory(subcategory,category,name);
        error_code = 0;
        return error_code;
    }

    public static int Change_income_Subcategory(Context context, String username, int Subcategory_position, int Category_position, String name){
        int error_code = -1;
        bookhelper = new BookHelper(context,username+".db");
        int subcategory,category;
        Cursor Category = bookhelper.listAll(bookhelper.CATEGORY);
        List<Integer> Income_category = new ArrayList<>();
        Category.moveToFirst();
        int i = Category.getCount();
        for(int x = 0;x<i;x++){
            if(Category.getInt(Category.getColumnIndex("type")) == 1){
                Income_category.add(Category.getInt(Category.getColumnIndex("category")));
            }
            Category.moveToNext();
        }
        category = Income_category.get(Category_position - 1);

        Cursor Subcategory = bookhelper.listAll(bookhelper.SUBCATEGORY);
        List<Integer> subcategory_list = new ArrayList<>();
        Subcategory.moveToFirst();
        int j = Subcategory.getCount();
        for(int y = 0;y<j;y++){
            if(Subcategory.getInt(Subcategory.getColumnIndex("category")) == category){
                subcategory_list.add(Subcategory.getInt(Subcategory.getColumnIndex("subcategory")));
            }
            Subcategory.moveToNext();
        }
        subcategory = subcategory_list.get(Subcategory_position - 1);
        bookhelper.updateSubcategory(subcategory,category,name);
        error_code = 0;
        return error_code;
    }

    public static int Change_Account(Context context, String username, int position,String name){
        int error_code = -1;
        bookhelper = new BookHelper(context,username+".db");
        int account;
        Cursor account_cursor = bookhelper.listAll(bookhelper.ACCOUNT);
        account_cursor.moveToFirst();
        List<Integer> Account = new ArrayList<>();
        int i = account_cursor.getCount();
        for(int x=0;x<i;x++){
            Account.add(account_cursor.getInt(account_cursor.getColumnIndex("account")));
            account_cursor.moveToNext();
        }
        account = Account.get(position - 1);
        bookhelper.updateAccount(account, name);
        error_code = 1;
        return error_code;
    }

    public static int Change_Member(Context context, String username, int position,String name){
        int error_code = -1;
        bookhelper = new BookHelper(context,username+".db");
        int member;
        Cursor Member_cursor = bookhelper.listAll(bookhelper.MEMBER);
        Member_cursor.moveToFirst();
        List<Integer> Member = new ArrayList<>();
        int i = Member_cursor.getCount();
        for(int x = 0;x<i;x++){
            Member.add(Member_cursor.getInt(Member_cursor.getColumnIndex("member")));
            Member_cursor.moveToNext();
        }
        member = Member.get(position - 1);
        bookhelper.updateMember(member, name);
        error_code = 1;
        return error_code;
    }


    public static int Delete_income_Category(Context context, String username, int position){
        int error_code = -1;
        bookhelper = new BookHelper(context,username+".db");
        int category;
        Cursor Category = bookhelper.listAll(bookhelper.CATEGORY);
        Category.moveToFirst();
        List<Integer> Income_category = new ArrayList<>();
        int i = Category.getCount();
        for(int x = 0;x<i;x++){
            if(Category.getInt(Category.getColumnIndex("type")) == 1){
                Income_category.add(Category.getInt(Category.getColumnIndex("category")));
            }
            Category.moveToNext();
        }
        category = Income_category.get(position - 1);
        bookhelper.deleteCategory(category);
        error_code = 0;
        return error_code;
    }

    public static int Delete_expense_Category(Context context, String username, int position){
        int error_code = -1;
        bookhelper = new BookHelper(context,username+".db");
        int category;
        Cursor Category = bookhelper.listAll(bookhelper.CATEGORY);
        Category.moveToFirst();
        List<Integer> Expense_category = new ArrayList<>();
        int i = Category.getCount();
        for(int x = 0;x<i;x++){
            if(Category.getInt(Category.getColumnIndex("type")) == 0){
                Expense_category.add(Category.getInt(Category.getColumnIndex("category")));
            }
            Category.moveToNext();
        }
        category = Expense_category.get(position - 1);
        bookhelper.deleteCategory(category);
        error_code = 0;
        return error_code;
    }

    public static int Delete_income_Subcategory(Context context, String username, int subcategory_position, int category_position){
        int error_code = -1;
        bookhelper = new BookHelper(context,username+".db");
        int subcategory,category;
        Cursor Category = bookhelper.listAll(bookhelper.CATEGORY);
        List<Integer> Income_category = new ArrayList<>();
        Category.moveToFirst();
        int i = Category.getCount();
        for(int x = 0;x<i;x++){
            if(Category.getInt(Category.getColumnIndex("type")) == 1){
                Income_category.add(Category.getInt(Category.getColumnIndex("category")));
            }
            Category.moveToNext();
        }
        category = Income_category.get(category_position - 1);

        Cursor Subcategory = bookhelper.listAll(bookhelper.SUBCATEGORY);
        List<Integer> subcategory_list = new ArrayList<>();
        Subcategory.moveToFirst();
        int j = Subcategory.getCount();
        for(int y = 0;y<j;y++){
            if(Subcategory.getInt(Subcategory.getColumnIndex("category")) == category){
                subcategory_list.add(Subcategory.getInt(Subcategory.getColumnIndex("subcategory")));
            }
            Subcategory.moveToNext();
        }
        subcategory = subcategory_list.get(subcategory_position - 1);
        bookhelper.deleteSubcategory(subcategory);
        error_code = 0;
        return error_code;
    }

    public static int Delete_Expense_Subcategory(Context context, String username, int subcategory_position, int category_position){
        int error_code = -1;
        bookhelper = new BookHelper(context,username+".db");
        int subcategory,category;
        Cursor Category = bookhelper.listAll(bookhelper.CATEGORY);
        List<Integer> Expense_category = new ArrayList<>();
        Category.moveToFirst();
        int i = Category.getCount();
        for(int x = 0;x<i;x++){
            if(Category.getInt(Category.getColumnIndex("type")) == 0){
                Expense_category.add(Category.getInt(Category.getColumnIndex("category")));
            }
            Category.moveToNext();
        }
        category = Expense_category.get(category_position - 1);

        Cursor Subcategory = bookhelper.listAll(bookhelper.SUBCATEGORY);
        List<Integer> subcategory_list = new ArrayList<>();
        Subcategory.moveToFirst();
        int j = Subcategory.getCount();
        for(int y = 0;y<j;y++){
            if(Subcategory.getInt(Subcategory.getColumnIndex("category")) == category){
                subcategory_list.add(Subcategory.getInt(Subcategory.getColumnIndex("subcategory")));
            }
            Subcategory.moveToNext();
        }
        subcategory = subcategory_list.get(subcategory_position - 1);
        bookhelper.deleteSubcategory(subcategory);
        error_code = 0;
        return error_code;
    }

    public static int Delete_Account(Context context, String username, int account){
        int error_code = -1;
        bookhelper = new BookHelper(context,username+".db");
        bookhelper.deleteAccount(account);
        error_code = 0;
        return error_code;
    }

    public static int Delete_Member(Context context, String username, int member){
        int error_code = -1;
        bookhelper = new BookHelper(context,username+".db");
        bookhelper.deleteMember(member);
        error_code = 0;
        return error_code;
    }
}
