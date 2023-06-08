package com.teriyake.stava.parser;

import java.util.ArrayList;

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

}
