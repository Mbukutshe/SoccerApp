package com.cloudflare.soccerapp;

import android.graphics.Bitmap;

/**
 * Created by Wiseman on 2017-04-30.
 */

public class MatchObject {
    private Bitmap firstBadge;
    private Bitmap secondBadge;
    private String time;
    private String ground;
    private String firstTeam;
    private String secondTeam;
    private String date;

    public MatchObject(Bitmap firstBadge,Bitmap secondBadge,String time,String date,String firstTeam,String secondTeam,String ground)
    {
        this.setFirstBadge(firstBadge);
        this.setSecondBadge(secondBadge);
        this.setTime(time);
        this.setDate(date);
        this.setFirstTeam(firstTeam);
        this.setSecondTeam(secondTeam);
        this.setGround(ground);
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getGround() {
        return ground;
    }

    public void setGround(String ground) {
        this.ground = ground;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(String firstTeam) {
        this.firstTeam = firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(String secondTeam) {
        this.secondTeam = secondTeam;
    }

    public Bitmap getFirstBadge() {
        return firstBadge;
    }

    public void setFirstBadge(Bitmap firstBadge) {
        this.firstBadge = firstBadge;
    }

    public Bitmap getSecondBadge() {
        return secondBadge;
    }

    public void setSecondBadge(Bitmap secondBadge) {
        this.secondBadge = secondBadge;
    }
}
