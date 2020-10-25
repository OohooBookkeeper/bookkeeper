package com.example.jizhangdemo.add;

import android.content.Context;
import android.database.Cursor;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;


public class Return_message extends AppCompatActivity {
    static BookHelper bookhelper;

    public static Message Return_nontransfer(Context context, int Expense_or_income_or_transfer) {
        bookhelper = new BookHelper(context, "book.db");
        Message M = new Message();
        Cursor Account = bookhelper.listAll(bookhelper.ACCOUNT);
        Account.moveToFirst();
        int Account_number[] = new int[100];
        int i = 0;
        if (Account != null && Account.moveToFirst()) {
            do {
                Account_number[i] = Account.getInt(Account.getColumnIndex("account"));
                M.Account_name[i] = Account.getString(Account.getColumnIndex("name"));
                i++;
            } while (Account.moveToNext());
        }

        Cursor Member = bookhelper.listAll(bookhelper.MEMBER);
        Member.moveToFirst();
        int Member_number[] = new int[100];
        i = 0;
        if (Member != null && Member.moveToFirst()) {
            do {
                Member_number[i] = Member.getInt(Member.getColumnIndex("member"));
                M.Member_name[i] = Member.getString(Member.getColumnIndex("name"));
                i++;
            } while (Member.moveToNext());
        }

        Cursor Trader = bookhelper.listAll(bookhelper.TRADER);
        Trader.moveToFirst();
        int Trader_number[] = new int[100];
        i = 0;
        if (Trader != null && Trader.moveToFirst()) {
            do {
                Trader_number[i] = Trader.getInt(Trader.getColumnIndex("member"));
                M.Trader_name[i] = Trader.getString(Trader.getColumnIndex("name"));
                i++;
            } while (Trader.moveToNext());
        }

        Cursor Project = bookhelper.listAll(bookhelper.PROJECT);
        Project.moveToFirst();
        int Project_number[] = new int[100];
        i = 0;
        if (Project != null && Project.moveToFirst()) {
            do {
                Project_number[i] = Project.getInt(Project.getColumnIndex("member"));
                M.Project_name[i] = Project.getString(Project.getColumnIndex("name"));
                i++;
            } while (Project.moveToNext());
        }

        Cursor Category = bookhelper.listAll(bookhelper.CATEGORY);
        Category.moveToFirst();
        int Category_number[] = new int[100];
        int Category_type_number[] = new int[100];
        String Category_name[] = new String[100];
        i = 0;
        if (Category != null && Category.moveToFirst()) {
            do {
                Category_number[i] = Category.getInt(Category.getColumnIndex("Category"));
                Category_type_number[i] = Category.getInt(Category.getColumnIndex("type"));
                ;
                Category_name[i] = Category.getString(Category.getColumnIndex("name"));
                i++;
            } while (Category.moveToNext());
        }

        Cursor Subcategory = bookhelper.listAll(bookhelper.SUBCATEGORY);
        Subcategory.moveToFirst();
        int Subcategory_number[] = new int[100];
        int Subcategory_category_number[] = new int[100];
        String Subcategory_name[] = new String[100];
        int j = 0;
        if (Subcategory != null && Subcategory.moveToFirst()) {
            do {
                Subcategory_number[j] = Subcategory.getInt(Subcategory.getColumnIndex("subcategory"));
                Subcategory_category_number[j] = Subcategory.getInt(Subcategory.getColumnIndex("category"));
                ;
                Subcategory_name[j] = Subcategory.getString(Subcategory.getColumnIndex("name"));
                j++;
            } while (Category.moveToNext());
        }

        String Connect_subcategory[][] = new String[i][j];
        for (int x = 0; x < i; x++) {
            int z = 0;
            for (int y = 0; y < j; y++) {
                if (Category_number[x] == Subcategory_category_number[y]) {
                    Connect_subcategory[x][z] = Subcategory_name[y];
                    z++;
                }
            }
        }

        List<FirstCategory> Expense_category = new ArrayList<FirstCategory>();
        List<FirstCategory> Income_category = new ArrayList<FirstCategory>();

        for (int x = 0; x < i; x++) {
            if (Category_name[x] == null) {
                break;
            }
            if (Category_type_number[x] == bookhelper.EXPENSE) {
                FirstCategory Firstcategory = new FirstCategory();
                List<String> List_subcategory = new ArrayList<String>();
                for (int y = 0; y < j; y++) {
                    if (Connect_subcategory[x][y] == null) {
                        break;
                    }
                    List_subcategory.add(Connect_subcategory[x][y]);
                }
                Firstcategory.setName(Category_name[x]);
                Firstcategory.setCategory(List_subcategory);
                Expense_category.add(Firstcategory);
            }
        }

        for (int x = 0; x < i; x++) {
            if (Category_name[x] == null) {
                break;
            }
            if (Category_type_number[x] == bookhelper.INCOME) {
                FirstCategory Firstcategory = new FirstCategory();
                List<String> List_subcategory = new ArrayList<String>();
                for (int y = 0; y < j; y++) {
                    if (Connect_subcategory[x][y] == null) {
                        break;
                    }
                    List_subcategory.add(Connect_subcategory[x][y]);
                }
                Firstcategory.setName(Category_name[x]);
                Firstcategory.setCategory(List_subcategory);
                Income_category.add(Firstcategory);
            }
        }
        if(Expense_or_income_or_transfer == 0){
            M.All_category = Expense_category;
            return M;
        }
        else if(Expense_or_income_or_transfer ==1){
            M.All_category = Income_category;
            return M;
        }
        else{
            return M;
        }
    }

}
