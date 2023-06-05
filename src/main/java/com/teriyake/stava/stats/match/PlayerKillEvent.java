package com.teriyake.stava.stats.match;

public class PlayerKillEvent extends PlayerDamageEvent {
    private int roundTime;
    private int gameTime;
    private String weaponName; // if melee it could be ability
    
    public int getRoundTime() {
        return roundTime;
    }
    public int getGameTime() {
        return gameTime;
    }
    public String getWeaponName() {
        return weaponName;
    }
}
