package com.teriyake.stava.stats;

public class Metadata {
    private String type; // player, season, map, etc
    private String region; // ie: na
    private String date;
    private String season; // ie: 6:1 = EPISODE 6: ACT 1 or 0:0 for all
    private String seasonID; // can also be act ie: 9c91a445-4f78-1baa-a3ea-8f8aadf4914d
    private String gameMode; // ie: competitive
    
    public String getType() {
        return type; 
    }

    public String getRegion() {
        return region;
    }

    public String getDate() {
        return date;
    }

    public String getSeason() {
        return season;
    }

    public String getSeasonID() {
        return seasonID;
    }

    public String getGameMode() {
        return gameMode;
    }
}