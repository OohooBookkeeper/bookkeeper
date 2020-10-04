package com.example.oohoobkk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class AccountHelper extends SQLiteOpenHelper {
    public static final String CREATE = "create table Account(id integer primary key autoincrement, " +
            "username text, password text, question integer, answer text, " +
            "patternenabled integer, pattern text)";
    private Context aContext;

    // 当前数据库版本为1，请传入1
    public AccountHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        aContext = context;
    }

    // 添加一条记录到账号数据库中，注册时调用
    public void signup(String username, String password, int question, String answer, int patternEnabled, String pattern) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        values.put("question", question);
        values.put("answer", answer);
        values.put("patternenabled", patternEnabled);
        values.put("pattern", pattern);
        db.insert("Account", null, values);
    }

    // 查询指定用户名的数据
    public Cursor query(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("Account", null, "username=?", new String[]{username}, null, null, null);
    }

    // 修改指定用户名的信息。该方法不会检查username指定的字段是否存在，需先用query方法检查。
    // 若不想修改某些字段，则password, answer, pattern字段传入null，question字段传入0，patternEnabled字段传入除0和1以外的值
    public void update(String username, String password, int question, String answer, int patternEnabled, String pattern) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (password != null)
            values.put("password", password);
        if (question != 0)
            values.put("question", question);
        if (answer != null)
            values.put("answer", answer);
        if (patternEnabled == 0 || patternEnabled == 1)
            values.put("patternenabled", patternEnabled);
        if (pattern != null)
            values.put("pattern", pattern);
        db.update("Account", values, "username = ?", new String[] {username});
    }

    // 注销账户
    public void delete(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Account", "username = ?", new String[] {username});
    }

    // 列出数据库中所有数据，调试时用
    public Cursor listall() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("Account", null, null, null, null, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
        Toast.makeText(aContext, "Database created", Toast.LENGTH_SHORT).show();
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
