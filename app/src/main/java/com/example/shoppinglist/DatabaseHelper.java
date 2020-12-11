package com.example.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    final static String DB_NAME = "shopping_list.db";
    final static String TABLE_NAME = "shopping_list";
    final static String CREATE = "CREATE TABLE " + TABLE_NAME + "( `_id` INTEGER PRIMARY KEY AUTOINCREMENT, `item` TEXT NOT NULL, `price` REAL NOT NULL)";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) { super(context, DB_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(CREATE); }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {this.onCreate(db);}
    }
}
