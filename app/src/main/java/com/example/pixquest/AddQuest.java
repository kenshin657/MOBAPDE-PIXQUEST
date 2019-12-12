package com.example.pixquest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddQuest extends AppCompatActivity {

    DatabaseReference singlequests;
    DatabaseReference dailyquests;
    DatabaseReference weeklyquests;

    Spinner type;
    EditText title, description, reward, dateView, timeView;
    final Calendar cal = Calendar.getInstance();
    int mYear, mMonth, mDay, mHour, mMinute;
    String un;

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
        dateView = findViewById(R.id.dateView);
        timeView = findViewById(R.id.timeView);

        Intent intent = getIntent();
        un = intent.getStringExtra("USER");

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

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(today);
        String lastcomplete = ("2000-01-01");

        int type;
        String id;
        Quest quest;
        if(this.type.getSelectedItem().equals("Single Quest")){
            type = 0;
            id = singlequests.push().getKey();
            quest = new Quest(id, title, description, date, lastcomplete, un, rewardvalue, type);
            singlequests.child(id).setValue(quest);
        }
        else if(this.type.getSelectedItem().equals("Daily Quest")){
            type = 1;
            id = dailyquests.push().getKey();
            quest = new Quest(id, title, description, date, lastcomplete, un, rewardvalue, type);
            dailyquests.child(id).setValue(quest);
        }
        else{
            type = 2;
            id = weeklyquests.push().getKey();
            quest = new Quest(id, title, description, date, lastcomplete, un, rewardvalue, type);
            weeklyquests.child(id).setValue(quest);
        }


        Intent alertIntent = new Intent(this, AlertReceiver.class);
        alertIntent.putExtra("TITLE", title);


        Long alertTime = cal.getTimeInMillis();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Log.d("DEBUG", Long.toString(cal.getTimeInMillis()));

//        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime,PendingIntent.getBroadcast(this, 1, alertIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT));

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alertTime, AlarmManager.INTERVAL_DAY, PendingIntent.getBroadcast(this,
                1,alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        finish();
    }

    public void datePick(View v) {
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void timePick(View v) {
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        timeView.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
