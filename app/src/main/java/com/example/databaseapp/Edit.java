package com.example.databaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.databaseapp.db.DbProduct;
import com.example.databaseapp.entities.Products;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Edit extends AppCompatActivity {
    EditText txtName, txtPrice, txtStock;
    Button btnSave;
    Products product;
    FloatingActionButton fabEdit, fabDelete;
    boolean isCorrect = false;
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
        fabEdit.setVisibility(View.INVISIBLE);
        fabDelete = findViewById(R.id.fabDelete);
        fabDelete.setVisibility(View.INVISIBLE);
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
        DbProduct dbProduct = new DbProduct(Edit.this);
        product = dbProduct.listProduct(id);
        //Validate if product exists
        if(product != null){
            try{
                txtName.setText(product.getName());
                txtPrice.setText(String.valueOf(product.getPrice()));
                txtStock.setText(String.valueOf(product.getStock()));
            }catch (Exception e){
                System.out.println(e);
            }
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtName.getText().toString().equals("") && !txtPrice.getText().toString().equals("") && !txtStock.getText().toString().equals("")){
                    isCorrect = dbProduct.editProduct(id,txtName.getText().toString(),Integer.parseInt(txtStock.getText().toString()), Double.parseDouble(txtPrice.getText().toString()));
                    if(isCorrect){
                        Toast.makeText(Edit.this,"PRODUCT UPDATED", Toast.LENGTH_LONG).show();
                        goBack();
                    }else{
                        Toast.makeText(Edit.this,"ERROR UPDATING PRODUCT", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Edit.this,"YOU MUST FILL ALL FIELDS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void goBack(){
        Intent intent = new Intent(this,Visualize.class);
        intent.putExtra("ID",id);
        startActivity(intent);
    }
}