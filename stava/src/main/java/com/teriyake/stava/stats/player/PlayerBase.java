package com.teriyake.stava.stats.player;

import com.teriyake.stava.stats.Metadata;

public class PlayerBase {
    private Metadata info;
    private double matchesPlayed;
    private double matchesWon;
    private double matchesLost;
    private double matchesTied;
    private double matchesWinPct;
    private double roundsPlayed;
    private double kills;
    private double killsPerRound;
    private double killsPerMatch;
    private double headshots;
    private double deaths;
    private double deathsPerRound;
    private double deathsPerMatch;
    private double kDRatio;
    private double headshotsPercentage;
    private double damage;
    private double damagePerRound;
    private double damagePerMatch;
    private double damageReceived;
    private double dealtHeadshots;
    private double dealtBodyshots;
    private double dealtLegshots;


    public Metadata info() {
        return info;
    }
    public double getMatchesPlayed() {
        return matchesPlayed;
    }
    public double getMatchesWon() {
        return matchesWon;
    }
    public double getMatchesLost() {
        return matchesLost;
    }
    public double getMatchesTied() {
        return matchesTied;
    }
    public double getMatchesWinPct() {
        return matchesWinPct;
    }
    public double getRoundsPlayed() {
        return roundsPlayed;
    }
    public double getKills() {
        return kills;
    }
    public double getKillsPerRound() {
        return killsPerRound;
    }
    public double getKillsPerMatch() {
        return killsPerMatch;
    }
    public double getHeadshots() {
        return headshots;
    }
    public double getDeaths() {
        return deaths;
    }
    public double getDeathsPerRound() {
        return deathsPerRound;
    }
    public double getDeathsPerMatch() {
        return deathsPerMatch;
    }
    public double getKDRatio() {
        return kDRatio;
    }
    public double getHeadshotsPercentage() {
        return headshotsPercentage;
    }
    public double getDamage() {
        return damage;
    }
    public double getDamagePerRound() {
        return damagePerRound;
    }
    public double getDamagePerMatch() {
        return damagePerMatch;
    }
    public double getDamageReceived() {
        return damageReceived;
    }
    public double getDealtHeadshots() {
        return dealtHeadshots;
    }
    public double getDealtBodyshots() {
        return dealtBodyshots;
    }
    public double getDealtLegshots() {
        return dealtLegshots;
    }
}
