package com.example.pixquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

        singleQuests = new ArrayList<>();
        dailyQuests = new ArrayList<>();
        weeklyQuests = new ArrayList<>();

        singleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Quest singlequest = singleQuests.get(position);
                showDeleteDialog(singlequest.getId(),singlequest.getTitle(),singlequest.getType());


                return true;
            }
        });

        dailyList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Quest dailyquest = dailyQuests.get(position);
                showDeleteDialog(dailyquest.getId(),dailyquest.getTitle(),dailyquest.getType());


                return true;
            }
        });

        weeklyList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Quest weeklyquest = weeklyQuests.get(position);
                showDeleteDialog(weeklyquest.getId(),weeklyquest.getTitle(),weeklyquest.getType());


                return true;
            }
        });

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
                    singleQuests.add(quest);
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
                    dailyQuests.add(quest);
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
                    weeklyQuests.add(quest);
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
        startActivity(intent);
    }

    private void showDeleteDialog(final String postId, String title, final int type) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(title);

        final AlertDialog b = dialogBuilder.create();
        b.show();




        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deletepost(postId , type);
                b.dismiss();
            }
        });
    }

    private boolean deletepost(String id, int type) {
        if(type == 0 ) {
            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("singlequests").child(id);


            dR.removeValue();

            Toast.makeText(getApplicationContext(), "Quest deleted", Toast.LENGTH_LONG).show();
        }
        if(type == 1 ) {
            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("dailyquests").child(id);


            dR.removeValue();

            Toast.makeText(getApplicationContext(), "Quest deleted", Toast.LENGTH_LONG).show();
        }
        if(type == 2 ) {
            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("weeklyquests").child(id);


            dR.removeValue();

            Toast.makeText(getApplicationContext(), "Quest deleted", Toast.LENGTH_LONG).show();
        }


        return true;
    }

}
