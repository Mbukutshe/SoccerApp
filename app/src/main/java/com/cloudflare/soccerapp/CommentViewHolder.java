package com.cloudflare.soccerapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Wiseman on 2017-05-02.
 */

public class CommentViewHolder extends RecyclerView.ViewHolder{
    public TextView time,comment;
    public Context context;
    public CommentViewHolder(View itemView) {
        super(itemView);
        time= (TextView) itemView.findViewById(R.id.time);
        comment= (TextView) itemView.findViewById(R.id.comment);
    }
}
