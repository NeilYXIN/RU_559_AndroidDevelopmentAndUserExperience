package com.rutgers.xy213.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.*;

/**
 * Created by yx654 on 2017/9/24.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) //first time create table
    {
        db.execSQL("create table task(_id integer primary key autoincrement, title varchar, description varchar)");//create database when first create
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //when upgrade
    }

}
