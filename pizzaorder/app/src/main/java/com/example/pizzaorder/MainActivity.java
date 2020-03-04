package com.example.pizzaorder;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pizzaorder.common.Common;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText Phone, Password;
    Button LogInButton, RegisterButton;
    ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        LogInButton = (Button) findViewById(R.id.login);
        RegisterButton = (Button) findViewById(R.id.register);
        Phone = (EditText) findViewById(R.id.phone);
        Password = (EditText) findViewById(R.id.password);

        final ProgressDialog dialog = new ProgressDialog(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("user");

        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setMessage("Loging in please wait...");
                dialog.setIndeterminate(true);
                dialog.show();

                myRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(Phone.getText().toString()).exists()) {
                            dialog.dismiss();
                            User user = dataSnapshot.child(Phone.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals(Password.getText().toString())) {

                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                intent.putExtra("userPhone", Phone.getText().toString());
                                Common.currentUser = user;
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(MainActivity.this, "Wrong password !", Toast.LENGTH_SHORT).show();
                                Phone.getText().clear();
                                Password.getText().clear();
                            }
                        } else {
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, "Phone number does not exists !", Toast.LENGTH_SHORT).show();
                            Phone.getText().clear();
                            Password.getText().clear();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
    }
}