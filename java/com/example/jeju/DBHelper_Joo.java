package com.example.jeju;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.fragment.app.FragmentActivity;

import com.example.jeju.Contract.Entry;

public class DBHelper_Joo extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Joo.db";
    public static final int DATABASE_VERSION = 4;
    private Context context;

    public DBHelper_Joo(FragmentActivity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LIST_TABLE = "CREATE TABLE " +
                Entry.TABLE_NAME + " (" +
                Entry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Entry.COLUMN_LOCATION + " TEXT NOT NULL, " +
                Entry.COLUMN_ITEM + " TEXT NOT NULL, " +
                Entry.COLUMN_AMOUNT + " INTIGER NOT NULL, " +
                Entry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Entry.TABLE_NAME);
        onCreate(db);
    }
}