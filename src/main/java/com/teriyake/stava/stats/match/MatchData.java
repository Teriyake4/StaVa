package com.teriyake.stava.stats.match;

import com.teriyake.stava.stats.Metadata;

public class MatchData extends Metadata {
    private String map;
    private double matchDuration;
    private double totalDefRounds;
    private double totalAttRounds;

    public String getMap() {
        return map;
    }

    public double getMatchDuration() {
        return matchDuration;
    }

    public double getTotalDefRounds() {
        return totalDefRounds;
    }

    public double getTotalAttRounds() {
        return totalAttRounds;
    }
}
