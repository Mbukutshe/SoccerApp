package com.cloudflare.soccerapp;

/**
 * Created by Wiseman on 2016-10-02.
 */

public class ItemObject {

    private int image;
    private String title;

    public ItemObject(int image, String title)
    {
        this.image=image;
        this.title = title;
    }

    public int getImage()
    {
        return image;
    }
    public void setImage(int image)
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


}