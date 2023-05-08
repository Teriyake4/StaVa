package com.teriyake.stava.parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MatchParser {
    // temp
    public static void getSegments(String jsonString) {
        Gson gson = new Gson(); // error when it gets 403?
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
        Gson gson = new Gson(); // error when it gets 403?
        JsonArray initJson = gson.fromJson(jsonString, JsonObject.class)
            .get("data").getAsJsonObject()
            .get("segments").getAsJsonArray();
        String[] players = new String[10];
        int listIndex = 0;
        for(int i = 0; i < initJson.size(); i++) {
            JsonObject segment = initJson.get(i).getAsJsonObject();
            if(segment.get("type").getAsString().equals("player-summary")) {
                players[listIndex] = segment.get("metadata").getAsJsonObject()
                    .get("platformInfo").getAsJsonObject()
                    .get("platformUserIdentifier").getAsString();
                listIndex++;
            }
        }
        return players;
    }

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

}
