package com.example.databaseapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databaseapp.adapters.ListProductsAdapter;
import com.example.databaseapp.db.DbProduct;
import com.example.databaseapp.entities.Products;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FilterProducts extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private AppCompatActivity activity = FilterProducts.this;
    Context context = FilterProducts.this;
    private RecyclerView recyclerViewBeneficiary;
    private ArrayList<Products> listBeneficiary;
    private ListProductsAdapter beneficiaryRecyclerAdapter;
    private DbProduct databaseHelper;
    SearchView searchBox;
    private ArrayList<Products> filteredList;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        initObjects();

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("name")) {
            //get all needed extras intent
            int id = getIntent().getExtras().getInt("id");
            String name = getIntent().getExtras().getString("name");
            String price = getIntent().getExtras().getString("price");
            String stock = getIntent().getExtras().getString("stock");
        }else{
            Toast.makeText(this, "No API Data", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        recyclerViewBeneficiary = (RecyclerView) findViewById(R.id.listProducts);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        listBeneficiary = new ArrayList<>();
        beneficiaryRecyclerAdapter = new ListProductsAdapter(listBeneficiary, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewBeneficiary.setLayoutManager(mLayoutManager);
        recyclerViewBeneficiary.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBeneficiary.setHasFixedSize(true);
        recyclerViewBeneficiary.setAdapter(beneficiaryRecyclerAdapter);
        databaseHelper = new DbProduct(activity);

        getDataFromSQLite();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        return true;
    }


    public boolean onQuerySubmit(String query){
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextChange(String incoming_text){
        incoming_text = incoming_text.toLowerCase();
        ArrayList<Products> newList = new ArrayList<>();
        for(Products product : listBeneficiary){
            String name = product.getName().toLowerCase();
            if(name.contains(incoming_text)){
                newList.add(product);
            }
        }
        beneficiaryRecyclerAdapter.setFilter(newList);
        return true;
    }



    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromSQLite() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        System.out.println("Calling getDataFromSQLite()");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listBeneficiary.clear();
                listBeneficiary.addAll(databaseHelper.listProducts());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                beneficiaryRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
