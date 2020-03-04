package com.example.pizzaorder;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class OrderActivity extends ListActivity implements View.OnClickListener{

    public boolean hasShown = false;
    public TextView Total;
    static String usPhone;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getListView().setChoiceMode(1);


        usPhone = getIntent().getStringExtra("userPhone");
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onNothingSelected(AdapterView arg0) {

            }

            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {

            }

        });

        View getNewPizzaButton = findViewById(R.id.newPizza);
        getNewPizzaButton.setOnClickListener(this);
        View getCheckoutButton = findViewById(R.id.checkout);
        getCheckoutButton.setOnClickListener(this);
        Total = (TextView) findViewById(R.id.total);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.newPizza:
                if (!hasShown) {
                    openHowToDialog();
                    hasShown = true;
                } else {
                    openSizeSelectionDialog();
                }
                break;
            case R.id.checkout:
                if (getListView().getCount() != 0)
                    checkOutDialog();
                else {
                    new AlertDialog.Builder(this).setTitle("Info").setMessage("You must order a pizza, before you can checkout.").setCancelable(false)
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    openSizeSelectionDialog();
                                }
                            }).show();
                }
                break;
        }

    }

    public void openHowToDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.how_to_title).setMessage(R.string.how_to_text).setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openSizeSelectionDialog();
                    }
                }).show();
    }

    public void openSizeSelectionDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.pizza_size).setItems(R.array.pizza_size, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String size;
                if (which == 0)
                    size = "Small";
                else if (which == 1)
                    size = "Medium";
                else
                    size = "Large";
                openCrustSelectionDialog(size);
            }
        }).show();
    }

    public void openCrustSelectionDialog(final String size) {
        new AlertDialog.Builder(this).setTitle(R.string.crust_selection).setItems(R.array.pizza_crust, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String crust;
                if (which == 0)
                    crust = "Thin";
                else if (which == 1)
                    crust = "Thick";
                else if (which == 2)
                    crust = "Deep";
                else
                    crust = "Stuffed";

                String sz = size;
                Intent intent = new Intent(OrderActivity.this, NewPizzaActivity.class);
                intent.putExtra("Size", sz);
                intent.putExtra("Crust", crust);
                intent.putExtra("Phone", usPhone);
                startActivity(intent);

            }
        }).show();
    }

    public void checkOutDialog() {

    }
}
