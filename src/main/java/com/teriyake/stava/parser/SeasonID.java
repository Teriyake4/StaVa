package com.teriyake.stava.parser;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SeasonId {
    // https://api.tracker.gg/api/v1/valorant/db/seasons/
    private Map<String, String> seasonIdToSeason;

    public SeasonId(String seasonsJson) {
        jsonToMap(seasonsJson);
    }

    private void jsonToMap(String seasonsJson) {
        JsonObject episodeList = JsonParser.parseString(seasonsJson).getAsJsonObject()
            .getAsJsonObject("data");
        for(Map.Entry<String, JsonElement> entry : episodeList.entrySet()) {
            JsonObject episode = entry.getValue().getAsJsonObject();
            String episodeNum = getNumFromJson(episode);
            JsonArray acts = episode.getAsJsonArray("seasons");
            for(int i = 0; i < acts.size(); i++) {
                JsonObject act = acts.get(i).getAsJsonObject();
                String actNum = getNumFromJson(act);
                seasonIdToSeason.put(
                    new StringBuilder(episodeNum).append(":").append(actNum).toString(),
                    act.get("id").getAsString());
            }            
        }
        episodeList = episodeList.getAsJsonObject("0");
    }

    private String getNumFromJson(JsonObject json) {
        return new StringBuilder(
            json.get("shortName").getAsString())
            .substring(1).toString();
    }
}
