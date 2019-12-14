package com.example.pixquest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class shop extends AppCompatActivity {

    DatabaseReference userData;
    DatabaseReference Userskin;
    String un, id;
    User user;
    //Items items;

    TextView txt;

    ListView skinlist;
    String[] skinname={"baseskin","skin1","skin2","skin3","skin4","skin5","skin6","skin7","skin9","skin10"};
    String[] skinprice={"0","10","50","80","100","120","150","180","200","250","500"};
    Integer[] imgid={R.drawable.baseskin,R.drawable.skin1,R.drawable.skin2,R.drawable.skin3,R.drawable.skin4,R.drawable.skin5,R.drawable.skin6,R.drawable.skin7,R.drawable.skin8,R.drawable.skin9,R.drawable.skin10};

    ArrayList<Items> ownedskin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        userData = FirebaseDatabase.getInstance().getReference("users");
        Userskin = FirebaseDatabase.getInstance().getReference("skins");

        txt = findViewById(R.id.creditshow);

        ownedskin = new ArrayList<>();

        Intent intent = getIntent();
        un = intent.getStringExtra("USER");
        //id = intent.getStringExtra("ID");

        skinlist = findViewById(R.id.shop);
        CustomShopView customShopView = new CustomShopView(this,skinname,skinprice,imgid);
        skinlist.setAdapter(customShopView);


        skinlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //System.out.println("this was clicked");
                //Toast.makeText(getApplicationContext(), "clicked" +position, Toast.LENGTH_LONG).show();
                showShopDialog(skinname[position],position);

                return true;
            }
        });




        userData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot item: dataSnapshot.getChildren()){
                    User user = item.getValue(User.class);
                    if(user.getUsername().equals(un)){
                        shop.this.user = user;
                        txt.setText(String.valueOf(user.getCredit()));
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Userskin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item: dataSnapshot.getChildren()) {
                    Items items = item.getValue(Items.class);
                    if(items.getOwner().equals(un)){
                        ownedskin.add(items);
                        //System.out.println("added");

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void showShopDialog(final String title, final int type) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.shop_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buybtn = (Button) dialogView.findViewById(R.id.Buybtn);
        final Button equipbtn = (Button) dialogView.findViewById(R.id.Equipbtn);

        dialogBuilder.setTitle(title);

        final AlertDialog b = dialogBuilder.create();
        b.show();



        buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "lol"+title, Toast.LENGTH_LONG).show();
                buy(type);
                b.dismiss();
            }
        });

        equipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equipzz(type);
                b.dismiss();
            }
        });




    }

    private void buy(int index){

        Boolean check = false;
        for(int i =0; i<ownedskin.size(); i++){
            if(ownedskin.get(i).getItemname().equals(skinname[index])){
                check=true;
            }
        }

        if(check==true){
            Toast.makeText(getApplicationContext(), "Already have it", Toast.LENGTH_SHORT).show();
        }
        else {
            if(user.getCredit()<Integer.parseInt(skinprice[index])){
                Toast.makeText(getApplicationContext(), "You can't afford it", Toast.LENGTH_SHORT).show();
            }
            else {
                buythis(index);
            }
        }

    }


    private void buythis(int index){
        String key;
        Items items;

        key = Userskin.push().getKey();
        items = new Items(key,skinname[index],skinname[index],un);
        Userskin.child(key).setValue(items);
        user.setCredit(user.getCredit()-Integer.parseInt(skinprice[index]));
        user.setImg(skinname[index]);
        Toast.makeText(getApplicationContext(), "Got it", Toast.LENGTH_SHORT).show();
        userData.child(user.getId()).setValue(user);


    }

    private void equipzz(int index){
        Boolean check = false;
        for(int i =0; i<ownedskin.size(); i++){
            if(ownedskin.get(i).getItemname().equals(skinname[index])){
                check=true;
            }
        }

        if(check==true){
            Toast.makeText(getApplicationContext(), "Equipping", Toast.LENGTH_SHORT).show();
            user.setImg(skinname[index]);
            userData.child(user.getId()).setValue(user);

        }
        else {
                Toast.makeText(getApplicationContext(), "You dont have this avatar", Toast.LENGTH_SHORT).show();
        }
    }



    public  void back(View v){
        finish();
    }

}
