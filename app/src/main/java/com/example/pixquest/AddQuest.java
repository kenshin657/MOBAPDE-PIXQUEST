package com.example.pixquest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddQuest extends AppCompatActivity {

    DatabaseReference singlequests;
    DatabaseReference dailyquests;
    DatabaseReference weeklyquests;

    Spinner type;
    EditText title, description, reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quest);

        singlequests = FirebaseDatabase.getInstance().getReference("singlequests");
        dailyquests = FirebaseDatabase.getInstance().getReference("dailyquests");
        weeklyquests = FirebaseDatabase.getInstance().getReference("weeklyquests");

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        reward = findViewById(R.id.reward);
        type = findViewById(R.id.type);

        String[] options = new String[]{"Single Quest", "Daily Quest", "Weekly Quest"};
        ArrayAdapter<String> optionadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        type.setAdapter(optionadapter);
    }

    public void startQuest(View v){
        String title = this.title.getText().toString().trim();
        String description = this.description.getText().toString().trim();

        String reward = this.reward.getText().toString().trim();
        int rewardvalue;
        try{
            rewardvalue = Integer.parseInt(reward);
        }catch (Exception e){
            rewardvalue = 100;
        }

        String owner = "default";

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(today);

        int type;
        String id;
        Quest quest;
        if(this.type.getSelectedItem().equals("Single Quest")){
            type = 0;
            id = singlequests.push().getKey();
            quest = new Quest(id, title, description, date, owner, rewardvalue, type);
            singlequests.child(id).setValue(quest);
        }
        else if(this.type.getSelectedItem().equals("Daily Quest")){
            type = 1;
            id = dailyquests.push().getKey();
            quest = new Quest(id, title, description, date, owner, rewardvalue, type);
            dailyquests.child(id).setValue(quest);
        }
        else{
            type = 2;
            id = weeklyquests.push().getKey();
            quest = new Quest(id, title, description, date, owner, rewardvalue, type);
            weeklyquests.child(id).setValue(quest);
        }

        finish();
    }
}
