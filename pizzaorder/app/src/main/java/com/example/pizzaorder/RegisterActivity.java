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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    EditText Phone, Name, Address, House, Block, Road, Password;
    Button RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Phone = (EditText) findViewById(R.id.phone);
        Name = (EditText) findViewById(R.id.name);
        Address = (EditText) findViewById(R.id.address);
        House = (EditText) findViewById(R.id.house);
        Road = (EditText) findViewById(R.id.road);
        Block = (EditText) findViewById(R.id.block);
        Password = (EditText) findViewById(R.id.password);
        RegisterButton = (Button) findViewById(R.id.register);

        final ProgressDialog dialog = new ProgressDialog(this);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("user");

        RegisterButton.setOnClickListener(new View.OnClickListener() {
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
                            Phone.getText().clear();
                            Name.getText().clear();
                            Address.getText().clear();
                            House.getText().clear();
                            Road.getText().clear();
                            Block.getText().clear();
                            Password.getText().clear();
                        }
                        else {
                            dialog.dismiss();
                            User user = new User(Address.getText().toString(), Block.getText().toString(), House.getText().toString(), Name.getText().toString(), Password.getText().toString(), Road.getText().toString());
                            myRef.child(Phone.getText().toString()).setValue(user);
                            Toast.makeText(RegisterActivity.this, "Successful !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
