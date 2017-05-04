package com.cloudflare.soccerapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_pictures,container,false);
        // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) myView.findViewById(R.id.my_recycler_pictures);
        mRecyclerView.setHasFixedSize(true);
        List<ItemObject> myDataset = getAllItemList();
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter(this.getContext(),myDataset);
        mRecyclerView.setAdapter(mAdapter);
        return myView;
    }
    private List<ItemObject> getAllItemList(){

        List<ItemObject> allItems = new ArrayList<ItemObject>();
        allItems.add(new ItemObject(R.drawable.pic,"Motaung....."));
        allItems.add(new ItemObject(R.drawable.laduma,"Soccer Ladumaaaa....."));
        allItems.add(new ItemObject(R.drawable.soccer,"Sundowns..Vs..Chiefs"));
        allItems.add(new ItemObject(R.drawable.league,"Sundowns....."));
        return allItems;
    }

}
