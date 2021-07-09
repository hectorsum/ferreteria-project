package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.databaseapp.adapters.ListProductsAdapter;
import com.example.databaseapp.db.DbProduct;
import com.example.databaseapp.db.dbHelper;
import com.example.databaseapp.entities.Products;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView listProducts;
    ArrayList<Products> listArrayProducts;
    ListProductsAdapter adapter;
    DbProduct dbProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher); //setting icon up into menu
        listProducts = findViewById(R.id.listProducts);
        listProducts.setLayoutManager(new LinearLayoutManager(this));
        dbProduct = new DbProduct(MainActivity.this);
        listArrayProducts = new ArrayList<>();
//        Bundle extras = getIntent().getExtras(); //Get ID saved in extras (ListProductsAdapter)
//        userid = extras.getInt("USERID");
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras(); //Get ID saved in extras (ListProductsAdapter)
            if(extras == null){
                //userid = Integer.parseInt(null);
                //userid=0;
            }else{
                //userid = extras.getInt("USERID");
            }
        }else{
            //userid = (int)savedInstanceState.getSerializable("USERID");
        }
        //Sending response from our database query to be processed in Adapter
        adapter = new ListProductsAdapter(dbProduct.listProducts(),null);
        listProducts.setAdapter(adapter);
    }
//    public void listProducts(ListProductsAdapter adapter){
//        adapter = new ListProductsAdapter(dbProduct.listProducts(),null);
//        listProducts.setAdapter(adapter);
//    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menu_new:
                newRecord();
                return true;
            case R.id.menu_logout:
                logOut();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void newRecord(){
        //Redirects to "New" screen activity
        Intent intent = new Intent(this,New.class);
        //intent.putExtra("USERID",userid);
        startActivity(intent);
    }
    private void logOut(){
        //Redirects to "New" screen activity
        startActivity(new Intent(this,SignInActivity.class));
        //finish(); //to reset stackHistory and clean up the previous ID stored
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        MenuItem searchProduct = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)searchProduct.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText); //we pass whatever we type on the filter
                return false;
            }
        });
        return true;
    }
}
