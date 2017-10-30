package com.cloudflare.soccerapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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
    public void onBindViewHolder(final MatchViewHolder holder, final int position) {
        holder.firstTeam.setText(itemList.get(position).getFirstTeam());
        holder.secondTeam.setText(itemList.get(position).getSecondTeam());
        holder.firstBadge.setImageBitmap(itemList.get(position).getFirstBadge());
        holder.secondBadge.setImageBitmap(itemList.get(position).getSecondBadge());
        holder.ground.setText(context.getResources().getString(R.string.Stadium)+itemList.get(position).getGround());
        holder.time.setText(context.getResources().getString(R.string.Time)+itemList.get(position).getTime());
        holder.date.setText(context.getResources().getString(R.string.Date)+itemList.get(position).getDate());
        final CardView cardView = (CardView)holder.itemView.findViewById(R.id.card_view);
        final Animation upAnim = AnimationUtils.loadAnimation(context,R.anim.fromtop_translation);
        final Animation Anim = AnimationUtils.loadAnimation(context,R.anim.alpha);
        upAnim.reset();
        cardView.startAnimation(Anim);
        holder.itemView.startAnimation(Anim);
    }
    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}

