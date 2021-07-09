package com.example.databaseapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbUser extends dbHelper {
    private static final String COL1 = "ID";
    private static final String COL2 = "USERNAME";
    private static final String COL3 = "EMAIL";
    private static final String COL4 = "PASSWORD";

    public DbUser(@Nullable Context context) {
        super(context);
    }
    public boolean registerUser(String username, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL2,username);
        values.put(COL3,email);
        values.put(COL4,password);
        long result = db.insert(TABLE_USERS, null, values);
        if(result == -1){ //validate if everything's ok
            return false;
        }else{
            return true;
        }
    }
    public int validateUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        int userid = 0; //value must be initialized so we pick a number that is not a valid userid
        String [] columns = {COL1}; //id
        String selection = COL2 + "=?" + " and " + COL4 + "=?"; //checking username and password
        String [] args = {username,password};
        // 1st = table to affect,
        // 2nd = columns to affect,
        // 3rd = WHERE conditional,
        // 4th = fill "?" args.
        Cursor cursor = db.query(TABLE_USERS,columns,selection,args,null,null,null);
        int count = cursor.getCount();
        if (count == 0) return 0;
        cursor = db.rawQuery("SELECT id FROM users WHERE username=? and password=?",args);
        if(cursor.moveToFirst()){
            do{
                userid = Integer.parseInt(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userid;
    }
}
