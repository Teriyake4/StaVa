package com.teriyake.stava.parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MatchParser {
    public static void getSegments(String jsonString) {
        Gson gson = new Gson(); // error when it gets 403?
        JsonArray initJson = gson.fromJson(jsonString, JsonObject.class)
            .get("data").getAsJsonObject()
            .get("segments").getAsJsonArray();
        for(int i = 0; i < initJson.size(); i++) {
            System.out.println("type: " + initJson.get(i).getAsJsonObject()
                .get("type").getAsString());
        }
    }
}
