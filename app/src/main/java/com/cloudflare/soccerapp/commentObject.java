package com.cloudflare.soccerapp;

/**
 * Created by Wiseman on 2017-05-02.
 */

public class commentObject {
    private String time;
    private String comment;

    public commentObject(String time, String comment)
    {
        this.setTime(time);
        this.setComment(comment);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
