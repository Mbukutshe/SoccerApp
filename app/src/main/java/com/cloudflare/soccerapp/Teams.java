package com.cloudflare.soccerapp;

/**
 * Created by Wiseman on 2017-08-04.
 */

public class Teams {
    public String Name;
    public String Captain;
    public String Chairman;
    public String League;
    public String Logo;
    public Teams()
    {
        Name="";
        Captain="";
        Chairman="";
        League="";
        Logo="";
    }
    public Teams(String Name,String Captain,String Chairman,String League,String Logo)
    {
        this.Name = Name;
        this.Captain = Captain;
        this.Chairman = Chairman;
        this.League = League;
        this.Logo = Logo;
    }
}
