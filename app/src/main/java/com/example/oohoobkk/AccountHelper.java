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
            "patternenabled integer, pattern integer)";
    private Context aContext;

    public AccountHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        aContext = context;
    }

    public void signup(String username, String password, int question, String answer, int patternEnabled, int pattern) {
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
