package com.teriyake.stava.stats.player;

public class PlayerAgent extends PlayerBase {
    private double matchesPlayed;
    private double matchesWon;
    private double matchesLost;
    private double matchesTied;
    private double matchesWinPct;
    private double matchesDuration;
    private double timePlayed;
    private double roundsPlayed;
    private double roundsWon;
    private double roundsLost;
    private double roundsWinPct;
    private double roundsDuration;
    private double score;
    private double scorePerMatch;
    private double scorePerRound;
    private double kills;
    private double killsPerRound;
    private double killsPerMatch;
    private double deaths;
    private double deathsPerRound;
    private double deathsPerMatch;
    private double assists;
    private double assistsPerRound;
    private double assistsPerMatch;
    private double kDRatio;
    private double kDARatio;
    private double kADRatio;
    private double damage;
    private double damageDelta;
    private double damageDeltaPerRound;
    private double damagePerRound;
    private double damagePerMatch;
    private double damagePerMinute;
    private double damageReceived;
    private double headshots;
    private double headshotsPerRound;
    private double headshotsPercentage;
    private double grenadeCasts;
    private double grenadeCastsPerRound;
    private double grenadeCastsPerMatch;
    private double ability1Casts;
    private double ability1CastsPerRound;
    private double ability1CastsPerMatch;
    private double ability2Casts;
    private double ability2CastsPerRound;
    private double ability2CastsPerMatch;
    private double ultimateCasts;
    private double ultimateCastsPerRound;
    private double ultimateCastsPerMatch;
    private double dealtHeadshots;
    private double dealtBodyshots;
    private double dealtLegshots;
    private double receivedHeadshots;
    private double receivedBodyshots;
    private double receivedLegshots;
    private double econRating;
    private double econRatingPerMatch;
    private double econRatingPerRound;
    private double suicides;
    private double firstBloods;
    private double firstBloodsPerRound;
    private double firstBloodsPerMatch;
    private double firstDeaths;
    private double firstDeathsPerRound;
    private double lastDeaths;
    private double survived;
    private double traded;
    private double kAST;
    private double mostKillsInMatch;
    private double flawless;
    private double thrifty;
    private double aces;
    private double teamAces;
    private double clutches;
    private double clutchesPercentage;
    private double clutchesLost;
    private double clutches1v1;
    private double clutches1v2;
    private double clutches1v3;
    private double clutches1v4;
    private double clutches1v5;
    private double clutchesLost1v1;
    private double clutchesLost1v2;
    private double clutchesLost1v3;
    private double clutchesLost1v4;
    private double clutchesLost1v5;
    private double kills1K;
    private double kills2K;
    private double kills3K;
    private double kills4K;
    private double kills5K;
    private double kills6K;
    private double plants;
    private double plantsPerMatch;
    private double plantsPerRound;
    private double attackKills;
    private double attackKillsPerRound;
    private double attackDeaths;
    private double attackKDRatio;
    private double attackAssists;
    private double attackAssistsPerRound;
    private double attackRoundsWon;
    private double attackRoundsLost;
    private double attackRoundsPlayed;
    private double attackRoundsWinPct;
    private double attackScore;
    private double attackScorePerRound;
    private double attackDamage;
    private double attackDamagePerRound;
    private double attackHeadshots;
    private double attackTraded;
    private double attackSurvived;
    private double attackFirstBloods;
    private double attackFirstBloodsPerRound;
    private double attackFirstDeaths;
    private double attackFirstDeathsPerRound;
    private double attackKAST;
    private double defuses;
    private double defusesPerMatch;
    private double defusesPerRound;
    private double defenseKills;
    private double defenseKillsPerRound;
    private double defenseDeaths;
    private double defenseKDRatio;
    private double defenseAssists;
    private double defenseAssistsPerRound;
    private double defenseRoundsWon;
    private double defenseRoundsLost;
    private double defenseRoundsPlayed;
    private double defenseRoundsWinPct;
    private double defenseScore;
    private double defenseScorePerRound;
    private double defenseDamage;
    private double defenseDamagePerRound;
    private double defenseHeadshots;
    private double defenseTraded;
    private double defenseSurvived;
    private double defenseFirstBloods;
    private double defenseFirstBloodsPerRound;
    private double defenseFirstDeaths;
    private double defenseFirstDeathsPerRound;
    private double defenseKAST;
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
    public double getMatchesDuration() {
        return matchesDuration;
    }
    public double getTimePlayed() {
        return timePlayed;
    }
    public double getRoundsPlayed() {
        return roundsPlayed;
    }
    public double getRoundsWon() {
        return roundsWon;
    }
    public double getRoundsLost() {
        return roundsLost;
    }
    public double getRoundsWinPct() {
        return roundsWinPct;
    }
    public double getRoundsDuration() {
        return roundsDuration;
    }
    public double getScore() {
        return score;
    }
    public double getScorePerMatch() {
        return scorePerMatch;
    }
    public double getScorePerRound() {
        return scorePerRound;
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
    public double getDeaths() {
        return deaths;
    }
    public double getDeathsPerRound() {
        return deathsPerRound;
    }
    public double getDeathsPerMatch() {
        return deathsPerMatch;
    }
    public double getAssists() {
        return assists;
    }
    public double getAssistsPerRound() {
        return assistsPerRound;
    }
    public double getAssistsPerMatch() {
        return assistsPerMatch;
    }
    public double getKDRatio() {
        return kDRatio;
    }
    public double getKDARatio() {
        return kDARatio;
    }
    public double getKADRatio() {
        return kADRatio;
    }
    public double getDamage() {
        return damage;
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
    public double getDamagePerMatch() {
        return damagePerMatch;
    }
    public double getDamagePerMinute() {
        return damagePerMinute;
    }
    public double getDamageReceived() {
        return damageReceived;
    }
    public double getHeadshots() {
        return headshots;
    }
    public double getHeadshotsPerRound() {
        return headshotsPerRound;
    }
    public double getHeadshotsPercentage() {
        return headshotsPercentage;
    }
    public double getGrenadeCasts() {
        return grenadeCasts;
    }
    public double getGrenadeCastsPerRound() {
        return grenadeCastsPerRound;
    }
    public double getGrenadeCastsPerMatch() {
        return grenadeCastsPerMatch;
    }
    public double getAbility1Casts() {
        return ability1Casts;
    }
    public double getAbility1CastsPerRound() {
        return ability1CastsPerRound;
    }
    public double getAbility1CastsPerMatch() {
        return ability1CastsPerMatch;
    }
    public double getAbility2Casts() {
        return ability2Casts;
    }
    public double getAbility2CastsPerRound() {
        return ability2CastsPerRound;
    }
    public double getAbility2CastsPerMatch() {
        return ability2CastsPerMatch;
    }
    public double getUltimateCasts() {
        return ultimateCasts;
    }
    public double getUltimateCastsPerRound() {
        return ultimateCastsPerRound;
    }
    public double getUltimateCastsPerMatch() {
        return ultimateCastsPerMatch;
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
    public double getReceivedHeadshots() {
        return receivedHeadshots;
    }
    public double getReceivedBodyshots() {
        return receivedBodyshots;
    }
    public double getReceivedLegshots() {
        return receivedLegshots;
    }
    public double getEconRating() {
        return econRating;
    }
    public double getEconRatingPerMatch() {
        return econRatingPerMatch;
    }
    public double getEconRatingPerRound() {
        return econRatingPerRound;
    }
    public double getSuicides() {
        return suicides;
    }
    public double getFirstBloods() {
        return firstBloods;
    }
    public double getFirstBloodsPerRound() {
        return firstBloodsPerRound;
    }
    public double getFirstBloodsPerMatch() {
        return firstBloodsPerMatch;
    }
    public double getFirstDeaths() {
        return firstDeaths;
    }
    public double getFirstDeathsPerRound() {
        return firstDeathsPerRound;
    }
    public double getLastDeaths() {
        return lastDeaths;
    }
    public double getSurvived() {
        return survived;
    }
    public double getTraded() {
        return traded;
    }
    public double getKAST() {
        return kAST;
    }
    public double getMostKillsInMatch() {
        return mostKillsInMatch;
    }
    public double getFlawless() {
        return flawless;
    }
    public double getThrifty() {
        return thrifty;
    }
    public double getAces() {
        return aces;
    }
    public double getTeamAces() {
        return teamAces;
    }
    public double getClutches() {
        return clutches;
    }
    public double getClutchesPercentage() {
        return clutchesPercentage;
    }
    public double getClutchesLost() {
        return clutchesLost;
    }
    public double getClutches1v1() {
        return clutches1v1;
    }
    public double getClutches1v2() {
        return clutches1v2;
    }
    public double getClutches1v3() {
        return clutches1v3;
    }
    public double getClutches1v4() {
        return clutches1v4;
    }
    public double getClutches1v5() {
        return clutches1v5;
    }
    public double getClutchesLost1v1() {
        return clutchesLost1v1;
    }
    public double getClutchesLost1v2() {
        return clutchesLost1v2;
    }
    public double getClutchesLost1v3() {
        return clutchesLost1v3;
    }
    public double getClutchesLost1v4() {
        return clutchesLost1v4;
    }
    public double getClutchesLost1v5() {
        return clutchesLost1v5;
    }
    public double getKills1K() {
        return kills1K;
    }
    public double getKills2K() {
        return kills2K;
    }
    public double getKills3K() {
        return kills3K;
    }
    public double getKills4K() {
        return kills4K;
    }
    public double getKills5K() {
        return kills5K;
    }
    public double getKills6K() {
        return kills6K;
    }
    public double getPlants() {
        return plants;
    }
    public double getPlantsPerMatch() {
        return plantsPerMatch;
    }
    public double getPlantsPerRound() {
        return plantsPerRound;
    }
    public double getAttackKills() {
        return attackKills;
    }
    public double getAttackKillsPerRound() {
        return attackKillsPerRound;
    }
    public double getAttackDeaths() {
        return attackDeaths;
    }
    public double getAttackKDRatio() {
        return attackKDRatio;
    }
    public double getAttackAssists() {
        return attackAssists;
    }
    public double getAttackAssistsPerRound() {
        return attackAssistsPerRound;
    }
    public double getAttackRoundsWon() {
        return attackRoundsWon;
    }
    public double getAttackRoundsLost() {
        return attackRoundsLost;
    }
    public double getAttackRoundsPlayed() {
        return attackRoundsPlayed;
    }
    public double getAttackRoundsWinPct() {
        return attackRoundsWinPct;
    }
    public double getAttackScore() {
        return attackScore;
    }
    public double getAttackScorePerRound() {
        return attackScorePerRound;
    }
    public double getAttackDamage() {
        return attackDamage;
    }
    public double getAttackDamagePerRound() {
        return attackDamagePerRound;
    }
    public double getAttackHeadshots() {
        return attackHeadshots;
    }
    public double getAttackTraded() {
        return attackTraded;
    }
    public double getAttackSurvived() {
        return attackSurvived;
    }
    public double getAttackFirstBloods() {
        return attackFirstBloods;
    }
    public double getAttackFirstBloodsPerRound() {
        return attackFirstBloodsPerRound;
    }
    public double getAttackFirstDeaths() {
        return attackFirstDeaths;
    }
    public double getAttackFirstDeathsPerRound() {
        return attackFirstDeathsPerRound;
    }
    public double getAttackKAST() {
        return attackKAST;
    }
    public double getDefuses() {
        return defuses;
    }
    public double getDefusesPerMatch() {
        return defusesPerMatch;
    }
    public double getDefusesPerRound() {
        return defusesPerRound;
    }
    public double getDefenseKills() {
        return defenseKills;
    }
    public double getDefenseKillsPerRound() {
        return defenseKillsPerRound;
    }
    public double getDefenseDeaths() {
        return defenseDeaths;
    }
    public double getDefenseKDRatio() {
        return defenseKDRatio;
    }
    public double getDefenseAssists() {
        return defenseAssists;
    }
    public double getDefenseAssistsPerRound() {
        return defenseAssistsPerRound;
    }
    public double getDefenseRoundsWon() {
        return defenseRoundsWon;
    }
    public double getDefenseRoundsLost() {
        return defenseRoundsLost;
    }
    public double getDefenseRoundsPlayed() {
        return defenseRoundsPlayed;
    }
    public double getDefenseRoundsWinPct() {
        return defenseRoundsWinPct;
    }
    public double getDefenseScore() {
        return defenseScore;
    }
    public double getDefenseScorePerRound() {
        return defenseScorePerRound;
    }
    public double getDefenseDamage() {
        return defenseDamage;
    }
    public double getDefenseDamagePerRound() {
        return defenseDamagePerRound;
    }
    public double getDefenseHeadshots() {
        return defenseHeadshots;
    }
    public double getDefenseTraded() {
        return defenseTraded;
    }
    public double getDefenseSurvived() {
        return defenseSurvived;
    }
    public double getDefenseFirstBloods() {
        return defenseFirstBloods;
    }
    public double getDefenseFirstBloodsPerRound() {
        return defenseFirstBloodsPerRound;
    }
    public double getDefenseFirstDeaths() {
        return defenseFirstDeaths;
    }
    public double getDefenseFirstDeathsPerRound() {
        return defenseFirstDeathsPerRound;
    }
    public double getDefenseKAST() {
        return defenseKAST;
    }
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