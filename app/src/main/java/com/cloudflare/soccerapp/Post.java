package com.cloudflare.soccerapp;

/**
 * Created by Wiseman on 2017-07-03.
 */

public class Post {


    public String Picture;
    public String Title;
    public Post()
    {
        Picture = "";
        Title = "";
    }
    public Post(String Picture,String Title)
    {
        this.Picture = Picture;
        this.Title = Title;
    }
}
