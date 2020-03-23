package com.ksb.covid_19.model;

public class WorldInfo {

    String contryName; // 0
    String tc;  // Total cases  // 1
    String active; // Active Cases //
    String cured; // Total Cured/Recovered
    String death; // Total Death
   // int Serious; // Serious/Critical

    public WorldInfo(String contryName, String tc, String active, String cured, String death) {
        this.contryName = contryName;
        this.tc = tc;
        this.active = active;
        this.cured = cured;
        this.death = death;
    }

    public String getContryName() {
        return contryName;
    }

    public void setContryName(String contryName) {
        this.contryName = contryName;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCured() {
        return cured;
    }

    public void setCured(String cured) {
        this.cured = cured;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }
}
