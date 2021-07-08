package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.databaseapp.adapters.ListProductsAdapter;
import com.example.databaseapp.db.DbProduct;
import com.example.databaseapp.db.dbHelper;
import com.example.databaseapp.entities.Products;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView listProducts;
    ArrayList<Products> listArrayProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listProducts = findViewById(R.id.listProducts);
        listProducts.setLayoutManager(new LinearLayoutManager(this));
        DbProduct dbProduct = new DbProduct(MainActivity.this);
        listArrayProducts = new ArrayList<>();
        //Sending response from our database query to be processed in Adapter
        System.out.println("products: "+dbProduct.listProducts());
        ListProductsAdapter adapter = new ListProductsAdapter(dbProduct.listProducts(),null);
        listProducts.setAdapter(adapter);
    }
    public boolean onCreateOptionsMenu(Menu menu){
	//Show our menu
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.menu_main, menu);
	return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
	switch(item.getItemId()){
		case R.id.menu_new:
			newRecord();
			return true;
		default:
			return super.onOptionsItemSelected(item);

	}
    }
    private void newRecord(){
	//Redirects to "New" screen activity
	Intent intent = new Intent(this,New.class);
	startActivity(intent);
    }
}
