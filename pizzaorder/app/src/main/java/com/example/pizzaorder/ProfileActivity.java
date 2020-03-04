package com.example.pizzaorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pizzaorder.common.Common;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    TextView Name, Phone, Address, House, Road, Block;
    Button EditButton, BackButton;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        FirebaseApp.initializeApp(this);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user");

        final String usPhone = getIntent().getStringExtra("userPhone");

        EditButton = (Button) findViewById(R.id.edit);
        BackButton = (Button) findViewById(R.id.back);
        Name = (TextView) findViewById(R.id.name);
        Phone = (TextView) findViewById(R.id.phone);
        Address = (TextView) findViewById(R.id.address);
        House = (TextView) findViewById(R.id.house);
        Road = (TextView) findViewById(R.id.road);
        Block = (TextView) findViewById(R.id.block);

        Name.setText(Common.currentUser.getName());
        Phone.setText(usPhone);
        Address.setText(Common.currentUser.getAddress());
        House.setText(Common.currentUser.getHouse());
        Road.setText(Common.currentUser.getRoad());
        Block.setText(Common.currentUser.getBlock());

        EditButton.setOnClickListener(new View.OnClickListener() {

            public void onClick (View view){

                Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                intent.putExtra("userPhone",  usPhone);
                startActivity(intent);

            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {

            public void onClick (View view){

                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                intent.putExtra("userPhone", usPhone);
                startActivity(intent);
            }
        });

    }
}
