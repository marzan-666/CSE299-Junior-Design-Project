package com.example.pizzaorder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConfirmActivity extends Activity {

    static String size, crust, whole, left, right, phone, payment, temp;
    TextView cSize, cCrust, cWhole, cLeft, cRight, cTotal, cPhone, cPayment;
    Button ConfirmButton, BackButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        size = getIntent().getStringExtra("Size");
        crust = getIntent().getStringExtra("Crust");
        whole = getIntent().getStringExtra("Whole");
        left = getIntent().getStringExtra("Left");
        right = getIntent().getStringExtra("Right");
        phone = getIntent().getStringExtra("Phone");
        payment = getIntent().getStringExtra("Payment");

        ConfirmButton = (Button) findViewById(R.id.confirm);
        BackButton = (Button) findViewById(R.id.back);
        cSize = (TextView) findViewById(R.id.size);
        cCrust = (TextView) findViewById(R.id.crust);
        cWhole = (TextView) findViewById(R.id.whole);
        cLeft = (TextView) findViewById(R.id.left);
        cRight = (TextView) findViewById(R.id.right);
        cTotal = (TextView) findViewById(R.id.total);
        cPhone = (TextView) findViewById(R.id.cphone);
        cPayment = (TextView) findViewById(R.id.payment);

        cSize.setText(size);
        cCrust.setText(crust);
        cWhole.setText(whole);
        cLeft.setText(left);
        cRight.setText(right);
        if(size.equals("Small"))
            temp = "400tk";
        else if(size.equals("Medium"))
            temp = "650Tk";
        else if(size.equals("Large"))
            temp = "800Tk";
        cTotal.setText("Total amount: " + temp);
        cPhone.setText(phone);
        cPayment.setText(payment);

        final ProgressDialog dialog = new ProgressDialog(this);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("order");

        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.setMessage("Please wait...");
                dialog.setIndeterminate(true);
                dialog.show();

                myRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                       dialog.dismiss();
                       DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                       String dateT = df.format(Calendar.getInstance().getTime());
                       Order order = new Order(size, crust, whole, left, right, temp, payment, dateT);
                       myRef.child(phone).setValue(order);
                       Toast.makeText(ConfirmActivity.this, "Confirmed ! It will take 25minutes to deliver !", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(ConfirmActivity.this, HomeActivity.class);
                       startActivity(intent);
                       finish();
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
                Intent intent = new Intent(ConfirmActivity.this, PizzaInfoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
