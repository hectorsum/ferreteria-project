package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.databaseapp.db.DbProduct;

public class New extends AppCompatActivity {
    EditText txtNombre, txtStock, txtPrice;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        txtNombre = findViewById(R.id.txtName);
        txtStock = findViewById(R.id.txtStock);
        txtPrice = findViewById(R.id.txtPrice);
	    btnSave = findViewById(R.id.btnSave);

        //Detect when the user clicks on "Save" button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbProduct dbProduct = new DbProduct(New.this);
                long id = dbProduct.insertProduct(txtNombre.getText().toString(), Integer.parseInt(txtStock.getText().toString()), Double.parseDouble(txtPrice.getText().toString()));
                if(id > 0){
                    Toast.makeText(New.this, "PRODUCT INSERTED", Toast.LENGTH_LONG).show();
                    clearFields();
                }else{
                    Toast.makeText(New.this, "ERROR WHEN INSERTING PRODUCTS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void clearFields(){
        txtNombre.setText("");
        txtPrice.setText("");
        txtStock.setText("");
    }
}
