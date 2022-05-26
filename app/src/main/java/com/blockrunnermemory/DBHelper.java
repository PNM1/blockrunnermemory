package com.blockrunnermemory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VER = 1;
    public static final String DB_NAME = "results";
    public static final String TABLE_NAME = "res";

    public static final String KEY_ID = "_id";
    public static final String KEY_TIME = "time";
    public static final String KEY_SCORE = "score";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (" + KEY_ID
        + " integer primary key," + KEY_TIME + " text," + KEY_SCORE + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
