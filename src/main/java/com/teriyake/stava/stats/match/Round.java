package com.teriyake.stava.stats.match;

import java.util.ArrayList;

public class Round {
    private int roundNum;
    private String roundResult;
    private String winningTeam;
    private String losingTeam;

    private ArrayList<DamageEvent> damageEvents;
    private ArrayList<KillEvent> killEvents;
    private ArrayList<PlayerRoundSummary> playerRoundSummaries;
    
}
