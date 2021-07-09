package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText emailtext;
    private EditText passtext;
    private Button loginbtn;
    private TextView gotosignup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
      emailtext=findViewById(R.id.loginemail);
      passtext=findViewById(R.id.loginpass);
      loginbtn=findViewById(R.id.loginbtn);
      gotosignup=findViewById(R.id.gotosignup);

gotosignup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        startActivity(new Intent(LoginActivity.this,com.example.firebasetest.SignupActivity.class));
    }
});

loginbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String email=emailtext.getText().toString().trim();
        String pass=passtext.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull  Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Log.d("TAG",task.getException().getMessage());
                    Toast.makeText(LoginActivity.this,"failed",Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }
            }
        });



    }
});
    }


}