package com.cloudflare.soccerapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LeagueActivity extends AppCompatActivity implements  View.OnClickListener{

    private int hour, minute,day,month,year;
    Button Submit, Date,Time;
    String TeamA,TeamB,Stadium,date;
    AppCompatSpinner teama,teamb,stadium;
    Toolbar toolbar;
    List<String> teams,stadiums;
    TimePicker timePicker;
    CalendarView calendarView;
    private DatabaseReference mdatabaseRef,fixture,stadiumReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches);
        Firebase.setAndroidContext(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Match");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        teama = (AppCompatSpinner)findViewById(R.id.teama);
        teamb = (AppCompatSpinner)findViewById(R.id.teamb);
        stadium = (AppCompatSpinner)findViewById(R.id.Stadium);
        timePicker = (TimePicker)findViewById(R.id.time);
        calendarView =(CalendarView)findViewById(R.id.calendarView);
        Submit = (Button)findViewById(R.id.btnMatches);

        mdatabaseRef = FirebaseDatabase.getInstance().getReference().child("Teams");
        fixture = FirebaseDatabase.getInstance().getReference().child("Fixture");
        stadiumReference = FirebaseDatabase.getInstance().getReference().child("Stadiums");
        teams= new ArrayList<String>();
        stadiums= new ArrayList<String>();
        Submit.setOnClickListener(this);

        hour=timePicker.getCurrentHour();
        minute=timePicker.getCurrentMinute();
        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                year=i;
                month = i1;
                day= i2;
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour = i;
                minute = i1;
            }
        });

        final LinearLayout layout = (LinearLayout)findViewById(R.id.match);
        Animation anim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.zoom_in);
        layout.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        teams.add("Team");
        getTeams();
        //team A Spinner
        ArrayAdapter<String> teamAadapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_items,teams);
        teamAadapter.setDropDownViewResource(R.layout.dropdown_items);
        teama .setAdapter(teamAadapter);
        teama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TeamA = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                TeamA = adapterView.getItemAtPosition(0).toString();
            }
        });
        //Team B Spinner
        ArrayAdapter<String> teamBadapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_items,teams);
        teamBadapter.setDropDownViewResource(R.layout.dropdown_items);
        teamb.setAdapter(teamBadapter);
        teamb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TeamB = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                TeamB = adapterView.getItemAtPosition(0).toString();
            }
        });
        stadiums.add("Stadium");
        getStadiums();
        //Stadium Spinner
        ArrayAdapter<String> stadiumAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown_items,stadiums);
        //stadiumAdapter.setDropDownViewResource(R.layout.dropdown_items);
        stadium.setAdapter(stadiumAdapter);
        stadium.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Stadium = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Stadium = adapterView.getItemAtPosition(0).toString();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btnMatches:
                setFixture();
                Toast.makeText(getApplicationContext(),"Sorted Out",Toast.LENGTH_LONG).show();
                break;
        }
    }
    public void setFixture()
    {
        String time = hour+":"+minute;
        String date = day+"/"+month+"/"+year;
        Map<String,String> match = new HashMap<String,String>();
        match.put("TeamA",TeamA);
        match.put("TeamB",TeamB);
        match.put("Stadium",Stadium);
        match.put("Time",time);
        match.put("Date",date);
        fixture.push().setValue(match);
    }
    public void getTeams()
    {
        mdatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Teams team = dataSnapshot.getValue(Teams.class);
                teams.add(team.Name);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getStadiums()
    {
       stadiumReference.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               Stadium stadium = dataSnapshot.getValue(Stadium.class);
               stadiums.add(stadium.Stadium);
           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }
}
