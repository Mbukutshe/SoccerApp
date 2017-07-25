package com.cloudflare.soccerapp;

import android.graphics.Bitmap;

/**
 * Created by Wiseman on 2016-10-02.
 */

public class ItemObject {

    private Bitmap image;
    private String title;
    private String key;
    public ItemObject(Bitmap image, String title,String key)
    {
        this.image=image;
        this.title = title;
        this.setKey(key);
    }

    public Bitmap getImage()
    {
        return image;
    }
    public void setImage(Bitmap image)
    {
        this.image = image;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}