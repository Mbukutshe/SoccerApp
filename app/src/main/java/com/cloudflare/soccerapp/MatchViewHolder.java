package com.cloudflare.soccerapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Wiseman on 2017-04-30.
 */

public class MatchViewHolder extends RecyclerView.ViewHolder{

    public ImageView firstBadge,secondBadge;
    public TextView time;
    public TextView firstTeam;
    public TextView secondTeam;
    public TextView date;
    public TextView ground;
    public Context context;

    public MatchViewHolder(View itemView) {
        super(itemView);
        firstBadge = (ImageView) itemView.findViewById(R.id.first_badge);
        secondBadge = (ImageView) itemView.findViewById(R.id.second_badge);
        time = (TextView) itemView.findViewById(R.id.time_match);
        firstTeam = (TextView) itemView.findViewById(R.id.firstteam);
        secondTeam = (TextView) itemView.findViewById(R.id.secondteam);
        ground = (TextView) itemView.findViewById(R.id.match_ground);
        date = (TextView) itemView.findViewById(R.id.date_match);

    }
}
