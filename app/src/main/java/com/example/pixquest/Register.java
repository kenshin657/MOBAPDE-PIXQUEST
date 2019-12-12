package com.example.pixquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Register extends AppCompatActivity {

    DatabaseReference userData;
    EditText username, password;
    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        userData = FirebaseDatabase.getInstance().getReference("users");
        users = new ArrayList<>();
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

    public void addUser(View v){
        User user = null;
        String un = username.getText().toString().trim();
        String pw = password.getText().toString().trim();
        for(int i = 0; i<users.size(); i++){
            if(users.get(i).getUsername().equals(un) && users.get(i).getPassword().equals(pw)){
                user = users.get(i);
            }
        }
        if(user!=null){
            //user already exists
            Toast.makeText(Register.this, "Username already taken!", Toast.LENGTH_SHORT).show();
        }
        else{
            //create user
            String id = userData.push().getKey();
            user = new User(id, un, pw);
            userData.child(id).setValue(user);
            Toast.makeText(Register.this, "Username added!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
