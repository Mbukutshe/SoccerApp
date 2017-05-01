package com.cloudflare.soccerapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        holder.firstBadge.setImageResource(itemList.get(position).getFirstBadge());
        holder.secondBadge.setImageResource(itemList.get(position).getSecondBadge());
        final CardView cardView = (CardView)holder.itemView.findViewById(R.id.card_view);
        final Animation upAnim = AnimationUtils.loadAnimation(context,R.anim.fromtop_translation);
        upAnim.reset();
        cardView.startAnimation(upAnim);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardView.setMinimumHeight(300);
                upAnim.reset();
                holder.time.startAnimation(upAnim);
                upAnim.reset();
                holder.ground.startAnimation(upAnim);
                upAnim.reset();
                holder.date.startAnimation(upAnim);
                holder.details.setMinimumHeight(150);
                holder.date.setText("Date : 01/05/207");
                holder.time.setText("Time: 07:42pm");
                holder.ground.setText("Stadium : Moses Mabhida Stadium");
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                holder.details.startAnimation(upAnim);
                upAnim.reset();
                cardView.setMinimumHeight(0);
                holder.details.setMinimumHeight(0);
                holder.date.setText("");
                holder.time.setText("");
                holder.ground.setText("");
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}

