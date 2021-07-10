package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText todotext;
    private Button todoadd;
    private  TextView display;
    private LinearLayoutManager manager;
    String todoinput;
    RecyclerView recyclerView;
// ...
// Initialize Firebase Auth
FirebaseDatabase database ;





    DatabaseReference myRef,myRef1;

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG","on started");

        mAuth = FirebaseAuth.getInstance();


        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            if (database==null){
                database=FirebaseDatabase.getInstance();
                try {
                    FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                } catch (Exception e) {}
            }
            myRef= database.getReference("Todos").child(mAuth.getUid());
            myRef1=database.getReference().child(mAuth.getUid()+"/name");
            myRef1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull  Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        display.setText("Welcome"+String.valueOf(task.getResult().getValue()));
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    }
                }
            });

            Log.d("TAG","");
            FirebaseRecyclerOptions<MyModel> options=new FirebaseRecyclerOptions.Builder<MyModel>().setQuery(myRef, MyModel.class).build();
            Log.d("TAG","optionsss"+options.toString());
            FirebaseRecyclerAdapter<MyModel,MyViewHolder> adapter=new FirebaseRecyclerAdapter<MyModel, MyViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull  MainActivity.MyViewHolder holder, int position, @NonNull MyModel model) {

                    Log.d("TAG","IN viewholder adater"+model.getTodo());

                    holder.setdata(model.getTodo());
                    holder.complete.setVisibility(View.VISIBLE);
                    holder.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myRef.child(model.getTodo()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull  Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Log.d("TAG","delete susexe");
                                    }

                                }
                            });
                        }
                    });
                    if (System.currentTimeMillis()-model.getTime()>=24*60*60*60*1000){
                        myRef.child(model.getTodo()).removeValue();
                    }
                    if(model.isCompleted()){
                        holder.text.setAlpha((float) 0.5);
                        holder.complete.setVisibility(View.INVISIBLE);
                    }

                    holder.complete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Log.d("TAG","opacity");


                            myRef.child(model.getTodo()).child("completed").setValue(true);




                        }

                    });


                }

                @NonNull

                @Override
                public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout,parent,false);
                    return new MyViewHolder(view);
                }
            };
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(manager);
            adapter.startListening();

        }
        else {
            Intent intent=new Intent(MainActivity.this,com.example.firebasetest.LoginActivity.class);
            Log.d("TAG","intent started");
            startActivity(intent);
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        manager=new LinearLayoutManager(this);
        todotext=findViewById(R.id.todotext);
        todoadd=findViewById(R.id.todoadd);
        recyclerView=findViewById(R.id.recyclerview);
        display=findViewById(R.id.displayname);



//        myRef= database.getReference("Todos").child(mAuth.getUid());
//        String id=myRef.push().getKey();

        todoadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                todoinput=todotext.getText().toString();
                todotext.getText().clear();
                Long time=System.currentTimeMillis();
                MyModel model=new MyModel(todoinput,false,time);
                Log.d("TAG",todoinput);

                myRef.child(todoinput).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Log.d("TAG","suxex");
                        }
                        else{
                            Log.d("TAG",task.getException().getMessage());
                        }
                    }
                });
            }
        });





    }

public static  class MyViewHolder extends RecyclerView.ViewHolder{
    TextView text;
    ImageView complete,delete;
    public MyViewHolder(@NonNull  View itemView) {
        super(itemView);

        complete=itemView.findViewById(R.id.complete);
        delete=itemView.findViewById(R.id.delete);

    }

    public void setdata(String data){
        text=itemView.findViewById(R.id.retrievedtext);
        text.setText(data);
        text.setAlpha(1);


    }


}
}