package com.example.pizzaorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pizzaorder.common.Common;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileEditActivity extends AppCompatActivity {

    EditText Name, Phone, Address, House, Road, Block, Pass;
    Button UpdateButton, BackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        FirebaseApp.initializeApp(this);

        final ProgressDialog dialog = new ProgressDialog(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("user");
        final String urPhone = getIntent().getStringExtra("userPhone");

        UpdateButton = (Button) findViewById(R.id.update);
        BackButton = (Button) findViewById(R.id.cancel);
        Name = (EditText) findViewById(R.id.name);
        Phone = (EditText) findViewById(R.id.phone);
        Address = (EditText) findViewById(R.id.address);
        House = (EditText) findViewById(R.id.house);
        Road = (EditText) findViewById(R.id.road);
        Block = (EditText) findViewById(R.id.block);
        Pass = (EditText) findViewById(R.id.pass);

        Name.setText(Common.currentUser.getName());
        Phone.setText(urPhone);
        Address.setText(Common.currentUser.getAddress());
        House.setText(Common.currentUser.getHouse());
        Road.setText(Common.currentUser.getRoad());
        Block.setText(Common.currentUser.getBlock());
        Pass.setText(Common.currentUser.getPassword());

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setMessage("Please wait...");
                dialog.setIndeterminate(true);
                dialog.show();

                myRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(Phone.getText().toString()).exists()) {

                            dialog.dismiss();
                            User user = new User(Address.getText().toString(), Block.getText().toString(), House.getText().toString(), Name.getText().toString(), Pass.getText().toString(), Road.getText().toString());
                            myRef.child(Phone.getText().toString()).setValue(user);
                            Toast.makeText(ProfileEditActivity.this, "Successful !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProfileEditActivity.this, ProfileActivity.class);
                            intent.putExtra("userPhone", urPhone);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProfileEditActivity.this, ProfileActivity.class);
                intent.putExtra("userPhone", urPhone);
                startActivity(intent);

            }
        });
    }
}
