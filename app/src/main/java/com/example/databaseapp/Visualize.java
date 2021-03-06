package com.example.databaseapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.databaseapp.db.DbProduct;
import com.example.databaseapp.entities.Products;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Visualize extends AppCompatActivity {
    EditText txtName, txtPrice, txtStock;
    Button btnSave;
    FloatingActionButton fabEdit, fabDelete;
    Products product;
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualize);
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtStock = findViewById(R.id.txtStock);
        btnSave = findViewById(R.id.btnSave);
        fabEdit = findViewById(R.id.fabEdit);
        fabDelete = findViewById(R.id.fabDelete);
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras(); //Get ID saved in extras (ListProductsAdapter)
            if(extras == null){
                id = Integer.parseInt(null);
            }else{
                id = extras.getInt("ID");
            }
        }else{
            id = (int)savedInstanceState.getSerializable("ID");
        }
        DbProduct dbProduct = new DbProduct(Visualize.this);
        product = dbProduct.listProduct(id);
        //Validate if product exists
        if(product != null){
            try{
                txtName.setText(product.getName());
                txtPrice.setText(String.valueOf(product.getPrice()));
                txtStock.setText(String.valueOf(product.getStock()));
                btnSave.setVisibility(View.INVISIBLE);
                txtName.setInputType(InputType.TYPE_NULL); //To not show the keyboard
                txtPrice.setInputType(InputType.TYPE_NULL);
                txtStock.setInputType(InputType.TYPE_NULL);
            }catch (Exception e){
                System.out.println(e);
            }
        }
        fabEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Visualize.this, Edit.class);
                intent.putExtra("ID",id);
                startActivity(intent);
            }
        });
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Visualize.this);
                builder.setMessage("Are you sure you want to delete this product?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isCorrect = dbProduct.deleteProduct(id);
                        if(isCorrect){
                            goBack();
                        }

                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
    }
    private void goBack(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}