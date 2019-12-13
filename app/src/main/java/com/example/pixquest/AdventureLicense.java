package com.example.pixquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class AdventureLicense extends AppCompatActivity {

    DatabaseReference userData;
    TextView txt, txt1, txt2, txt3, txt4;
    String un, id;
    User user;
    //String hello,lol,zillo,wam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure_license);

        userData = FirebaseDatabase.getInstance().getReference("users");

        Intent intent = getIntent();
        un = intent.getStringExtra("USER");

        txt = findViewById(R.id.name);
        txt1 = findViewById(R.id.coin);
        txt2 = findViewById(R.id.singlequest);
        txt3 = findViewById(R.id.weeklyquest);
        txt4 = findViewById(R.id.monthlyquest);



        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item: dataSnapshot.getChildren()){
                    User user = item.getValue(User.class);
                    if(user.getUsername().equals(un)){
                        AdventureLicense.this.user = user;

                        //hello = user.getUsername();

                        //System.out.println(hello);
                        txt.setText(user.getUsername());
                        txt1.setText("Credit  " +String.valueOf(user.getCredit()));
                        txt2.setText(String.valueOf(user.getDailycompleted()));
                        txt3.setText(String.valueOf(user.getSinglecompleted()));
                        txt4.setText("gfd" + String.valueOf(user.getWeeklycompleted()));

                        //System.out.println(user.getCredit());

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


}
