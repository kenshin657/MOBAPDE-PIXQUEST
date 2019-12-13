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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class shop extends AppCompatActivity {

    DatabaseReference userData;
    DatabaseReference Userskin;
    String un, id;
    User user;

    TextView txt;

    ListView skinlist;
    String[] skinname={"baseskin","skin1","skin2","skin3","skin4","skin5","skin6","skin7","skin9","skin10"};
    String[] skinprice={"0","1","100","3","4","5","6","7","8","9","10"};
    Integer[] imgid={R.drawable.baseskin,R.drawable.skin1,R.drawable.skin2,R.drawable.skin3,R.drawable.skin4,R.drawable.skin5,R.drawable.skin6,R.drawable.skin7,R.drawable.skin8,R.drawable.skin9,R.drawable.skin10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        userData = FirebaseDatabase.getInstance().getReference("users");
        Userskin = FirebaseDatabase.getInstance().getReference("skins");

        txt = findViewById(R.id.creditshow);

        Intent intent = getIntent();
        un = intent.getStringExtra("USER");

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
                Toast.makeText(getApplicationContext(), "lol"+title, Toast.LENGTH_LONG).show();
                buy(type);
                b.dismiss();
            }
        });

        equipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "lol"+title, Toast.LENGTH_LONG).show();
                //deletepost(postId , type);
                //b.dismiss();
            }
        });




    }

    private void buy(int id){
        if(user.getCredit()>Integer.parseInt(skinprice[id])){

        }
        else{
            Toast.makeText(getApplicationContext(), "cantafford", Toast.LENGTH_LONG).show();
        }
    }







    public  void back(View v){
        finish();
    }

}
