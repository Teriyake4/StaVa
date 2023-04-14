package com.teriyake.stava.stats.player;

public class PlayerAgent extends PlayerFull {
    private double ability1Kills;
    private double ability1KillsPerMatch;
    private double ability2Kills;
    private double ability2KillsPerMatch;
    private double grenadeKills;
    private double grenadeKillsPerMatch;
    private double primaryKills;
    private double primaryKillsPerMatch;
    private double ultimateKills;
    private double ultimateKillsPerMatch;


    public double getAbility1Kills() {
      return ability1Kills;
    }
    public double getAbility1KillsPerMatch() {
      return ability1KillsPerMatch;
    }
  
    public double getAbility2Kills() {
      return ability2Kills;
    }
    public double getAbility2KillsPerMatch() {
      return ability2KillsPerMatch;
    }
    public double getGrenadeKills() {
      return grenadeKills;
    }
    public double getGrenadeKillsPerMatch() {
      return grenadeKillsPerMatch;
    }
    public double getPrimaryKills() {
      return primaryKills;
    }
    public double getPrimaryKillsPerMatch() {
      return primaryKillsPerMatch;
    }
    public double getUltimateKills() {
      return ultimateKills;
    }
    public double getUltimateKillsPerMatch() {
      return ultimateKillsPerMatch;
    }
}