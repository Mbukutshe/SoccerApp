package com.cloudflare.soccerapp;

/**
 * Created by Wiseman on 2016-04-24.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{

    private List<ItemObject> itemList;
    private Context context,c;
    ProgressBar progressBar;
    public RecyclerViewAdapter(Context context, List<ItemObject> itemList,ProgressBar progressBar) {
        this.itemList = itemList;
        this.context = context;
        this.progressBar = progressBar;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view,null);
        RecyclerViewHolder rcv = new RecyclerViewHolder(layoutView);
        c=parent.getContext();
        return rcv;
    }
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.title.setText(itemList.get(position).getTitle());
        holder.image.setImageBitmap(itemList.get(position).getImage());
        holder.key.setText(itemList.get(position).getKey());
        Animation upAnim = AnimationUtils.loadAnimation(context,R.anim.alpha);
        holder.itemView.setAnimation(upAnim);

        final Animation anim = AnimationUtils.loadAnimation(context,R.anim.zoom_out);

        ImageView zoomImage = (ImageView)holder.itemView.findViewById(R.id.image);
        Animation zoomAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom);
        zoomImage.startAnimation(upAnim);
        upAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                progressBar.startAnimation(anim);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        final ImageView options = (ImageView)holder.itemView.findViewById(R.id.options);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation rotate = AnimationUtils.loadAnimation(context,R.anim.alpha);
                options.startAnimation(rotate);
                Bitmap image = itemList.get(position).getImage();
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), image, itemList.get(position).getTitle().toString(), null);

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(path) );
                shareIntent.setType("image/jpeg");
                context.startActivity(Intent.createChooser(shareIntent, "Share Via:"));
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
