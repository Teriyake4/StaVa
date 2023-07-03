package com.teriyake.stava.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MatchParser {

    public static String getMatchFromRecentMatchs(String jsonString) {
        Gson gson = new Gson();
        JsonArray array = gson.fromJson(jsonString, JsonObject.class)
            .get("data").getAsJsonObject()
            .get("matches").getAsJsonArray();
        int index = (int) (Math.random() * array.size());
        String matchId = array.get(index).getAsJsonObject()
            .get("attributes").getAsJsonObject()
            .get("id").getAsString();
        return matchId;
    }
    // temp
    public static void getSegments(String jsonString) {
        Gson gson = new Gson();
        JsonArray initJson = gson.fromJson(jsonString, JsonObject.class)
            .get("data").getAsJsonObject()
            .get("segments").getAsJsonArray();
        for(int i = 0; i < initJson.size(); i++) {
            System.out.println((i + ". ") + initJson.get(i).getAsJsonObject()
                .get("type").getAsString());
        }
    }
    // temp
    public static String[] getPlayers(String jsonString) {
        Gson gson = new Gson();
        JsonArray initJson = gson.fromJson(jsonString, JsonObject.class)
            .get("data").getAsJsonObject()
            .get("segments").getAsJsonArray();
        ArrayList<String> toAdd = new ArrayList<String>();
        for(int i = 0; i < initJson.size(); i++) {
            JsonObject segment = initJson.get(i).getAsJsonObject();
            if(segment.get("type").getAsString().equals("player-summary")) {
                toAdd.add(
                    segment.get("metadata").getAsJsonObject()
                    .get("platformInfo").getAsJsonObject()
                    .get("platformUserIdentifier").getAsString()
                );
            }
        }
        String[] players = new String[toAdd.size()];
        for(int i = 0; i < players.length; i++)
            players[i] = toAdd.get(i);
        return players;
    }

    public static String getMap(String jsonString) {
        Gson gson = new Gson();
        String map = gson.fromJson(jsonString, JsonObject.class)
            .get("data").getAsJsonObject()
            .get("metadata").getAsJsonObject()
            .get("mapName").getAsString();
        return map;
    }

    public static String getMode(String jsonString) {
        Gson gson = new Gson();
        String mode = gson.fromJson(jsonString, JsonObject.class)
            .get("data").getAsJsonObject()
            .get("metadata").getAsJsonObject()
            .get("queueId").getAsString();
        return mode;
    }

    /**
     * returns winning team of team from first half
     * @param jsonString
     * @return
     */
    public static String getWinningTeam(String jsonString) {
        Gson gson = new Gson();
        JsonArray initJson = gson.fromJson(jsonString, JsonObject.class)
            .get("data").getAsJsonObject()
            .get("segments").getAsJsonArray();
        JsonObject team1 = initJson.get(0).getAsJsonObject(); // Red / Attacker
        JsonObject team2 = initJson.get(1).getAsJsonObject(); // Blue / Defender
        // String team = team1.get("metadata").getAsJsonObject()
            // .get("name").getAsString();
        boolean winTeam1 = team1.get("metadata").getAsJsonObject()
            .get("hasWon").getAsBoolean();
        boolean winTeam2 = team2.get("metadata").getAsJsonObject()
            .get("hasWon").getAsBoolean();
        if(winTeam1)
            return "attacker";
        else if(winTeam2)
            return "defender";
        else if(!winTeam1 && !winTeam2)
            return "tie";
        return null;
    }

    /**
     * 
     * @param jsonString
     * @return
     */
    public static Map<String, ArrayList<String>> getTeams(String jsonString) {
        Gson gson = new Gson();
        JsonArray initJson = gson.fromJson(jsonString, JsonObject.class)
            .get("data").getAsJsonObject()
            .get("segments").getAsJsonArray();
        ArrayList<String> defender = new ArrayList<String>();
        ArrayList<String> attacker = new ArrayList<String>();
        for(int i = 0; i < initJson.size(); i++) {
            JsonObject segment = initJson.get(i).getAsJsonObject();
            if(segment.get("type").getAsString().equals("player-summary")) {
                String name = segment.get("metadata").getAsJsonObject()
                    .get("platformInfo").getAsJsonObject()
                    .get("platformUserIdentifier").getAsString();
                String team = segment.get("metadata").getAsJsonObject()
                    .get("teamId").getAsString();
                if(team.equals("Blue"))
                    defender.add(name);
                else if(team.equals("Red"))
                    attacker.add(name);
            }
        }
        Map<String, ArrayList<String>> teams = new HashMap<String, ArrayList<String>>();
        teams.put("defender", defender);
        teams.put("attacker", attacker);
        return teams;
    }

    public static String getAgentOfPlayer(String jsonString, String player) {
        Gson gson = new Gson();
        JsonArray initJson = gson.fromJson(jsonString, JsonObject.class)
            .get("data").getAsJsonObject()
            .get("segments").getAsJsonArray();
        for(int i = 0; i < initJson.size(); i++) {
            JsonObject segment = initJson.get(i).getAsJsonObject();
            if(segment.get("type").getAsString().equals("player-summary")) {
                String name = segment.get("metadata").getAsJsonObject()
                    .get("platformInfo").getAsJsonObject()
                    .get("platformUserIdentifier").getAsString();
                if(name.equals(player)) {
                    return segment.get("metadata").getAsJsonObject()
                    .get("agentName").getAsString();
                }
            }
        }
        return null;
    }

}
