package com.cloudflare.soccerapp;

/**
 * Created by Wiseman on 2017-08-04.
 */

public class Fixture {
    public String TeamA;
    public String TeamB;
    public String Stadium;
    public String Time;
    public String Date;

    public Fixture()
    {
        TeamA="";
        TeamB="";
        Stadium="";
        Time="";
        Date="";
    }

    public Fixture(String TeamA,String TeamB,String Stadium,String Time,String Date)
    {
        this.TeamA = TeamA;
        this.TeamB = TeamB;
        this.Stadium = Stadium;
        this.Time = Time;
        this.Date = Date;
    }

}
