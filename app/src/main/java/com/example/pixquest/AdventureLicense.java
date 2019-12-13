package com.example.pixquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    ImageView avatar;
    String un, id;
    User user;
    //String hello,lol,zillo,wam;

    String[] skinname={"baseskin","skin1","skin2","skin3","skin4","skin5","skin6","skin7","skin9","skin10"};
    Integer[] imgid={R.drawable.baseskin,R.drawable.skin1,R.drawable.skin2,R.drawable.skin3,R.drawable.skin4,R.drawable.skin5,R.drawable.skin6,R.drawable.skin7,R.drawable.skin8,R.drawable.skin9,R.drawable.skin10};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure_license);

        userData = FirebaseDatabase.getInstance().getReference("users");

        Intent intent = getIntent();
        un = intent.getStringExtra("USER");


        avatar = findViewById(R.id.avatarpicture);
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


                        int hmm=0;
                        for(int i =0; i<skinname.length; i++){
                            if(user.getImg()==skinname[i]){
                                hmm=i;
                            }
                        }

                        avatar.setImageResource(imgid[hmm]);
                        txt.setText(user.getUsername());
                        txt1.setText("Credit  " +String.valueOf(user.getCredit()));
                        txt2.setText(String.valueOf(user.getDailycompleted()));
                        txt3.setText(String.valueOf(user.getSinglecompleted()));
                        txt4.setText("something" + String.valueOf(user.getWeeklycompleted()));

                        //System.out.println(user.getCredit());

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void finish(View v){
        finish();
    }


}
