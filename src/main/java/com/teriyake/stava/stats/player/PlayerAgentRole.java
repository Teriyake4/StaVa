package com.teriyake.stava.stats.player;

public class PlayerAgentRole extends PlayerBase {
    private double matchesPlayed;
    private double matchesWon;
    private double matchesLost;
    private double matchesTied;
    private double matchesWinPct;
    private double timePlayed;
    private double scorePerRound;
    private double kills;
    private double deaths;
    private double assists;
    private double kDRatio;
    private double kADRatio;
    private double damageDelta;
    private double damageDeltaPerRound;
    private double damagePerRound;
    private double kAST;

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
    public double getTimePlayed() {
        return timePlayed;
    }
    public double getScorePerRound() {
        return scorePerRound;
    }
    public double getKills() {
        return kills;
    }
    public double getDeaths() {
        return deaths;
    }
    public double getAssists() {
        return assists;
    }
    public double getKDRatio() {
        return kDRatio;
    }
    public double getKADRatio() {
        return kADRatio;
    }
    public double getDamageDelta() {
        return damageDelta;
    }
    public double getDamageDeltaPerRound() {
        return damageDeltaPerRound;
    }
    public double getDamagePerRound() {
        return damagePerRound;
    }
    public double getKAST() {
        return kAST;
    }
}
