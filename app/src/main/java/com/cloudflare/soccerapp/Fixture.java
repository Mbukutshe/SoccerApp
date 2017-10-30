package com.cloudflare.soccerapp;

/**
 * Created by Wiseman on 2017-08-04.
 */

public class Fixture {
    public String TeamA;
    public String TeamB;
    public String LogoA;
    public String LogoB;
    public String Stadium;
    public String Time;
    public String Date;


    public Fixture()
    {
        TeamA="";
        TeamB="";
        LogoA="";
        LogoB="";
        Stadium="";
        Time="";
        Date="";
    }

    public Fixture(String TeamA,String TeamB,String LogoA,String LogoB,String Stadium,String Time,String Date)
    {
        this.TeamA = TeamA;
        this.TeamB = TeamB;
        this.LogoA = LogoA;
        this.LogoB = LogoB;
        this.Stadium = Stadium;
        this.Time = Time;
        this.Date = Date;
    }

}
