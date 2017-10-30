package com.cloudflare.soccerapp;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wiseman on 2017-04-27.
 */

public class Matches extends Fragment {
    View myView;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    MatchViewAdapter mAdapter;
    private DatabaseReference reference,dbReference;
    Fixture fixture;
    static Bitmap bitmapTeama,bitmapTeamb;
    List<MatchObject> myDataset;
    List<String> logos;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_matches,container,false);
        // Inflate the layout for this fragment
        Firebase.setAndroidContext(this.getContext());
        reference = FirebaseDatabase.getInstance().getReference().child("Fixture");
        dbReference = FirebaseDatabase.getInstance().getReference().child("Teams");
        mRecyclerView = (RecyclerView) myView.findViewById(R.id.my_recycler_matches);
        mRecyclerView.setHasFixedSize(true);
        myDataset = new ArrayList<MatchObject>();
        logos=new ArrayList<>();
        Configuration config = getResources().getConfiguration();
        if((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL)
        {
            mLayoutManager = new LinearLayoutManager(this.getContext());
        }
        else
        {
            mLayoutManager = new StaggeredGridLayoutManager(2,1);
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MatchViewAdapter(this.getContext(),myDataset);
        mRecyclerView.setAdapter(mAdapter);
        getFixtures();
        return myView;
    }
    public void getFixtures()
    {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                Bitmap logoA,logoB;
                fixture =dataSnapshot.getValue(Fixture.class);
                byte[] decodeImage = Base64.decode( fixture.LogoA, Base64.DEFAULT);
                logoA = BitmapFactory.decodeByteArray(decodeImage,0,decodeImage.length);

                byte[] decryptImage = Base64.decode( fixture.LogoB, Base64.DEFAULT);
                logoB = BitmapFactory.decodeByteArray(decodeImage,0,decryptImage.length);

                myDataset.add(new MatchObject(logoA,logoB,fixture.Time,
                        fixture.Date,fixture.TeamA,fixture.TeamB,fixture.Stadium));
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }
            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

}
