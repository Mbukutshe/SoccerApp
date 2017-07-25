package com.cloudflare.soccerapp;

/**
 * Created by Wiseman on 2016-04-24.
 */
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private List<ItemObject> itemList;
    private Context context;
    public RecyclerViewAdapter(Context context, List<ItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,null);
        RecyclerViewHolder rcv = new RecyclerViewHolder(layoutView);
        return rcv;
    }
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.title.setText(itemList.get(position).getTitle());
        holder.image.setImageBitmap(itemList.get(position).getImage());
        holder.key.setText(itemList.get(position).getKey());
        Animation upAnim = AnimationUtils.loadAnimation(context,R.anim.alpha);
        upAnim.reset();
        holder.itemView.clearAnimation();
        holder.itemView.setAnimation(upAnim);
        upAnim = AnimationUtils.loadAnimation(context,R.anim.fromtop_translation);
        holder.itemView.clearAnimation();
        holder.itemView.startAnimation(upAnim);

        ImageView zoomImage = (ImageView)holder.itemView.findViewById(R.id.image);
        Animation zoomAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom);
        zoomImage.startAnimation(zoomAnimation);

        final ImageView options = (ImageView)holder.itemView.findViewById(R.id.options);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation rotate = AnimationUtils.loadAnimation(context,R.anim.alpha);
                options.startAnimation(rotate);
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, "");
                shareIntent.setType("image/jpeg");
                context.startActivity(Intent.createChooser(shareIntent, ""));
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation rotate = AnimationUtils.loadAnimation(context,R.anim.alpha);
                holder.like.startAnimation(rotate);
                int like = Integer.parseInt(holder.likeText.getText().toString())+1;
                holder.likeText.setText(""+like);
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation rotate = AnimationUtils.loadAnimation(context,R.anim.alpha);
                holder.share.startAnimation(rotate);
                int comment = Integer.parseInt(holder.commentText.getText().toString())+1;
                holder.commentText.setText(""+comment);
                Intent intent = new Intent(context,LoginActivity.class);
                intent.putExtra("Image",itemList.get(position).getImage());
                intent.putExtra("Title",itemList.get(position).getTitle());
                intent.putExtra("key",holder.key.getText().toString());
                context.startActivity(intent);
            }
        });

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
