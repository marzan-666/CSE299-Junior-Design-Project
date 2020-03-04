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

public class HomeActivity extends AppCompatActivity {

    TextView Name;
    Button ProfileButton, OrderButton, LogOutButton;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseApp.initializeApp(this);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("user");

        final String uPhone = getIntent().getStringExtra("userPhone");

        ProfileButton = (Button) findViewById(R.id.profile);
        LogOutButton = (Button) findViewById(R.id.logout);
        OrderButton = (Button) findViewById(R.id.order);
        Name = (TextView) findViewById(R.id.name);
        Name.setText(Common.currentUser.getName());

        ProfileButton.setOnClickListener(new View.OnClickListener() {

            public void onClick (View view){

                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("userPhone", uPhone);
                startActivity(intent);
            }
        });

        OrderButton.setOnClickListener(new View.OnClickListener() {

            public void onClick (View view){

                Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
                intent.putExtra("userPhone", uPhone);
                startActivity(intent);
            }
        });

        LogOutButton.setOnClickListener(new View.OnClickListener() {

            public void onClick (View view){

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}