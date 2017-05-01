package com.cloudflare.soccerapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Wiseman on 2017-05-02.
 */

public class CommentViewAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private List<commentObject> itemList;
    private Context context;
    public CommentViewAdapter(Context context, List<commentObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }
    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, null);
        CommentViewHolder rcv = new CommentViewHolder(layoutView);
        return rcv;
    }
    @Override
    public void onBindViewHolder(final CommentViewHolder holder, int position) {
        holder.time.setText(itemList.get(position).getTime());
        holder.comment.setText(itemList.get(position).getComment());
        Animation upAnim = AnimationUtils.loadAnimation(context,R.anim.alpha);
        upAnim.reset();
        holder.itemView.clearAnimation();
        holder.itemView.setAnimation(upAnim);
        upAnim = AnimationUtils.loadAnimation(context,R.anim.fromtop_translation);
        holder.itemView.clearAnimation();
        holder.itemView.startAnimation(upAnim);

       /* Context popup = new ContextThemeWrapper(context,R.style.popup);
        final PopupMenu optionsMenu = new PopupMenu(popup,options);
        MenuInflater inflater = optionsMenu.getMenuInflater();
        inflater.inflate(R.menu.save_image,optionsMenu.getMenu());
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionsMenu.show();
            }
        });
        optionsMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.save) {

                }
                return false;
            }
        });*/
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
