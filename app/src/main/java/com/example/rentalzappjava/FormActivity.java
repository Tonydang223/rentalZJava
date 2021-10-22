package com.example.rentalzappjava;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.SimpleFormatter;

public class FormActivity extends AppCompatActivity {
    TextInputLayout textInputLayout;
    AutoCompleteTextView furTypeCompleteTextView,bedRoomCompleteTextView;
    Button create;
    private EditText property,dateTime,price,name,note;
    String regexLetter = "^[a-zA-Z\\s]*$";

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
        dateTime.setInputType(InputType.TYPE_NULL);
        price = (EditText) findViewById(R.id.price);
        name = (EditText) findViewById(R.id.name);
        note = (EditText) findViewById(R.id.note);
        // btn variable
        create = (Button) findViewById(R.id.btnCreate);
        dateTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDialogDateTime(dateTime);
                }else{
                    dateTime.setError(null);
                }
            }
        });
        bedRoomCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    bedRoomCompleteTextView.setError(null);
                }
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.btnCreate){
                    if (!validateDateTime() | !validatePrice() | !validateName() | !validatebedRoom() | !validateProper()) {
                        Toast.makeText(FormActivity.this,"fail!!!",Toast.LENGTH_LONG).show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(FormActivity.this,R.style.Theme_AppCompat_Dialog_Alert);
                        View customeLayout = getLayoutInflater().inflate(R.layout.layout_dialog,null);
                        builder.setView(customeLayout);
                        builder.setTitle("Entered Details");
                        String enteredProper = property.getText().toString();
                        String enteredBedRoom = bedRoomCompleteTextView.getText().toString();
                        String enteredName = name.getText().toString();
                        String enteredPrice = price.getText().toString();
                        String enteredDateTime = dateTime.getText().toString();
                        String enteredNote = note.getText().toString();
                        String noteText = (enteredNote.length() ==0)?("None"):enteredNote;
                        String enteredFurType = furTypeCompleteTextView.getText().toString();
                        String furTypeText = (enteredFurType.length() ==0)?("None"):enteredFurType;
                        TextView resultDetail = customeLayout.findViewById(R.id.rental_detail);
                        String result = "Property Type:\t"+enteredProper+"\nBed Room:\t"
                                         +enteredBedRoom+"\nName:\t"+enteredName
                                         +"\nPrice:\t"+ enteredPrice
                                         +"\nDate and Time:\t"+enteredDateTime
                                         +"\nFurniture Type:\t"+furTypeText
                                         +"\nNote:\t"+noteText;
                        resultDetail.setText(result);
                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(FormActivity.this,"Ok!!!",Toast.LENGTH_LONG).show();
                                property.getText().clear();
                                bedRoomCompleteTextView.getText().clear();
                                dateTime.getText().clear();
                                price.getText().clear();
                                name.getText().clear();
                                note.getText().clear();
                                furTypeCompleteTextView.getText().clear();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(FormActivity.this,"Cancelled!!!",Toast.LENGTH_LONG).show();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }
            }
        });
    }
    private void showDialogDateTime(EditText dateTime){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
                        dateTime.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };
                new TimePickerDialog(FormActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };
        new DatePickerDialog(FormActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    private boolean validateProper(){
        String properType = property.getEditableText().toString().trim();
        if(properType.isEmpty()){
            property.setError("Please enter property type");
            return false;
        }
        else if(!properType.matches(regexLetter)){
            property.setError("The property contains only letters");
            return false;
        }
        else if(properType.length() > 25){
            property.setError("The property is not bigger than 25 characters");
            return false;
        }
        else{
            property.setError(null);
            return true;
        }
    }
    private boolean validateName(){
        String nameText = name.getEditableText().toString().trim();
        if(nameText.isEmpty()){
            name.setError("Please enter the name");
            return false;
        }
        else if(!nameText.matches(regexLetter)){
            name.setError("The name contains only letters");
            return false;
        }
        else if(nameText.length()>25){
            name.setError("The name is not bigger than 25 characters");
            return false;
        }
        else{
            name.setError(null);
            return true;
        }
    }
    private boolean validatePrice(){
        String priceText = price.getEditableText().toString().trim();
        if(priceText.isEmpty()){
            price.setError("Please enter the price");
            return false;
        }
        else{
            price.setError(null);
            return true;
        }
    }
    private boolean validatebedRoom(){
        String bedRoomText = bedRoomCompleteTextView.getText().toString().trim();
        if(bedRoomText.isEmpty()){
            bedRoomCompleteTextView.setError("Please enter bed room");
            return false;
        }else{
            bedRoomCompleteTextView.setError(null);
            return true;
        }
    }
    private boolean validateDateTime(){
        String timeText = dateTime.getEditableText().toString().trim();
        if(timeText.isEmpty()){
            dateTime.setError("Please enter date time");
            return false;
        }else{
            dateTime.setError(null);
            return true;
        }
    }


}
