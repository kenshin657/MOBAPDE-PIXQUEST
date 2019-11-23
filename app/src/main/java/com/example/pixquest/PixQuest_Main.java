package com.example.pixquest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PixQuest_Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pix_quest__main);
    }

    public void addQuest(View v){
        Intent intent = new Intent(this, AddQuest.class);
        startActivity(intent);
    }
}
