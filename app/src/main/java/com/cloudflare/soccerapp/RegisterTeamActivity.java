package com.cloudflare.soccerapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    String league;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_team);

        CoordinatorLayout layout = (CoordinatorLayout)findViewById(R.id.coordinator_team);
        Animation anim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.layout_anim);
        layout.startAnimation(anim);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Team");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        editTeamName = (EditText) findViewById(R.id.editTeamName);
        editTeamCaptain = (EditText) findViewById(R.id.editTeamCaptain);
        editChairman = (EditText) findViewById(R.id.editChairman);
        editFounded = (EditText) findViewById(R.id.editFounded);
        spinner = (Spinner) findViewById(R.id.spinner);

        AddTeamMembers = (Button) findViewById(R.id.btnAddTeamMembers);
        Date = (Button) findViewById(R.id.btnDate);



        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.Leagues,R.layout.dropdown_items);
        adapter.setDropDownViewResource(R.layout.dropdown_items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                league = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                league = parent.getItemAtPosition(0).toString();
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
                        parameters.put("Chairman",editChairman.getText().toString());
                        parameters.put("Date",editFounded.getText().toString());
                        parameters.put("League",league);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}