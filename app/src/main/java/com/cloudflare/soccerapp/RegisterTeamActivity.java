package com.cloudflare.soccerapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class RegisterTeamActivity extends AppCompatActivity implements View.OnClickListener {

    Button Cancel, AddTeamMembers, Date;
    EditText editTeamName, editTeamCaptain, editChairman, editFounded;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_team);

        editTeamName = (EditText) findViewById(R.id.editTeamName);
        editTeamCaptain = (EditText) findViewById(R.id.editTeamCaptain);
        editChairman = (EditText) findViewById(R.id.editChairman);
        editFounded = (EditText) findViewById(R.id.editFounded);

        Cancel = (Button) findViewById(R.id.btnCancel);
        AddTeamMembers = (Button) findViewById(R.id.btnAddTeamMembers);
        Date = (Button) findViewById(R.id.btnDate);

        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterTeamActivity.this, MainActivity.class);
                startActivity(intent);//onclick takes you to MainActivity.java
            }
        });
        AddTeamMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterTeamActivity.this, TeamMembersActivity.class);
                startActivity(intent); //onclick takes you to TeamMemberActivity.java

            }
        });

        Date.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == Date) {

            // Get Current Date and v is an object for View
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                           editFounded.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }
    }
}