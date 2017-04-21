package com.cloudflare.soccerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LeagueActivity extends AppCompatActivity {

    Button Register2, Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league);
        Register2= (Button)  findViewById(R.id.btnRegister2); //widget(Button), path to widget(findViewById) and R is res for resources
        Cancel= (Button)  findViewById(R.id.btnCancel); //widget(Button), path to widget(findViewById) and R is res for resources
        //onclick listener in button widget
        Register2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeagueActivity.this,ConfirmationActivity.class);
                startActivity(intent); //onclick takes you to RegisterActivity.java
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeagueActivity.this,MainActivity.class);
                startActivity(intent); //onclick takes you to NewsFeedActivity.java
            }
        });
    }
}
