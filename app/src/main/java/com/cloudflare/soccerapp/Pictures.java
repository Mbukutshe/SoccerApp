package com.cloudflare.soccerapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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

public class Pictures extends Fragment {
    View myView;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerViewAdapter mAdapter;
    List<ItemObject> allItems;
    Post post;
    Firebase fire;
    private DatabaseReference mdatabaseRef,reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_pictures,container,false);
        // Inflate the layout for this fragment
        Firebase.setAndroidContext(this.getContext());

        //Newer version of Firebase
        allItems= new ArrayList<ItemObject>();
        mRecyclerView = (RecyclerView) myView.findViewById(R.id.my_recycler_pictures);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this.getContext());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mdatabaseRef = FirebaseDatabase.getInstance().getReference().child("Post");
        ProgressBar progressBar = (ProgressBar) getActivity().findViewById(R.id.progressToolBar);
        mAdapter = new RecyclerViewAdapter(getContext(),allItems,progressBar);
        mRecyclerView.setAdapter(mAdapter);
        getData();
        return myView;
    }
    public  void getData()
    {
        mdatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Bitmap bitmap;
                post =dataSnapshot.getValue(Post.class);
                byte[] decodeImage = Base64.decode( post.Picture, Base64.DEFAULT);
                bitmap= BitmapFactory.decodeByteArray(decodeImage,0,decodeImage.length);
/*
                float scaleWidth = ((float) 0.5) ;
                float scaleHeight = ((float) 0.5);

                Display display = getActivity().getWindowManager().getDefaultDisplay();
                DisplayMetrics outMetrics = new DisplayMetrics();
                display.getMetrics(outMetrics);
                float pxWidth = outMetrics.widthPixels;
                float screenWidth= pxWidth;
                float newHeight = screenWidth;
                if (bitmap.getWidth() != 0 && bitmap.getHeight() != 0) {
                    newHeight = (screenWidth * bitmap.getHeight()) / bitmap.getWidth();
                }
                // create a matrix for the manipulation
                Matrix matrix = new Matrix();

                // resize the bit map
                matrix.postScale(scaleWidth, scaleHeight);

                // recreate the new Bitmap
                bitmap = Bitmap.createScaledBitmap(bitmap,(int)screenWidth, (int)newHeight,true);*/

                String key = dataSnapshot.getKey();
                allItems.add(new ItemObject(bitmap,post.Title,key));
                mAdapter.notifyDataSetChanged();
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
