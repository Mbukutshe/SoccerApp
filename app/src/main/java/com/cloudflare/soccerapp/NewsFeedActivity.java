package com.cloudflare.soccerapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class NewsFeedActivity extends AppCompatActivity {

    private ListView listView;

    ArrayList list = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        list.add("21 4 2013");
        list.add("21 4 2013");

        listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter arrayAdapter  = new ArrayAdapter(this,R.layout.mylist,list);
        listView.setAdapter(arrayAdapter);
    }
}
