package com.example.firebasetest;

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

public class SignupActivity extends AppCompatActivity {
    private EditText emailtext;
    private EditText passtext;
    private Button signupbtn;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
mAuth=FirebaseAuth.getInstance();
        emailtext=findViewById(R.id.signupmail);
        passtext=findViewById(R.id.signuppass);
        signupbtn=findViewById(R.id.signupbtn);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailtext.getText().toString().trim();
                String password=passtext.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(SignupActivity.this,"Unsuccessful",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            startActivity(new Intent(SignupActivity.this,MainActivity.class));

                        }

                    }
                });

            }
        });
    }
}