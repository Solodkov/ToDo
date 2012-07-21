package com.sample;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Addition extends Activity {

    EditText task, comment;
    Button toAdd, date;
    ToDoApp toDoApp;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addition);

        task = (EditText) findViewById(R.id.task);
        comment = (EditText) findViewById(R.id.comment);
        date = (Button) findViewById(R.id.date);
        toAdd = (Button) findViewById(R.id.toAdd);
        toDoApp = (ToDoApp) getApplication();


        date.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getSupportFragmentManager(), "datePicker");
        }
    });

        toAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskText = task.getText().toString();
                String commentText = comment.getText().toString();

                toDoApp.setData(taskText, commentText);

                Toast.makeText(getApplicationContext(),"Task added",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Main.class);
                startActivity(intent);
            }
        });

    class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
        }
    }
    }
}