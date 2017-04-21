package com.cloudflare.soccerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.start;

public class ConfirmationActivity extends AppCompatActivity {
    Button Start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        Start=(Button) findViewById(R.id.btnStart);

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }}