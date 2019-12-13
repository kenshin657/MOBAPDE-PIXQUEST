package com.example.pixquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PixQuest_Main extends AppCompatActivity {

    ListView singleList, dailyList, weeklyList;
    ArrayList<Quest> singleQuests, dailyQuests, weeklyQuests;
    DatabaseReference databaseSingle, databaseDaily, databaseWeekly, userData;
    String un, id;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix_quest__main);

        databaseSingle = FirebaseDatabase.getInstance().getReference("singlequests");
        databaseDaily = FirebaseDatabase.getInstance().getReference("dailyquests");
        databaseWeekly = FirebaseDatabase.getInstance().getReference("weeklyquests");
        userData = FirebaseDatabase.getInstance().getReference("users");

        singleList = findViewById(R.id.singleList);
        dailyList = findViewById(R.id.dailyList);
        weeklyList = findViewById(R.id.weeklyList);

        Intent intent = getIntent();
        un = intent.getStringExtra("USER");
        id = intent.getStringExtra("ID");

        user = null;

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

        singleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Quest singlequest = singleQuests.get(position);
                showCompleteDialog(singlequest);
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

        dailyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Quest dailyquest = dailyQuests.get(position);
                showCompleteDialog(dailyquest);
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

        weeklyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Quest weeklyquest = weeklyQuests.get(position);
                showCompleteDialog(weeklyquest);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item: dataSnapshot.getChildren()){
                    User user = item.getValue(User.class);
                    if(user.getUsername().equals(un)){
                        PixQuest_Main.this.user = user;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

                Date today = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(today);

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Quest quest = postSnapshot.getValue(Quest.class);
                    if(quest.getOwner().equals(un)){

                        String todayData[] = date.split("-");
                        String lastData[] = quest.getLastCompleted().split("-");
                        int dayDifference = Integer.parseInt(todayData[2]) - Integer.parseInt(lastData[2]);
                        if(dayDifference!=0){
                            quest.setComplete(false);
                            String id = quest.getId();
                            databaseDaily.child(id).setValue(quest);
                        }
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

                Date today = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(today);

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

    public  void license(View v){
        Intent intent = new Intent(this, AdventureLicense.class);
        intent.putExtra("USER", un);
        startActivity(intent);
    }

    private void showCompleteDialog(final Quest quest){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.complete_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonComplete = (Button) dialogView.findViewById(R.id.buttonCompleteArtist);
        dialogBuilder.setTitle(quest.getTitle());

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quest.isComplete()){
                    Toast.makeText(getApplicationContext(), "Quest has been completed already!", Toast.LENGTH_SHORT).show();
                }
                else{
                    completepost(quest);
                    b.dismiss();
                }
            }
        });
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

    private void completepost(Quest quest){
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(today);
        quest.setLastCompleted(date);
        quest.setComplete(true);

        int amt = user.getCredit() + quest.getReward();
        user.setCredit(amt);


        switch(quest.getType()){
            case 0:
                int singlecount = user.getSinglecompleted() + 1;
                user.setSinglecompleted(singlecount);
                databaseSingle.child(quest.getId()).setValue(quest);
                break;
            case 1:
                int dailycount = user.getDailycompleted() + 1;
                user.setDailycompleted(dailycount);
                databaseDaily.child(quest.getId()).setValue(quest);
                break;
            case 2:
                int weeklycount = user.getWeeklycompleted() + 1;
                user.setWeeklycompleted(weeklycount);
                databaseWeekly.child(quest.getId()).setValue(quest);
                break;
            default:
                break;
        }
        userData.child(id).setValue(user);
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
