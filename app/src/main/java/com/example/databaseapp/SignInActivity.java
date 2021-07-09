package com.example.databaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.databaseapp.db.DbUser;

public class SignInActivity extends AppCompatActivity {
    private EditText username, password;
    private Button loginButton;
    private TextView goSignUp;
    private DbUser db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //setting logo up
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        username = findViewById(R.id.loginusername);
        password = findViewById(R.id.loginpassword);
        loginButton = findViewById(R.id.loginbutton);
        goSignUp = (TextView)findViewById(R.id.gosignup);
        db = new DbUser(this);
        //Initializing listeners in order to wait the user to click on them
        loginUser();
        goSignUp();
    }
    private void loginUser(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userid = db.validateUser(username.getText().toString(),password.getText().toString());
                if(userid != 0){
                    Toast.makeText(SignInActivity.this, "Welcome "+username.getText().toString()+"!", Toast.LENGTH_LONG).show();
                    //Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    //intent = intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intent.putExtra("USERID",userid);
                    startActivity(intent);
                    finish(); //to reset stackHistory and clean up the previous ID stored
                }else{
                    Toast.makeText(SignInActivity.this, "User or password are not correct "+userid, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void goSignUp(){
        goSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                finish();
            }
        });
    }
}
