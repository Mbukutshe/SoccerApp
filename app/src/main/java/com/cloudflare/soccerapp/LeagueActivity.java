package com.cloudflare.soccerapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;

public class LeagueActivity extends AppCompatActivity {

    private int mYear, mMonth, mDay;
    EditText editDate, editVenue, editScore, editTime;
    Button Submit, Date,Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches);

        editDate = (EditText) findViewById(R.id.editDate);
        Submit= (Button) findViewById(R.id.btnMatches);
        editVenue= (EditText) findViewById(R.id.editVenue);
        editScore= (EditText) findViewById(R.id.editScore);
        Date= (Button) findViewById(R.id.btnDate2);
        Time = (Button) findViewById(R.id.btnTime);
        editTime =(EditText) findViewById(R.id.editTime);
        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // Create a new instance of TimePickerDialog and return it
                TimePickerDialog time =  new TimePickerDialog(LeagueActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        editTime.setText(hour + ":" + minute);
                    }
                }, hour, minute,
                        DateFormat.is24HourFormat((LeagueActivity.this)));
                time.show();
            }
        });

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v == Date) {

                    // Get Current Date and v is an object for View
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(LeagueActivity.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    editDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();

                }
            }
        });
    }
}
