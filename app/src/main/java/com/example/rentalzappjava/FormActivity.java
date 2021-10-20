package com.example.rentalzappjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class FormActivity extends AppCompatActivity {
    TextInputLayout textInputLayout;
    AutoCompleteTextView furTypeCompleteTextView,bedRoomCompleteTextView;
    Button create;
    EditText property,dateTime,price,name,bedRoom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //bedroom
        textInputLayout = findViewById(R.id.bedRoom);
        bedRoomCompleteTextView = findViewById(R.id.chooseBedRoom);
        String[] items = {"Studio","1","2","3"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(FormActivity.this,R.layout.dropdown,items);
        bedRoomCompleteTextView.setAdapter(itemAdapter);
        // fur type
        textInputLayout = findViewById(R.id.furType);
        furTypeCompleteTextView = findViewById(R.id.chooseFurType);
        String[] furItems = {"Furnished","Unfurnished","Part Furnished"};
        ArrayAdapter<String> itemAdapterFur = new ArrayAdapter<>(FormActivity.this,R.layout.dropdown,furItems);
        furTypeCompleteTextView.setAdapter(itemAdapterFur);
        // editText variable
        property = (EditText) findViewById(R.id.property);
        dateTime = (EditText) findViewById(R.id.dateTime);
        price = (EditText) findViewById(R.id.price);
        name = (EditText) findViewById(R.id.name);
        // btn variable
        create = (Button) findViewById(R.id.btnCreate);


        // property Text
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }



}