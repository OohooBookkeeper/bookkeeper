package com.example.jizhangdemo.add;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

import static com.github.abel533.echarts.code.AxisType.log;


public class Return_message extends AppCompatActivity {
    static BookHelper bookhelper;

    public static Message Return(Context context, int Expense_or_income_or_transfer,String username) {
        Log.d("test-username", username);
        bookhelper = new BookHelper(context,  username + ".db");
        Message M = new Message();
        M.Account_name = new ArrayList<>();
        Cursor Account = bookhelper.listAll(bookhelper.ACCOUNT);
        if (Account.moveToFirst()) {
            do {
                M.Account_name.add(Account.getString(Account.getColumnIndex("name")));
            } while (Account.moveToNext());
        }

        Log.d("test-account",M.Account_name.toString());
        Cursor Member = bookhelper.listAll(bookhelper.MEMBER);
        M.Member_name = new ArrayList<>();
        if (Member.moveToFirst()) {
            do {
                M.Member_name.add(Member.getString(Member.getColumnIndex("name")));
            } while (Member.moveToNext());
        }

        Cursor Trader = bookhelper.listAll(bookhelper.TRADER);
        M.Trader_name = new ArrayList<>();
        if (Trader.moveToFirst()) {
            do {
                M.Trader_name.add(Trader.getString(Trader.getColumnIndex("name")));
            } while (Trader.moveToNext());
        }

        Cursor Project = bookhelper.listAll(bookhelper.PROJECT);
        M.Project_name = new ArrayList<>();
        if (Project.moveToFirst()) {
            do {
                M.Project_name.add(Project.getString(Project.getColumnIndex("name")));
            } while (Project.moveToNext());
        }

        Cursor Category = bookhelper.listAll(bookhelper.CATEGORY);
        Log.d("test-count", String.valueOf(Category.getCount()));
        List<Integer> Category_number = new ArrayList<>();
        List<Integer> Category_type_number = new ArrayList<>();
        List<String> Category_name = new ArrayList<>();
        int i = 0;
        if (Category.moveToFirst()) {
            do {
                Category_number.add(Category.getInt(Category.getColumnIndex("category"))) ;
                Category_type_number.add(Category.getInt(Category.getColumnIndex("type")));
                ;
                Category_name.add( Category.getString(Category.getColumnIndex("name"))) ;
                i++;
                Log.d("test-category_name", Category.getString(Category.getColumnIndex("name")));
            } while (Category.moveToNext());
        }
        Log.d("test-category_name", Category_name.toString());

        Cursor Subcategory = bookhelper.listAll(bookhelper.SUBCATEGORY);
        List<Integer> Subcategory_number = new ArrayList<>();
        List<Integer> Subcategory_category_number = new ArrayList<>();
        List<String> Subcategory_name = new ArrayList<>();
        int j = 0;
        if (Subcategory.moveToFirst()) {
            do {
                Subcategory_number.add(Subcategory.getInt(Subcategory.getColumnIndex("subcategory")));
                Subcategory_category_number.add(Subcategory.getInt(Subcategory.getColumnIndex("category")));
                Subcategory_name.add(Subcategory.getString(Subcategory.getColumnIndex("name")));
                j++;
            } while (Subcategory.moveToNext());
        }

        String Connect_subcategory[][] = new String[i][j];
        for (int x = 0; x < i; x++) {
            int z = 0;
            for (int y = 0; y < j; y++) {
                if (Category_number.get(x) == Subcategory_category_number.get(y)) {
                    Connect_subcategory[x][z] = Subcategory_name.get(y);
                    z++;
                }
            }
        }

        List<FirstCategory> Expense_category = new ArrayList<>();
        List<FirstCategory> Income_category = new ArrayList<>();



        for (int x = 0; x < i; x++) {
            if (Category_type_number.get(x) == bookhelper.EXPENSE) {
                FirstCategory Firstcategory = new FirstCategory();
                Firstcategory.category = new ArrayList<String>();
                for (int y = 0; y < j; y++) {
                    if (Connect_subcategory[x][y] == null) {
                        break;
                    }
                    Firstcategory.category.add(Connect_subcategory[x][y]);
                }
                Firstcategory.setName(Category_name.get(x));
                Expense_category.add(Firstcategory);
            }
        }

        for (int x = 0; x < i; x++) {
            if (Category_type_number.get(x) == bookhelper.INCOME) {
                FirstCategory Firstcategory = new FirstCategory();
                List<String> List_subcategory = new ArrayList<String>();
                for (int y = 0; y < j; y++) {
                    if (Connect_subcategory[x][y] == null) {
                        break;
                    }
                    List_subcategory.add(Connect_subcategory[x][y]);
                }
                Firstcategory.setName(Category_name.get(x));
                Firstcategory.setCategory(List_subcategory);
                Income_category.add(Firstcategory);
            }
        }
        if(Expense_or_income_or_transfer == bookhelper.EXPENSE){
            M.All_category = Expense_category;
            return M;
        }
        else if(Expense_or_income_or_transfer == bookhelper.INCOME){
            M.All_category = Income_category;
            return M;
        }
        else{

            return M;
        }
    }
}
