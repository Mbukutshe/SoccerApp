package com.cloudflare.soccerapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Wiseman on 2017-04-30.
 */

public class MatchViewAdapter extends RecyclerView.Adapter<MatchViewHolder>{

    private List<MatchObject> itemList;
    private Context context;
    public MatchViewAdapter(Context context, List<MatchObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }
    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_matches, null);
        MatchViewHolder rcv = new MatchViewHolder(layoutView);
        return rcv;
    }
    @Override
    public void onBindViewHolder(final MatchViewHolder holder, int position) {
        holder.time.setText(itemList.get(position).getTime());
        holder.firstTeam.setText(itemList.get(position).getFirstTeam());
        holder.secondTeam.setText(itemList.get(position).getSecondTeam());
        holder.firstBadge.setImageResource(itemList.get(position).getFirstBadge());
        holder.secondBadge.setImageResource(itemList.get(position).getSecondBadge());
        /*holder.countryPhoto.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                holder.selectPic.setVisibility(View.VISIBLE);
                holder.selectPic.setChecked(true);
                return false;
            }
        });*/
    }
    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}

