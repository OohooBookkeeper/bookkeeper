package com.example.oohoobkk;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BookHelper extends SQLiteOpenHelper {
    private static final String CREATE_TRANSACTION = "create table Transaction(id integer primary key autoincrement, " +
            "amount integer, category integer, subcategory integer, account integer, outaccount integer, " +
            "time integer, member integer, trader integer, project integer, note text)";
    private static final String CREATE_CATEGORY = "create table Category(category integer primary key autoincrement, name text)";
    private static final String CREATE_SUBCATEGORY = "create table Subategory(subcategory integer primary key autoincrement, " +
            "category integer, name text)";
    private static final String CREATE_ACCOUNT = "create table Account(account integer primary key autoincrement, name text)";
    private static final String CREATE_MEMBER = "create table Member(member integer primary key autoincrement, name text)";
    private static final String CREATE_TRADER = "create table Trader(trader integer primary key autoincrement, name text)";
    private static final String CREATE_PROJECT = "create table Project(project integer primary key autoincrement, name text)";
    private static final String[] CREATE = {CREATE_TRANSACTION, CREATE_CATEGORY, CREATE_SUBCATEGORY,
            CREATE_ACCOUNT, CREATE_MEMBER, CREATE_TRADER, CREATE_PROJECT};
    private Context context;
    private static final int version = 1;

    public static final int TRANSACTION = 0;
    public static final int CATEGORY = 1;
    public static final int SUBCATEGORY = 2;
    public static final int ACCOUNT = 3;
    public static final int MEMBER = 4;
    public static final int TRADER = 5;
    public static final int PROJECT = 6;
    public static final int TIME = 7;

    public BookHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, BookHelper.version);
        this.context = context;
    }

    // 增加一笔账目
    public void addTransaction(Transaction t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues entry = new ContentValues();
        entry.put("amount", t.amount);
        entry.put("category", t.category);
        entry.put("subcategory", t.subcategory);
        entry.put("account", t.account);
        entry.put("outaccount", t.outaccount);
        entry.put("category", t.category);
        entry.put("time", t.time);
        entry.put("trader", t.trader);
        entry.put("project", t.project);
        entry.put("note", t.note);
        db.insert("Transaction", null, entry);
    }

    // 修改一笔账目，通过指定账目的唯一标识号ID实现
    public void updateTransaction(int id, Transaction t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues entry = new ContentValues();
        entry.put("amount", t.amount);
        entry.put("category", t.category);
        entry.put("subcategory", t.subcategory);
        entry.put("account", t.account);
        entry.put("outaccount", t.outaccount);
        entry.put("category", t.category);
        entry.put("time", t.time);
        entry.put("trader", t.trader);
        entry.put("project", t.project);
        entry.put("note", t.note);
        db.update("Transaction", entry, "id = ?", new String[] {String.valueOf(id)});
        Cursor c;
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, selectionArgs);
    }

    // 显示所有收入/支出或所有转账，orderBy为排序依据，desc规定结果是否降序排序
    @SuppressLint("Recycle")
    public Cursor displayAllTransactions(int orderBy, Boolean desc) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sDesc = desc ? "desc" : "";
        String sOrderBy;
        switch (orderBy) {
            case CATEGORY:
                sOrderBy = "category";
                break;
            case SUBCATEGORY:
                sOrderBy = "subcategory";
                break;
            case ACCOUNT:
                sOrderBy = "account";
                break;
            case TIME:
                sOrderBy = "time";
                break;
            case MEMBER:
                sOrderBy = "member";
                break;
            case TRADER:
                sOrderBy = "trader";
                break;
            case PROJECT:
                sOrderBy = "project";
                break;
            default:
                sOrderBy = null;
        }
        final String SELECT = " T.*, C.name as cname, S.name as sname, A1.name as aname, " +
                "A2.name as oaname, M.name as mname, R.name as rname, P.name as pname ";
        final String FROM = " [Transaction] as T left join Category as C on T.category = C.category " +
                "left join Subcategory as S on T.category = S.category and T.subcategory = S.subcategory " +
                "left join Account as A1 on T.account = A1.account left join Account as A2 on " +
                "T.onaccount = A2.account left join Member as M on T.member = M.member left join " +
                "Trader as R on T.trader = R.trader left join Project as P on T.project = R.project ";
        final String SQL = "select" + SELECT + "from" + FROM;
        if (sOrderBy == null)
            return db.rawQuery(SQL, null);
        return db.rawQuery(SQL + "order by ? " + desc, new String[]{sOrderBy});
    }

    // 删除一笔账目，通过指定账目的唯一标识号ID实现
    public void deleteTransaction(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Transaction", "id = ?", new String[] {String.valueOf(id)});
    }

    // 列出一张表中所有数据，调试时用，若表不存在则返回null
    public Cursor listAll(int table) {
        SQLiteDatabase db = this.getReadableDatabase();
        String s;
        switch (table) {
            case TRANSACTION:
                s = "Transaction";
                break;
            case CATEGORY:
                s = "Category";
                break;
            case SUBCATEGORY:
                s = "Subcategory";
                break;
            case ACCOUNT:
                s = "Account";
                break;
            case MEMBER:
                s = "Member";
                break;
            case TRADER:
                s = "Trader";
                break;
            case PROJECT:
                s = "Project";
                break;
            default:
                return null;
        }
        return db.query(s, null, null, null, null, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (String s : CREATE)
            db.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
