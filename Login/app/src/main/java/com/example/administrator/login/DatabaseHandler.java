package com.example.administrator.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import java.util.List;

/**
 * Created by Administrator on 10/17/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    // Database Name
    private static final String DATABASE_NAME = "contactsManager";
    private static final int DATABASE_VERSION = 1;

    // Contacts table name
    private static final String TABLE_Users = "Userlist";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "account";
    private static final String KEY_PW = "password";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PW + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);

        // Create tables again
        onCreate(db);
    }
    // Adding a new user
    void addUser (UserData user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PW,user.getPassword());

        // Inserting Row
        db.insert(TABLE_Users, null, values);
        db.close();
    }

    boolean login (String username,String password){
        SQLiteDatabase db=this.getReadableDatabase();
        String sqlseq="SELECT "+"* FROM "+ TABLE_Users+ " WHERE account= '"+username+ "' AND password='"+password+"'" ;
        String[] userN= new String[] {username,password};

        // Reading all contacts
        Log.d("Reading: ", sqlseq);

        Cursor cursor = db.rawQuery(sqlseq,null);
        Log.d("Reading: ", cursor.getCount()+"");
        if (cursor.getCount()!=0){
            return true;
        }
        else {
            return false;
        }
    }
    // Getting contacts Count
    public int getUserCount() {
        String countQuery = "SELECT  * FROM " + TABLE_Users;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();

        // return count
        return cursor.getCount();
    }


}
