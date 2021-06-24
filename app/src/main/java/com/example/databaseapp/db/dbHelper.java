package com.example.databaseapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {

    //Everytime I do any changes, I upgrade the version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ferreteria.db";
    public static final String TABLE_PRODUCTS = "products";



    public dbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_PRODUCTS+"(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL," +
                "price DOUBLE NOT NULL," +
                "stock INTEGER NOT NULL" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Evertime there's an upgrade, we delete the table and then create it
        db.execSQL("DROP TABLE "+TABLE_PRODUCTS);
        onCreate(db);
    }
}
