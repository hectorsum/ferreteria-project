package com.example.databaseapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.databaseapp.entities.Products;

import java.util.ArrayList;

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
    public ArrayList<Products> listProducts(){
        //Process to use our db
        dbHelper dbhelper = new dbHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ArrayList<Products> products = new ArrayList<>();
        Products p = null;
        Cursor cursorProducts = null;
        cursorProducts = db.rawQuery("SELECT * FROM "+ TABLE_PRODUCTS, null);
        //Get first element from response
        if(cursorProducts.moveToFirst()){
            do{
                p = new Products();
                p.setId(cursorProducts.getInt(0)); //column position
                p.setName(cursorProducts.getString(1));
                p.setPrice(cursorProducts.getDouble(2));
                p.setStock(cursorProducts.getInt(3));
                products.add(p);
            }while(cursorProducts.moveToNext());
        }
        cursorProducts.close();
        return products;
    }

    public Products listProduct(int id){
        //Process to use our db
        dbHelper dbhelper = new dbHelper(context);
        SQLiteDatabase db = dbhelper.getWritableDatabase();

        Products p = null;
        Cursor cursorProducts = null;

        cursorProducts = db.rawQuery("SELECT * FROM "+ TABLE_PRODUCTS + " WHERE id="+id+" LIMIT 1", null);
        //Get first element from response
        if(cursorProducts.moveToFirst()){
            //We don't need a "do while" bc there's gonna be just 1 record
            p = new Products();
            p.setId(cursorProducts.getInt(0)); //column position
            p.setName(cursorProducts.getString(1));
            p.setPrice(cursorProducts.getDouble(2));
            p.setStock(cursorProducts.getInt(3));
        }
        cursorProducts.close();
        return p;
    }
    public boolean editProduct(int id, String name, int stock, Double price){
        boolean isCorrect = false;
        dbHelper dbHelper = new dbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            db.execSQL("UPDATE "+TABLE_PRODUCTS+" SET name='"+name+"', price="+price+", stock="+stock+" WHERE id='"+id+"'");
            isCorrect = true;
        }catch (Exception e){
            e.toString();
            isCorrect = false;
        }finally { //Whichever, try or catch, it will exec everything inside "finally"
            db.close();
        }
        return isCorrect;
    }
    public boolean deleteProduct(int id){
        boolean isCorrect = false;
        dbHelper dbHelper = new dbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            db.execSQL("DELETE FROM "+TABLE_PRODUCTS+" WHERE id='"+id+"'");
            isCorrect = true;
        }catch (Exception e){
            e.toString();
            isCorrect = false;
        }finally { //Whichever, try or catch, it will exec everything inside "finally"
            db.close();
        }
        return isCorrect;
    }
}
