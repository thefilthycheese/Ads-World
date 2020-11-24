package com.example.el_bazaar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {

    EditText memail  , mpassword;
    Button mbutt;
    TextView mregbutt;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        memail = findViewById(R.id.regemail);
        mpassword = findViewById(R.id.regpass);
        mbutt = findViewById(R.id.regbutton);
        mregbutt = findViewById(R.id.regexit);
        fAuth = FirebaseAuth.getInstance();


        mbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                    memail.setError("Email is Required!");
                if(TextUtils.isEmpty(password))
                    mpassword.setError("Password is Required!");
                if(password.length() < 6)
                    mpassword.setError("Password must be 6 characters or more!");

                fAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this , "Logged in successfully!" , Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext() , MainActivity.class));
                        }
                        else{
                            Toast.makeText(Login.this , "Error has been encountered "+ task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                            //should add progress bar function here
                        }
                    }
                });

            }
        });

        mregbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , Register.class));
            }
        });

    }
}