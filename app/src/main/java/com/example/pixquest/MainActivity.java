package com.example.pixquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference userData;
    ArrayList<User> users;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        userData = FirebaseDatabase.getInstance().getReference("users");
        users = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    User user = postSnapshot.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void login(View v){
        User user = null;
        String un = username.getText().toString().trim();
        String pw = password.getText().toString().trim();
        for(int i = 0; i<users.size(); i++){
            if(users.get(i).getUsername().equals(un) && users.get(i).getPassword().equals(pw)){
                user = users.get(i);
            }
        }
        if(user!=null){
            //login
            Intent intent = new Intent(this, PixQuest_Main.class);
            intent.putExtra("USER", un);
            intent.putExtra("ID", user.getId());
            startActivity(intent);
        }
        else{
            //invalid username or password
            Toast.makeText(MainActivity.this, "Incorrect username and/or password!", Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View v) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }



}
