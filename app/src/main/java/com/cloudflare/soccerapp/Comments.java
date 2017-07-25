package com.cloudflare.soccerapp;

/**
 * Created by Wiseman on 2017-07-24.
 */

public class Comments {
    public String comment;
    public String time;
    public Comments()
    {
        comment = "";
        time="";
    }
    public Comments(String comment,String time)
    {
      this.comment = comment;
      this.time = time;
    }
}
