package com.arobit.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText uname, pass;
    private Button login, register;
    private FirebaseAuth auth;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            auth = FirebaseAuth.getInstance();
            if (auth.getCurrentUser() != null) {
                startActivity(new Intent(getApplicationContext(), GroupChatActivity.class));
                finish();
            }
            uname = findViewById(R.id.uname);
            pass = findViewById(R.id.pass);
            login = findViewById(R.id.login);
            register = findViewById(R.id.register);


            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = uname.getText().toString();
                    String password = pass.getText().toString();

                    if (!email.equals("") && !password.equals("")) {
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), GroupChatActivity.class));
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                    }
                }
            });


        } catch (Exception e) {
            Toast.makeText(this, "1 Error: " + e, Toast.LENGTH_LONG).show();
        }
    }

}