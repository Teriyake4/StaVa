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
import com.google.gson.JsonParser;
import com.teriyake.stava.stats.Player;
import com.teriyake.stava.stats.player.PlayerAgent;
import com.teriyake.stava.stats.player.PlayerAgentRole;
import com.teriyake.stava.stats.player.PlayerData;
import com.teriyake.stava.stats.player.PlayerMap;
import com.teriyake.stava.stats.player.PlayerMapTopAgent;
import com.teriyake.stava.stats.player.PlayerMode;
import com.teriyake.stava.stats.player.PlayerWeapon;

public class PlayerParser {
    private Gson gson;
    private String jsonString;
    private JsonObject parsed;
    private JsonObject preParsed;
    private Map<String, ArrayList<String>> types;
    private boolean isParsed;

    public PlayerParser(String jsonString) throws NullPointerException {
        gson = new Gson();
        if(jsonString == null)
            throw new NullPointerException("jsonString can not be null");
        this.jsonString = jsonString;
        this.parsed = null;
        this.preParsed = null;
        this.types = null;
        this.isParsed = false;
    }

    public PlayerParser() {
        gson = new Gson();
        this.jsonString = null;
        this.parsed = null;
        this.preParsed = null;
        this.types = null;
        this.isParsed = false;
    }

    public void setJsonString(String jsonString) throws NullPointerException {
        if(jsonString == null)
            throw new NullPointerException("jsonString can not be null");
        this.jsonString = jsonString;
        this.isParsed = false;
    }

    private void initParse() {
        if(isParsed)
            return;
        this.preParsed = preParse();
        this.types = null;
        this.types = getTypes();
        this.parsed = parse();
        this.isParsed = true;
    }

    /**
     * Orginizes the player data of the json string, and sets the metadata
     * @param jsonString
     * @return JsonObject with general metadata information
     */
    private JsonObject preParse() {
        // long startTime = System.nanoTime();

        // error when it gets 403?
        JsonObject initJson = JsonParser.parseString(this.jsonString).getAsJsonObject()
        // JsonObject initJson = gson.fromJson(this.jsonString, JsonObject.class)
            .get("data").getAsJsonObject();
        JsonArray segments = initJson.getAsJsonArray("segments");
        JsonObject platformInfo = initJson.getAsJsonObject("platformInfo");
        JsonObject metadata = new JsonObject();

        metadata.addProperty("name", platformInfo.getAsJsonPrimitive("platformUserHandle").getAsString());

        metadata.addProperty("userID", platformInfo.getAsJsonPrimitive("platformUserId").getAsString());

        JsonElement temp = platformInfo.get("avatarUrl");
        String data = null;
        if(temp.isJsonNull()) // rare case when avatar url is null. replaced with default avatar
            data = "https://imgsvc.trackercdn.com/url/size(128),fit(cover)/https%3A%2F%2Ftrackercdn.com%2Fcdn%2Ftracker.gg%2Fvalorant%2Fimages%2Fdefault-avatar.png/image.jpg";
        else
            data = platformInfo.getAsJsonPrimitive("avatarUrl").getAsString();
        metadata.addProperty("avatarURL", data);

        metadata.addProperty("region", initJson.getAsJsonObject("metadata")
            .getAsJsonPrimitive("activeShard").getAsString());

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        OffsetDateTime date = OffsetDateTime.parse(initJson.getAsJsonPrimitive("expiryDate").getAsString(), formatter);
        ZonedDateTime dateTime = date.atZoneSameInstant(ZoneId.of("America/Los_Angeles"));
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z");
        metadata.addProperty("date", dateTime.format(formatter));

        String type = null;
        for(JsonElement segment : segments) {
            JsonObject segmentObject = segment.getAsJsonObject();
            type = segmentObject.getAsJsonPrimitive("type").getAsString();
            if(type.equals("season")) {
                segmentObject = segmentObject.getAsJsonObject("metadata");
                String seasonInput = segmentObject.getAsJsonPrimitive("name").getAsString();
                StringBuilder builder = new StringBuilder();
                for(int i = 0; i < seasonInput.length(); i++) { // condenses season into value:value
                    char c = seasonInput.charAt(i);
                    if(Character.isDigit(c))
                        builder.append(c);
                    else if(c == ':')
                        builder.append(':');
                }
                metadata.addProperty("season", builder.toString());
                break;
            }
        }

        data = initJson.getAsJsonObject("metadata")
            .get("defaultSeason").getAsString();
        metadata.addProperty("seasonID", data);

        JsonObject playerJson = new JsonObject();
        playerJson.add("metadata", metadata);
        playerJson.add("segments", segments);
        // System.out.println("Parse Time: " + (System.nanoTime() - startTime) / 1000000 + "ms");

        return playerJson;
    }

    /**
     * Removes unused data from the stats. 
     * @param jsonData
     * @return JsonObjects with nessecary stats and metadata
     */
    private JsonObject statCompress(JsonObject jsonData) {
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
    private JsonObject parse() {
        JsonObject jsonData = this.preParsed;
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
            if(type.equals("peak-rating"))
                continue;
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
    private String getSub(JsonObject segment) {
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
    private JsonObject getSubType(JsonObject jsonPlayer, String type, String sub) {
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
    public Map<String, ArrayList<String>> getTypes() throws NullPointerException {
        if(this.types != null)
            return this.types;
        if(this.jsonString == null)
            throw new NullPointerException("Must set json string to parse using SetJsonString");
        
        JsonObject preJson = this.preParsed;
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

    private void distributeStats(String key, String sub,
        Map<String, PlayerMode> modeStats,
        Map<String, PlayerMap> mapStats,
        Map<String, PlayerAgent> agentStats,
        Map<String, PlayerWeapon> weaponStats,
        Map<String, PlayerMapTopAgent> mapTopAgentStats,
        Map<String, PlayerAgentRole> agentRoleStats) {
        switch(key) {
            case "season":
                modeStats.put(sub, getPlayerMode(sub));
                break;
            case "map":
                mapStats.put(sub, getPlayerMap(sub));
                break;
            case "agent":
                agentStats.put(sub, getPlayerAgent(sub));
                break;
            case "weapon":
                weaponStats.put(sub, getPlayerWeapon(sub));
                break;
            case "mapTopAgent":
                mapTopAgentStats.put(sub, getPlayerMapTopAgent(sub));
                break;
            case "agentRole":
                agentRoleStats.put(sub, getPlayerAgentRole(sub));
                break;
            default:
                throw new IllegalArgumentException("Unsupported type: " + key);
        }
    }

    /**
     * Parses and orginizes all the player data of the jsonString into a 
     * Player Object
     * @param jsonString to convert to Player Object
     * @return Player Object with all stats from the JsonObject
     */
    public Player getPlayer() throws NullPointerException {
        // long startTime = System.nanoTime();
        if(this.jsonString == null)
            throw new NullPointerException("Must set json string to parse using SetJsonString");
        initParse();
        JsonObject jsonPlayer = this.parsed;
        JsonObject metadata = jsonPlayer.get("metadata").getAsJsonObject();
        metadata.addProperty("type", "player");
        PlayerData info = gson.fromJson(metadata, PlayerData.class);

        Map<String, PlayerMode> modeStats = new HashMap<String, PlayerMode>();
        Map<String, PlayerMap> mapStats = new HashMap<String, PlayerMap>();
        Map<String, PlayerAgent> agentStats = new HashMap<String, PlayerAgent>();
        Map<String, PlayerWeapon> weaponStats = new HashMap<String, PlayerWeapon>();
        Map<String, PlayerMapTopAgent> mapTopAgentStats = new HashMap<String, PlayerMapTopAgent>();
        Map<String, PlayerAgentRole> agentRoleStats = new HashMap<String, PlayerAgentRole>();
        Map<String, ArrayList<String>> segments = getTypes();

        // segments.entrySet().parallelStream().forEach(types -> {
        for(Map.Entry<String, ArrayList<String>> types : segments.entrySet()) {
            for(String sub : types.getValue()) { // iterate over array with subtypes
            // types.getValue().parallelStream().forEach(sub -> {
                distributeStats(types.getKey(), sub,
                    modeStats,
                    mapStats,
                    agentStats,
                    weaponStats,
                    mapTopAgentStats,
                    agentRoleStats
                );
            }
        }

        // System.out.println("Get Time: " + (System.nanoTime() - startTime) / 1000000 + "ms");

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
    public PlayerMode getPlayerMode(String mode) throws NullPointerException {
        if(this.jsonString == null)
            throw new NullPointerException("Must set json string to parse using SetJsonString");
        initParse();
        JsonObject jsonPlayer = getSubType(this.parsed, "season", mode);
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
    public PlayerMap getPlayerMap(String map) throws NullPointerException {
        if(this.jsonString == null)
            throw new NullPointerException("Must set json string to parse using SetJsonString");
        initParse();
        JsonObject jsonPlayer = getSubType(this.parsed, "map", map);
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
    public PlayerAgent getPlayerAgent(String agent) throws NullPointerException {
        if(this.jsonString == null)
            throw new NullPointerException("Must set json string to parse using SetJsonString");
        initParse();
        JsonObject jsonPlayer = getSubType(this.parsed, "agent", agent);
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
    public PlayerWeapon getPlayerWeapon(String weapon) throws NullPointerException {
        if(this.jsonString == null)
            throw new NullPointerException("Must set json string to parse using SetJsonString");
        initParse();
        JsonObject jsonPlayer = getSubType(this.parsed, "weapon", weapon);
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
    public PlayerMapTopAgent getPlayerMapTopAgent(String weapon) throws NullPointerException {
        if(this.jsonString == null)
            throw new NullPointerException("Must set json string to parse using SetJsonString");
        initParse();
        JsonObject jsonPlayer = getSubType(this.parsed, "map-top-agent", weapon);
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
    public PlayerAgentRole getPlayerAgentRole(String weapon) throws NullPointerException {
        if(this.jsonString == null)
            throw new NullPointerException("Must set json string to parse using SetJsonString");
        initParse();
        JsonObject jsonPlayer = getSubType(this.parsed, "agent-role", weapon);
        PlayerAgentRole finalData = gson.fromJson(jsonPlayer, PlayerAgentRole.class);
        return finalData;
    }

    /**
     * Converts already parsed json Player to Player object. 
     * @param jsonString to convert to Player Object
     * @return Player Object
     */
    public Player parsedJsonToPlayer() throws NullPointerException {
        if(this.jsonString == null)
            throw new NullPointerException("Must set json string to parse using SetJsonString");
        Player finalData = gson.fromJson(jsonString, Player.class);
        return finalData;
    }
 }