package com.example.databaseapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbProduct extends dbHelper {

    Context context;
    public DbProduct(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public long insertProduct(String name, int stock, Double price){
        long id = 0;
        try{
            dbHelper dbHelper = new dbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //Inserting records
            ContentValues values = new ContentValues();
            values.put("name",name);
            values.put("price",price);
            values.put("stock",stock);
            id = db.insert(TABLE_PRODUCTS,null,values);
        }catch (Exception e){
            e.toString();
        }
        return id;
    }
}
