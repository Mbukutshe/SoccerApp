package com.cloudflare.soccerapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterTeamActivity extends AppCompatActivity implements View.OnClickListener {

    Button Cancel, AddTeamMembers, Date;
    EditText editTeamName, editTeamCaptain, editChairman, editFounded;
    private int mYear, mMonth, mDay;
    Spinner spinner;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_team);

        editTeamName = (EditText) findViewById(R.id.editTeamName);
        editTeamCaptain = (EditText) findViewById(R.id.editTeamCaptain);
        editChairman = (EditText) findViewById(R.id.editChairman);
        editFounded = (EditText) findViewById(R.id.editFounded);
        spinner = (Spinner) findViewById(R.id.spinner);


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
                String url = "http://soccer.payghost.co.za/teamdata.php";
                StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response)
                    {
                        Intent intent = new Intent(RegisterTeamActivity.this, TeamMembersActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> parameters = new HashMap<String, String>();
                        parameters.put("Name",editTeamName.getText().toString());
                        parameters.put("Captain",editTeamCaptain.getText().toString());
                        parameters.put("Chairman",editTeamCaptain.getText().toString());
                        parameters.put("Date",editFounded.getText().toString());
                        return parameters;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);


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