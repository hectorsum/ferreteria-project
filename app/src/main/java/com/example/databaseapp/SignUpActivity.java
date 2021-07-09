package com.example.databaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.databaseapp.db.DbUser;

public class SignUpActivity extends AppCompatActivity {
    private EditText email, username, password;
    private Button signUpButton;
    private TextView goBackSignIn;
    private DbUser db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email = findViewById(R.id.signupemail);
        username = findViewById(R.id.signupusername);
        password = findViewById(R.id.siguppassword);
        signUpButton = findViewById(R.id.signupbutton);
        goBackSignIn = (TextView)findViewById(R.id.backlogin);
        db = new DbUser(this); //passing signup activity in order to use db methods
        createUser();
        goBackLogin();
    }

    private void createUser(){
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCorrect = db.registerUser(username.getText().toString(), email.getText().toString(), password.getText().toString());
                if(isCorrect){
                    Toast.makeText(SignUpActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                }else{
                    Toast.makeText(SignUpActivity.this, "Error when registering"+isCorrect, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void goBackLogin(){
        goBackSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });
    }
}
