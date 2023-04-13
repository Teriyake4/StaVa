package com.teriyake.stava.stats.player;

public class PlayerWeapon extends PlayerBase {
    private double secondaryKills; //
    private double secondaryKillsPerRound; //
    private double secondaryKillsPerMatch; //
    private double killDistance; //
    private double avgKillDistance; //
    private double longestKillDistance; //


    public double getSecondaryKills() {
        return secondaryKills;
    }
    public double getSecondaryKillsPerRound() {
        return secondaryKillsPerRound;
    }
    public double getSecondaryKillsPerMatch() {
        return secondaryKillsPerMatch;
    }
    public double getKillDistance() {
        return killDistance;
    }
    public double getAvgKillDistance() {
        return avgKillDistance;
    }
    public double getLongestKillDistance() {
        return longestKillDistance;
    }
}