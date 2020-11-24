package com.example.el_bazaar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText mname , memail , mpassword , mphone;
    Button mbutt ;
    TextView mloginbutt ;
    FirebaseAuth fAuth;
    ProgressBar mprog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mname = findViewById(R.id.regfullname);
        memail = findViewById(R.id.regemail);
        mpassword = findViewById(R.id.regpass);
        mphone = findViewById(R.id.regphone);
        mbutt = findViewById(R.id.regbutton);
        mloginbutt = findViewById(R.id.regexit);
        mprog = findViewById(R.id.regprogrbar);
        fAuth = FirebaseAuth.getInstance();


        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }



        mbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                String name = mname.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    mname.setError("Please enter your Name");
                    return;
                }

                if(TextUtils.isEmpty(email)) {
                    memail.setError("Email is Required!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("Password is Required!");
                    return;
                }

                if(password.length() < 6)
                    mpassword.setError("Password must be 6 characters or more!");

                mprog.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this , "User Successfully Created" , Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext() , MainActivity.class));
                        }
                        else
                            Toast.makeText(Register.this , "Error has been encountered "+ task.getException().getMessage() , Toast.LENGTH_SHORT).show();


                    }
                });

            }
        });

        mloginbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , Login.class));
            }
        });

    }
}