package com.cloudflare.soccerapp;

/**
 * Created by Wiseman on 2017-04-30.
 */

public class MatchObject {
    private int firstBadge;
    private int secondBadge;
    private String time;
    private String firstTeam;
    private String secondTeam;

    public MatchObject(int firstBadge,int secondBadge,String time, String firstTeam,String secondTeam)
    {
        this.setFirstBadge(firstBadge);
        this.setSecondBadge(secondBadge);
        this.setTime(time);
        this.setFirstTeam(firstTeam);
        this.setSecondTeam(secondTeam);

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

    public int getFirstBadge() {
        return firstBadge;
    }

    public void setFirstBadge(int firstBadge) {
        this.firstBadge = firstBadge;
    }

    public int getSecondBadge() {
        return secondBadge;
    }

    public void setSecondBadge(int secondBadge) {
        this.secondBadge = secondBadge;
    }
}
