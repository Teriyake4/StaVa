package com.teriyake.stava.parser;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.teriyake.stava.stats.Player;
import com.teriyake.stava.stats.player.PlayerAgent;
import com.teriyake.stava.stats.player.PlayerAgentRole;
import com.teriyake.stava.stats.player.PlayerData;
import com.teriyake.stava.stats.player.PlayerMap;
import com.teriyake.stava.stats.player.PlayerMapTopAgent;
import com.teriyake.stava.stats.player.PlayerMode;
import com.teriyake.stava.stats.player.PlayerWeapon;

public class PlayerParser {

    /**
     * Orginizes the player data of the json string, and sets the metadata
     * @param jsonString
     * @return JsonObject with general metadata information
     */
    private static JsonObject preParse(String jsonString) {
        Gson gson = new Gson(); // error when it gets 403?
        JsonObject initJson = gson.fromJson(jsonString, JsonObject.class)
            .get("data").getAsJsonObject();
        JsonArray segments = initJson.get("segments").getAsJsonArray();
        JsonObject platformInfo = initJson.get("platformInfo").getAsJsonObject();
        JsonObject metadata = new JsonObject();

        String data = platformInfo.get("platformUserHandle").getAsString();
        metadata.addProperty("name", data);

        data = platformInfo.get("platformUserId").getAsString();
        metadata.addProperty("userID", data);

        data = platformInfo.get("avatarUrl").getAsString();
        metadata.addProperty("avatarURL", data);

        data = initJson.get("metadata").getAsJsonObject()
            .get("activeShard").getAsString();
        metadata.addProperty("region", data);

        data = initJson.get("expiryDate").getAsString();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        OffsetDateTime date = OffsetDateTime.parse(data, formatter);
        ZonedDateTime dateTime = date.atZoneSameInstant(ZoneId.of("America/Los_Angeles"));
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z");
        data = dateTime.format(formatter);
        metadata.addProperty("date", data);

        for(int i = 0; i < segments.size(); i++) {
            String type = segments.get(i).getAsJsonObject()
                .get("type").getAsString();
            if(type.equals("season")) {
                data = segments.get(i).getAsJsonObject()
                    .get("metadata").getAsJsonObject()
                    .get("name").getAsString();
                int index = data.indexOf(" ");
                data = data.substring(index + 1).replace(" ACT ", "");
                index = data.indexOf(" ");
                data = data.substring(0, index);
                metadata.addProperty("season", data);
                break;
            }
        }

        data = initJson.get("metadata").getAsJsonObject()
            .get("defaultSeason").getAsString();
        metadata.addProperty("seasonID", data);

        JsonObject playerJson = new JsonObject();
        playerJson.add("metadata", metadata);
        playerJson.add("segments", segments);

        return playerJson;
    }

    /**
     * Removes unused data from the stats. 
     * @param jsonData
     * @return JsonObjects with nessecary stats and metadata
     */
    private static JsonObject statCompress(JsonObject jsonData) {
        JsonObject compressed = new JsonObject();
        jsonData = jsonData.get("stats").getAsJsonObject();
        for(Map.Entry<String, JsonElement> entry : jsonData.entrySet()) {
            String key = entry.getKey();
            if(key.equals("rank") || key.equals("peakRank")) {
                String rankData = jsonData.get(key).getAsJsonObject()
                    .get("metadata").getAsJsonObject()
                    .get("tierName").getAsString()
                    .toLowerCase();
                compressed.addProperty(key, rankData);
            }
            else {
                double data = jsonData.get(key).getAsJsonObject()
                    .get("value").getAsDouble();
                compressed.addProperty(key, data);
            }
        }
        return compressed;
    }

    /**
     * Orginizes and removes stats and data
     * @param jsonData
     * @return JsonObject that is orginized
     */
    private static JsonObject parse(JsonObject jsonData) {
        JsonArray segments = jsonData.get("segments").getAsJsonArray();
        JsonObject metadata = jsonData.get("metadata").getAsJsonObject();

        JsonObject parsedData = new JsonObject();
        JsonObject season = new JsonObject();
        JsonObject map = new JsonObject();
        JsonObject agent = new JsonObject();
        JsonObject weapon = new JsonObject();
        JsonObject mapTopAgent = new JsonObject();
        JsonObject agentRole = new JsonObject();

        for(int i = 0; i < segments.size(); i++) {
            JsonObject segment = segments.get(i).getAsJsonObject();
            JsonObject data = new JsonObject();
            String type = segment.get("type").getAsString();
            String sub = getSub(segment);
            String gameMode = segment.get("attributes").getAsJsonObject()
                .get("playlist").getAsString();
            String url = "";

            if(type.equals("season")) { // gamemodes
                if(sub.equals("competitive")) {
                    // nested because it would go to else statement, 
                    // since dm doesn't have a pic and it would throw an error otherwise
                    url = segment.get("stats").getAsJsonObject()
                        .get("rank").getAsJsonObject()
                        .get("metadata").getAsJsonObject()
                        .get("iconUrl").getAsString();
                    data.addProperty("itemImageURL", url);
                }
            }
            else { // agent, map, weapon, map-top-agent, agent-role
                url = segment.get("metadata").getAsJsonObject()
                    .get("imageUrl").getAsString();
                data.addProperty("itemImageURL", url);
            }
            data.addProperty("gameMode", gameMode);
            segment = statCompress(segment);
            segment.add("metadata", data);
            switch(type) {
                case "season":
                    season.add(sub, segment);
                    break;
                case "map":
                    map.add(sub, segment);
                    break;
                case "agent":
                    agent.add(sub, segment);
                    break;
                case "weapon":
                    weapon.add(sub, segment);
                    break;
                case "map-top-agent":
                    mapTopAgent.add(sub, segment);
                    break;
                case "agent-role":
                    agentRole.add(sub, segment);
                    break;
            }
        }
        parsedData.add("metadata", metadata);
        parsedData.add("season", season);
        parsedData.add("map", map);
        parsedData.add("agent", agent);
        parsedData.add("weapon", weapon);
        parsedData.add("map-top-agent", mapTopAgent);
        parsedData.add("agent-role", agentRole);

        return parsedData;
    }

    /**
     * Gets the subtype of the specified type of data.
     * @param segment
     * @return subtype String. Always lowercase
     */
    private static String getSub(JsonObject segment) {
        JsonObject attribute = segment.get("attributes").getAsJsonObject();
        String type = segment
            .get("type").getAsString();
        String sub = "";
        switch(type) {
            case "season":
                sub = attribute.get("playlist").getAsString();
                break;
            case "map":
                sub = attribute.get("key").getAsString();
                break;
            case "agent":
                sub = segment.get("metadata").getAsJsonObject()
                    .get("name").getAsString().toLowerCase();
                break;
            case "weapon":
                sub = attribute.get("key").getAsString();
                break;
            case "map-top-agent":
                sub = attribute.get("mapKey").getAsString() + "-" + 
                    attribute.get("agentKey").getAsString();
                break;
            case "agent-role":
                sub = segment.get("metadata").getAsJsonObject()
                    .get("name").getAsString().toLowerCase();
                break;
        }
        return sub;
    }

    /**
     * 
     * @param jsonPlayer JsonObject to get the data from
     * @param type of data to get
     * @param sub subtype of data to get from the type. 
     * @return JsonObject of the specified subtype data from jsonPlayer
     */
    private static JsonObject getSubType(JsonObject jsonPlayer, String type, String sub) {
        jsonPlayer = jsonPlayer
            .get(type).getAsJsonObject()
            .get(sub).getAsJsonObject();
        JsonObject innerData = jsonPlayer.get("metadata").getAsJsonObject();
        String gameMode = innerData.get("gameMode").getAsString();
        // String itemImage = "";
        // itemImage = innerData.get("itemImageURL").getAsString();

        JsonObject metadata = jsonPlayer.get("metadata").getAsJsonObject();
        metadata.addProperty("type", type);
        metadata.addProperty("subType", sub);
        metadata.addProperty("gameMode", gameMode);
        // metadata.addProperty("itemImageURL", itemImage);
        jsonPlayer.remove("metadata");
        jsonPlayer.add("info", metadata);
        return jsonPlayer;
    }

    /**
     * Get the segment list of types
     * @param jsonString
     * @return Map<String, ArrayList<String>> with key as the type and the value
     * containing an arraylist of the subtypes. 
     */
    public static Map<String, ArrayList<String>> getTypes(String jsonString) {
        JsonObject preJson = preParse(jsonString);
        JsonArray jsonSegments = preJson.get("segments").getAsJsonArray();
        Map<String, ArrayList<String>> output = new HashMap<String, ArrayList<String>>();
        ArrayList<String> season = new ArrayList<String>();
        ArrayList<String> agent = new ArrayList<String>();
        ArrayList<String> map = new ArrayList<String>();
        ArrayList<String> weapon = new ArrayList<String>();
        ArrayList<String> mapTopAgent = new ArrayList<String>();
        ArrayList<String> agentRole = new ArrayList<String>();

        for(int i = 0; i < jsonSegments.size(); i++) {
            JsonObject initData = jsonSegments.get(i).getAsJsonObject();
            String dataType = initData.get("type").getAsString();
            String subType = getSub(initData);

            switch(dataType) {
                case "season":
                    season.add(subType);
                    break;
                case "map":
                    map.add(subType);
                    break;
                case "agent":
                    agent.add(subType);
                    break;
                case "weapon":
                    weapon.add(subType);
                    break;
                case "map-top-agent":
                    mapTopAgent.add(subType);
                    break;
                case "agent-role":
                    agentRole.add(subType);
                    break;
            }
        }
        output.put("season", season);
        output.put("agent", agent);
        output.put("map", map);
        output.put("weapon", weapon);
        output.put("mapTopAgent", mapTopAgent);
        output.put("agentRole", agentRole);
        return output;
    }

    /**
     * Parses and orginizes all the player data of the jsonString into a 
     * Player Object
     * @param jsonString to convert to Player Object
     * @return Player Object with all stats from the JsonObject
     */
    public static Player getPlayer(String jsonString) {
        Gson gson = new Gson();
        JsonObject jsonPlayer = preParse(jsonString);
        jsonPlayer = parse(jsonPlayer);
        JsonObject metadata = jsonPlayer.get("metadata").getAsJsonObject();
        metadata.addProperty("type", "player");
        PlayerData info = gson.fromJson(metadata, PlayerData.class);

        Map<String, PlayerMode> modeStats = new HashMap<String, PlayerMode>();
        Map<String, PlayerMap> mapStats = new HashMap<String, PlayerMap>();
        Map<String, PlayerAgent> agentStats = new HashMap<String, PlayerAgent>();
        Map<String, PlayerWeapon> weaponStats = new HashMap<String, PlayerWeapon>();
        Map<String, PlayerMapTopAgent> mapTopAgentStats = new HashMap<String, PlayerMapTopAgent>();
        Map<String, PlayerAgentRole> agentRoleStats = new HashMap<String, PlayerAgentRole>();
        Map<String, ArrayList<String>> segments = getTypes(jsonString);
        for(Map.Entry<String, ArrayList<String>> types : segments.entrySet()) {
            for(String sub : types.getValue()) { // iterate over array with subtypes
                switch(types.getKey()) {
                    case "season":
                        modeStats.put(sub, getPlayerMode(jsonString, sub));
                        break;
                    case "map":
                        mapStats.put(sub, getPlayerMap(jsonString, sub));
                        break;
                    case "agent":
                        agentStats.put(sub, getPlayerAgent(jsonString, sub));
                        break;
                    case "weapon":
                        weaponStats.put(sub, getPlayerWeapon(jsonString, sub));
                        break;
                    case "map-top-agent":
                        mapTopAgentStats.put(sub, getPlayerMapTopAgent(jsonString, sub));
                        break;
                    case "agent-role":
                        agentRoleStats.put(sub, getPlayerAgentRole(jsonString, sub));
                        break;
                }
            }
        }
        Player output = new Player(modeStats, mapStats, agentStats, weaponStats, 
            mapTopAgentStats, agentRoleStats, info);
        return output;
    }

    /**
     * Mode also known as season
     * Parses the given json string to get specified stat as a PlayerMode object
     * @param jsonString playerdata to get stats from
     * @param mode stats to get from selected mode such as "competitive", or 
     * "deathmatch"
     * @return PlayerMode Object with stats of the specified gamemode
     */
    public static PlayerMode getPlayerMode(String jsonString, String mode) {
        JsonObject jsonPlayer = preParse(jsonString);
        jsonPlayer = parse(jsonPlayer);
        jsonPlayer = getSubType(jsonPlayer, "season", mode);
        Gson gson = new Gson();
        PlayerMode finalData = gson.fromJson(jsonPlayer, PlayerMode.class);
        return finalData;
    }

    /**
     * Parses the given json string to get specified stat as a PlayerMap object
     * @param jsonString playerdata to get stats from
     * @param mode stats to get from selected mode such as "lotus", or 
     * "icebox"
     * @return PlayerMap Object with stats of the specified map
     */
    public static PlayerMap getPlayerMap(String jsonString, String map) {
        JsonObject jsonPlayer = preParse(jsonString);
        jsonPlayer = parse(jsonPlayer);
        jsonPlayer = getSubType(jsonPlayer, "map", map);
        Gson gson = new Gson();
        PlayerMap finalData = gson.fromJson(jsonPlayer, PlayerMap.class);
        return finalData;
    }

    /**
     * Parses the given json string to get specified stat as a PlayerAgent object
     * @param jsonString playerdata to get stats from
     * @param mode stats to get from selected mode such as "viper", or 
     * "brimstone"
     * @return PlayerAgent Object with stats of the specified agent
     */
    public static PlayerAgent getPlayerAgent(String jsonString, String agent) {
        JsonObject jsonPlayer = preParse(jsonString);
        jsonPlayer = parse(jsonPlayer);
        jsonPlayer = getSubType(jsonPlayer, "agent", agent);
        Gson gson = new Gson();
        PlayerAgent finalData = gson.fromJson(jsonPlayer, PlayerAgent.class);
        return finalData;
    }

    /**
     * Parses the given json string to get specified stat as a PlayerWeapon object
     * @param jsonString playerdata to get stats from
     * @param mode stats to get from selected mode such as "phantom", or 
     * "classic"
     * @return PlayerWeapon Object with stats of the specified weapon
     */
    public static PlayerWeapon getPlayerWeapon(String jsonString, String weapon) {
        JsonObject jsonPlayer = preParse(jsonString);
        jsonPlayer = parse(jsonPlayer);
        jsonPlayer = getSubType(jsonPlayer, "weapon", weapon);
        Gson gson = new Gson();
        PlayerWeapon finalData = gson.fromJson(jsonPlayer, PlayerWeapon.class);
        return finalData;
    }

    /**
     * Parses the given json string to get specified stat as a PlayerWeapon object
     * @param jsonString playerdata to get stats from
     * @param mode stats to get from selected mode such as "phantom", or 
     * "classic"
     * @return PlayerWeapon Object with stats of the specified weapon
     */
    public static PlayerMapTopAgent getPlayerMapTopAgent(String jsonString, String weapon) {
        JsonObject jsonPlayer = preParse(jsonString);
        jsonPlayer = parse(jsonPlayer);
        jsonPlayer = getSubType(jsonPlayer, "map-top-agent", weapon);
        Gson gson = new Gson();
        PlayerMapTopAgent finalData = gson.fromJson(jsonPlayer, PlayerMapTopAgent.class);
        return finalData;
    }

    /**
     * Parses the given json string to get specified stat as a PlayerWeapon object
     * @param jsonString playerdata to get stats from
     * @param mode stats to get from selected mode such as "phantom", or 
     * "classic"
     * @return PlayerWeapon Object with stats of the specified weapon
     */
    public static PlayerAgentRole getPlayerAgentRole(String jsonString, String weapon) {
        JsonObject jsonPlayer = preParse(jsonString);
        jsonPlayer = parse(jsonPlayer);
        jsonPlayer = getSubType(jsonPlayer, "agent-role", weapon);
        Gson gson = new Gson();
        PlayerAgentRole finalData = gson.fromJson(jsonPlayer, PlayerAgentRole.class);
        return finalData;
    }

    /**
     * Converts already parsed json Player to Player object. 
     * @param jsonString to convert to Player Object
     * @return Player Object
     */
    public static Player parsedJsonToPlayer(String jsonString) {
        Gson gson = new Gson();
        Player finalData = gson.fromJson(jsonString, Player.class);
        return finalData;
    }

    
 }
