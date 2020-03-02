package com.example.pizzaorder;


import static android.provider.BaseColumns._ID;
import static com.example.pizzaorder.PizzaData.SIZE;
import static com.example.pizzaorder.PizzaData.TABLE_NAME;
import static com.example.pizzaorder.PizzaData.TOPPINGS_LEFT;
import static com.example.pizzaorder.PizzaData.TOPPINGS_RIGHT;
import static com.example.pizzaorder.PizzaData.TOPPINGS_WHOLE;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzaorder.R;

public class NewPizzaActivity extends Activity implements OnClickListener {

    public RadioButton wholeRadio;
    public RadioButton leftRadio;
    public RadioButton rightRadio;
    public TextView wholeText;
    public TextView leftText;
    public TextView rightText;
    public ArrayList<String> wList = new ArrayList<String>();
    public ArrayList<String> lList = new ArrayList<String>();
    public ArrayList<String> rList = new ArrayList<String>();
    public ArrayList<String> topingList = new ArrayList<String>();
    int id = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pizza);
        createToppingList();

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null && bundle.size() > 0)
            id = bundle.getInt(_ID);

        wholeRadio = (RadioButton) findViewById(R.id.whole);
        leftRadio = (RadioButton) findViewById(R.id.left);
        rightRadio = (RadioButton) findViewById(R.id.right);
        wholeText = (TextView) findViewById(R.id.whole_text);
        leftText = (TextView) findViewById(R.id.left_text);
        rightText = (TextView) findViewById(R.id.right_text);


        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(this));

        gallery.setOnItemClickListener(new OnItemClickListener() {
            @SuppressWarnings("rawtypes")
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                if (wList.isEmpty())
                    wList.clear();
                if (lList.isEmpty())
                    lList.clear();
                if (rList.isEmpty())
                    rList.clear();
                if (wholeRadio.isChecked()) { // Whole Pizza
                    if (wList.contains(topingList.get(position))) {
                        displayMessage(position, " removed");
                        wList.remove(wList.indexOf(topingList.get(position)));
                        if (wList.isEmpty())
                            wholeText.setText("");
                        else
                            wholeText.setText(editString(wList));
                    } else if (rList.contains(topingList.get(position))) {
                        rList.remove(rList.indexOf(topingList.get(position)));
                        wList.add(topingList.get(position));
                        rightText.setText(editString(rList));
                        wholeText.setText(editString(wList));
                        displayMessage(position, " added");
                    } else if (lList.contains(topingList.get(position))) {
                        lList.remove(lList.indexOf(topingList.get(position)));
                        wList.add(topingList.get(position));
                        leftText.setText(editString(lList));
                        wholeText.setText(editString(wList));
                        displayMessage(position, " added");
                    } else {
                        displayMessage(position, " added");
                        wList.add(topingList.get(position));
                        wholeText.setText(editString(wList));
                    }
                } else if (leftRadio.isChecked()) { // Left side of pizza.
                    if (lList.contains(topingList.get(position))) {
                        displayMessage(position, " removed");
                        lList.remove(lList.indexOf(topingList.get(position)));
                        if (lList.isEmpty())
                            leftText.setText("");
                        else
                            leftText.setText(editString(lList));
                    } else if (rList.contains(topingList.get(position))) { // Checking if the same topping is on the right side of pizza.
                        displayMessage(position, " added to whole pizza");
                        rList.remove(rList.indexOf(topingList.get(position)));
                        rightText.setText(editString(rList));
                        wList.add(topingList.get(position));
                        wholeText.setText(editString(wList));
                    } else if (wList.contains(topingList.get(position))) {
                        displayMessage(position, " have already been added to the whole pizza");
                    } else {
                        displayMessage(position, " added");
                        lList.add(topingList.get(position));
                        leftText.setText(editString(lList));
                    }
                } else { // Right side of pizza.
                    if (rList.contains(topingList.get(position))) {
                        displayMessage(position, " removed");
                        rList.remove(rList.indexOf(topingList.get(position)));
                        if (rList.isEmpty())
                            rightText.setText("");
                        else
                            rightText.setText(editString(rList));
                    } else if (lList.contains(topingList.get(position))) { // Checking if the same topping is on the left side of pizza.
                        displayMessage(position, " added to whole pizza");
                        lList.remove(lList.indexOf(topingList.get(position)));
                        leftText.setText(editString(lList));
                        wList.add(topingList.get(position));
                        wholeText.setText(editString(wList));
                    } else if (wList.contains(topingList.get(position))) {
                        displayMessage(position, " have already been added to the whole pizza");
                    } else {
                        displayMessage(position, " added");
                        rList.add(topingList.get(position));
                        rightText.setText(editString(rList));
                    }
                }
            }

        });

        View AddToCartButton = findViewById(R.id.add_to_cart_button);
        AddToCartButton.setOnClickListener(this);
        View CancelButton = findViewById(R.id.cancel_button);
        CancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_to_cart_button:
                String toppingsWhole = "";
                String toppingsLeft = "";
                String toppingsRight = "";
                if (wList.size() > 0) {
                    toppingsWhole = toppingsWhole + editString(wList);
                }
                if (lList.size() > 0) {
                    toppingsLeft = toppingsLeft + editString(lList);
                }
                if (rList.size() > 0) {
                    toppingsRight = toppingsRight + editString(rList);
                }
                final String size = getIntent().getStringExtra("Size");
                final String crust = getIntent().getStringExtra("Crust");
                final String phone = getIntent().getStringExtra("Phone");
                Intent intent = new Intent(NewPizzaActivity.this, PizzaInfoActivity.class);
                intent.putExtra("pSize", size);
                intent.putExtra("pCrust", crust);
                intent.putExtra("pWhole", toppingsWhole);
                intent.putExtra("pLeft", toppingsLeft);
                intent.putExtra("pRight", toppingsRight);
                intent.putExtra("uPhone", phone);
                startActivity(intent);
                break;
            case R.id.cancel_button:
                Intent intent1 = new Intent(NewPizzaActivity.this, OrderActivity.class);
                startActivity(intent1);
                finish();
                break;
        }

    }

    private void createToppingList() {
        topingList.add("Anchovies");
        topingList.add("Bacon");
        topingList.add("Banana Peppers");
        topingList.add("Black Olives");
        topingList.add("Chicken");
        topingList.add("Green Peppers");
        topingList.add("Ham");
        topingList.add("Jalapeno Peppers");
        topingList.add("Extra Cheese");
        topingList.add("Mushrooms");
        topingList.add("Onion");
        topingList.add("Pepperoni");
        topingList.add("Pineapple");
        topingList.add("Sausage");
        topingList.add("Roma Tomatoes");
    }

    private void displayMessage(int position, String message) {
        Toast.makeText(NewPizzaActivity.this, topingList.get(position) + message, Toast.LENGTH_SHORT).show();
    }

    private String editString(List<String> list) {
        String toppings = "";
        String withOutComma;
        for (String item : list) {
            toppings += item + ", ";
        }
        if (toppings.equals(""))
            withOutComma = "";
        else
            withOutComma = toppings.substring(0, toppings.length() - 2);
        return withOutComma;
    }

    private void setArrayList(ArrayList<String> list, String[] toppings) {
        for (int i = 0; i < toppings.length; i++) {
            list.add(toppings[i].trim());
        }
    }

}