package com.cloudflare.soccerapp;

/**
 * Created by Wiseman on 2016-04-24.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder{

    public TextView title,likeText,commentText,key;
    public ImageView image,options,like,share,comment;
    public Context context;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title_image);
        image = (ImageView) itemView.findViewById(R.id.image);
        options = (ImageView) itemView.findViewById(R.id.options);
        like= (ImageView)itemView.findViewById(R.id.like);
        share = (ImageView)itemView.findViewById(R.id.share);
        comment = (ImageView)itemView.findViewById(R.id.comments);
        likeText = (TextView) itemView.findViewById(R.id.like_text);
        commentText = (TextView) itemView.findViewById(R.id.comment_text);
        key = (TextView)itemView.findViewById(R.id.key);
    }
}

