package com.teriyake.stava.stats.match;

public class PlayerDamageEvent {
    private String opponent;
    private String player;
    private int damage;
    private int numHeadShots;
    private int numBodyShots;
    private int numLegShots;

    public String getOpponent() {
        return opponent;
    }
    public String getPlayer() {
        return player;
    }
    public int getDamage() {
        return damage;
    }
    public int getNumHeadShots() {
        return numHeadShots;
    }
    public int getNumBodyShots() {
        return numBodyShots;
    }
    public int getNumLegShots() {
        return numLegShots;
    }
}
