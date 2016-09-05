package com.github.florent37.materialviewpager.subham;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dhruv on 7/24/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String MY_TAG = "the_custom_message";
    public DatabaseHelper(Context context) {
        super(context, "scapp_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table login (username varchar(80) primary key,password varchar(80))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table if exists login");

        onCreate(db);
    }
    public boolean insertData(String username,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password",password);

        long result = db.insert("login", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    public Cursor getlogin(){

        Log.i(MY_TAG, "retrieving Login Data");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from login",null);
        Log.i(MY_TAG, "Retrieved Login Data");
        return res;
    }

    public void onlogout() {
        Log.i(MY_TAG, "Logging out!");
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete("login",null,null);
        Log.i(MY_TAG, "Logged Out!");

    }
}