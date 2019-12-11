package com.example.pixquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PixQuest_Main extends AppCompatActivity {

    ListView singleList, dailyList, weeklyList;
    ArrayList<Quest> singleQuests, dailyQuests, weeklyQuests;
    DatabaseReference databaseSingle, databaseDaily, databaseWeekly;
    String un;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix_quest__main);

        databaseSingle = FirebaseDatabase.getInstance().getReference("singlequests");
        databaseDaily = FirebaseDatabase.getInstance().getReference("dailyquests");
        databaseWeekly = FirebaseDatabase.getInstance().getReference("weeklyquests");

        singleList = findViewById(R.id.singleList);
        dailyList = findViewById(R.id.dailyList);
        weeklyList = findViewById(R.id.weeklyList);

        Intent intent = getIntent();
        un = intent.getStringExtra("USER");

        singleQuests = new ArrayList<>();
        dailyQuests = new ArrayList<>();
        weeklyQuests = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseSingle.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                singleQuests.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Quest quest = postSnapshot.getValue(Quest.class);
                    if(quest.getOwner().equals(un)){
                        singleQuests.add(quest);
                    }
                }

                QuestList questAdapter = new QuestList(PixQuest_Main.this, singleQuests);
                singleList.setAdapter(questAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseDaily.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dailyQuests.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Quest quest = postSnapshot.getValue(Quest.class);
                    if(quest.getOwner().equals(un)){
                        dailyQuests.add(quest);
                    }
                }

                QuestList questAdapter = new QuestList(PixQuest_Main.this, dailyQuests);
                dailyList.setAdapter(questAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseWeekly.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                weeklyQuests.clear();

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Quest quest = postSnapshot.getValue(Quest.class);
                    if(quest.getOwner().equals(un)) {
                        weeklyQuests.add(quest);
                    }
                }

                QuestList questAdapter = new QuestList(PixQuest_Main.this, weeklyQuests);
                weeklyList.setAdapter(questAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addQuest(View v){
        Intent intent = new Intent(this, AddQuest.class);
        intent.putExtra("USER", un);
        startActivity(intent);
    }
}
