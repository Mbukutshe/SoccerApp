package com.cloudflare.soccerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    Button Register, NewsFeed; //Declaring and importing
    ImageButton postImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialise buttons
      Register= (Button)  findViewById(R.id.btnRegister); //widget(Button), path to widget(findViewById) and R is res for resources
      NewsFeed= (Button)  findViewById(R.id.btnNewsFeed);//widget(Button), path to widget(findViewById) and R is res for resources
       postImage = (ImageButton)findViewById(R.id.imageButton3);

        //onclick listener in button widget
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent); //onclick takes you to RegisterActivity.java
            }
        });
        NewsFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewsFeedActivity.class);
                startActivity(intent); //onclick takes you to NewsFeedActivity.java
            }
        });

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PostsActivity.class);
                startActivity(intent); //onclick takes you to postsActivity.java


            }
        });
    }
}
