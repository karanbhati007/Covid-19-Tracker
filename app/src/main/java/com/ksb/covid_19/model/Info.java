package com.ksb.covid_19.model;

public class Info {
    String stateName;
    int tcci;  // Total Confirmed cases (Indian National)
   // int tccf;  // Total Confirmed cases ( Foreign National )
    int cured; // Cured
    int death; // Death

    public Info(String stateName, int tcci, int cured, int death) {
        this.stateName = stateName;
        this.tcci = tcci;
      //  this.tccf = tccf;
        this.cured = cured;
        this.death = death;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getTcci() {
        return tcci;
    }

    public void setTcci(int tcci) {
        this.tcci = tcci;
    }


    public int getCured() {
        return cured;
    }

    public void setCured(int cured) {
        this.cured = cured;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }
}
