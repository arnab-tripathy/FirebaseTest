package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    private EditText emailtext;
    private EditText passtext,nametext;
    private Button signupbtn;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TAG","signup started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
mAuth=FirebaseAuth.getInstance();
        emailtext=findViewById(R.id.signupmail);
        passtext=findViewById(R.id.signuppass);
        signupbtn=findViewById(R.id.signupbtn);
        nametext=findViewById(R.id.name);


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailtext.getText().toString().trim();
                String password=passtext.getText().toString().trim();
                String name=nametext.getText().toString().trim();

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(SignupActivity.this,"Unsuccessful",Toast.LENGTH_SHORT).show();
                        }
                        else {

                            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull  Task<AuthResult> task) {
                                    Log.d("TAG","oncomplete name"+mAuth.getUid());
                                    myRef=database.getReference().child(mAuth.getUid());
                                    myRef.child("name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull  Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                            }
                                            }
                                    });


                                }
                            });


                        }

                    }
                });

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}