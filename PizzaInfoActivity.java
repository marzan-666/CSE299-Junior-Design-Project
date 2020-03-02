package com.example.pizzaorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class PizzaInfoActivity extends Activity {

    TextView Size, Crust, Whole, Left, Right, Total;
    Button PayButton, BackButton;
    static String ppsize, ppcrust, ppwhole, ppleft, ppright, uuphone, paym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_info);

        ppsize = getIntent().getStringExtra("pSize");
        ppcrust = getIntent().getStringExtra("pCrust");
        ppwhole = getIntent().getStringExtra("pWhole");
        ppleft = getIntent().getStringExtra("pLeft");
        ppright = getIntent().getStringExtra("pRight");
        uuphone = getIntent().getStringExtra("uPhone");

        PayButton = (Button) findViewById(R.id.payM);
        BackButton = (Button) findViewById(R.id.cancel);
        Size = (TextView) findViewById(R.id.size);
        Crust = (TextView) findViewById(R.id.crust);
        Whole = (TextView) findViewById(R.id.whole);
        Left = (TextView) findViewById(R.id.left);
        Right = (TextView) findViewById(R.id.right);
        Total = (TextView) findViewById(R.id.stotal);

        Size.setText(ppsize);
        Crust.setText(ppcrust);
        Whole.setText(ppwhole);
        Left.setText(ppleft);
        Right.setText(ppright);
        String temp = null;
        if(ppsize.equals("Small"))
            temp = "400tk";
        else if(ppsize.equals("Medium"))
            temp = "650Tk";
        else if(ppsize.equals("Large"))
            temp = "800Tk";
        Total.setText("Total amount: " + temp);
        PayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PizzaInfoActivity.this);
                alertDialogBuilder.setTitle("Select Payment Method").setItems(R.array.payment, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0)
                            bkashTxnDialog();
                        else {
                            paym = "Cash On Delivery";
                            Intent intent = new Intent(PizzaInfoActivity.this, ConfirmActivity.class);
                            intent.putExtra("Size", ppsize);
                            intent.putExtra("Crust", ppcrust);
                            intent.putExtra("Whole", ppwhole);
                            intent.putExtra("Left", ppleft);
                            intent.putExtra("Right", ppright);
                            intent.putExtra("Phone", uuphone);
                            intent.putExtra("Payment", paym);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).show();

            }

            private void bkashTxnDialog() {
                LayoutInflater li = LayoutInflater.from(PizzaInfoActivity.this);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PizzaInfoActivity.this);

                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editText);

                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                paym = "Bkash TxnId: " + userInput.getText().toString();
                                Intent intent = new Intent(PizzaInfoActivity.this, ConfirmActivity.class);
                                intent.putExtra("Size", ppsize);
                                intent.putExtra("Crust", ppcrust);
                                intent.putExtra("Whole", ppwhole);
                                intent.putExtra("Left", ppleft);
                                intent.putExtra("Right", ppright);
                                intent.putExtra("Phone", uuphone);
                                intent.putExtra("Payment", paym);
                                startActivity(intent);
                                finish();
                            }
                        });
                alertDialogBuilder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();
            }
        });

    }
}
