package com.cloudflare.soccerapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_matches,container,false);
        // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) myView.findViewById(R.id.my_recycler_matches);
        mRecyclerView.setHasFixedSize(true);
        List<MatchObject> myDataset = getAllItemList();
        Configuration config = getResources().getConfiguration();
        if((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_NORMAL)
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


        return myView;
    }
    public void getFixtures()
    {

    }
    private List<MatchObject> getAllItemList(){

        List<MatchObject> allItems = new ArrayList<MatchObject>();
        allItems.add(new MatchObject(R.drawable.chiefs,R.drawable.pirates,"","Kaizer Chiefs","Orlando Pirates"));
        allItems.add(new MatchObject(R.drawable.amazulu,R.drawable.mtata,"","Amazulu","Mtata Bucks"));
        allItems.add(new MatchObject(R.drawable.ajax,R.drawable.platnum,"","Ajax CT","Platnum Stars"));
        allItems.add(new MatchObject(R.drawable.sundowns,R.drawable.supersport,"","Sundowns","Supersport"));
        allItems.add(new MatchObject(R.drawable.baroka,R.drawable.celtic,"","Baroka FC","Celtic"));
        allItems.add(new MatchObject(R.drawable.chippa,R.drawable.maritzburg,"","Chippa United","Maritzburg"));
        allItems.add(new MatchObject(R.drawable.swallows,R.drawable.wits,"","Swallows","Wits"));
        return allItems;
    }
}
