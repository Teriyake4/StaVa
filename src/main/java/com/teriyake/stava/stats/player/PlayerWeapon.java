package com.teriyake.stava.stats.player;

public class PlayerWeapon extends PlayerBase {
    private double matchesPlayed;
    private double matchesWon;
    private double matchesLost;
    private double matchesTied;
    private double matchesWinPct;
    private double roundsPlayed;
    private double kills;
    private double killsPerRound;
    private double killsPerMatch;
    private double secondaryKills;
    private double headshots;
    private double secondaryKillsPerRound;
    private double secondaryKillsPerMatch;
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
    private double killDistance;
    private double avgKillDistance;
    private double longestKillDistance;

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
    public double getSecondaryKills() {
        return secondaryKills;
    }
    public double getHeadshots() {
        return headshots;
    }
    public double getSecondaryKillsPerRound() {
        return secondaryKillsPerRound;
    }
    public double getSecondaryKillsPerMatch() {
        return secondaryKillsPerMatch;
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