package com.cloudflare.soccerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TeamMembersActivity extends AppCompatActivity {
    Button Yes, No;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_members2);

        Yes = (Button) findViewById(R.id.btnYes);
        No = (Button) findViewById(R.id.btnNo);

        Yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamMembersActivity.this, LeagueActivity.class);
                startActivity(intent);
            }
        });
        No.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamMembersActivity.this, ConfirmationActivity.class);
                startActivity(intent);
            }
        });
    }
}