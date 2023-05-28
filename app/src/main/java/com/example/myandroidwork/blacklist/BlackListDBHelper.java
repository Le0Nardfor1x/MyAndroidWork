package com.example.myandroidwork.blacklist;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class BlackListDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "blacklist.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_BLACKLIST = "blacklist";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NUMBER = "number";

    private static final String DATABASE_CREATE = "create table " +
            TABLE_BLACKLIST + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_NAME + " text not null, " +
            COLUMN_NUMBER + " text not null);";

    public BlackListDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLACKLIST);
        onCreate(db);
    }
}

