package com.teriyake.stava;

import java.util.List;

import com.microsoft.playwright.PlaywrightException;
import com.teriyake.stava.connection.ConnectPage;
import com.teriyake.stava.parser.SearchParser;
import com.teriyake.stava.parser.MatchParser;
import com.teriyake.stava.parser.PlayerParser;
import com.teriyake.stava.stats.Player;
import com.teriyake.stava.stats.player.PlayerAgent;
import com.teriyake.stava.stats.player.PlayerMap;
import com.teriyake.stava.stats.player.PlayerMode;
import com.teriyake.stava.stats.player.PlayerWeapon;

/**
 * Main class to retrieve and automatically parse.Option to also store data using the Store class. 
 */
public class Retriever {
    private static ConnectPage connect;
    private Store storage;
    private PlayerParser parser;
    /**
     * Constructs a new Retriever instance creating connection. 
     * @throws PlaywrightException if connection is unsuccessful. 
     */
    public Retriever() {
        storage = null;
        connect();
        parser = new PlayerParser();
    }
    /**
     * Constructs a new Retriever instance creating connection 
     * with storage to automatically store data. 
     * @param path The path of where to store retrieved data. 
     * @throws PlaywrightException if connection is unsuccessful. 
     */
    public Retriever(Store store){
        storage = store;
        connect();
        parser = new PlayerParser();
    }
    /**
     * Constructs a new Retriever instance with the given connection. 
     * @param connection to pass to the Retriever instance. 
     */
    public Retriever(ConnectPage connection) {
        connect = connection;
        storage = null;
        parser = new PlayerParser();
    }
    /**
     * Return 
     * @return
     */
    public Store getStorage() {
        return storage;
    }
    /**
     * Set a Store instance to store parsed data. 
     * @param store
     */
    public void setStorage(Store store) {
        storage = store;
    }
    /**
     * Removes Store, will not store data.
     */
    public void removeStorage() {
        storage = null;
    }
    /**
     * Creates Connection. Use only if connection was closed previously. 
     */
    public void connect() {
        if(connect == null)
            connect = new ConnectPage();
    }
    /**
     * Closes connection. Best to call this method after use of this 
     * Retriever instance is finished. 
     */
    public void closeConnection() {
        connect.close();
    }
    /**
     * Searches for specified player returning an array of results. 
     * @param name The player to search for. 
     * @return The results of searched player. 
     * @throws HttpStatusException
     */
    public List<String> getSearch(String name) throws HttpStatusException {
        String jsonResults = connect.getSearchPage(name);
        return SearchParser.getSearchResults(jsonResults);
    }
    /**
     * Searches for specified player returning an array of results for non private accounts. 
     * @param name The player to search for. 
     * @return The non private results of searched player. 
     * @throws HttpStatusException
     */
    public List<String> getNonPrivateSearch(String name) throws HttpStatusException {
        String jsonResults = connect.getSearchPage(name);
        return SearchParser.getNonPrivateSearchResults(jsonResults);
    }
    /**
     * Retrieves specified player and returns their stats. 
     * @param name The name of player to retrieve data for. 
     * Must include tagline ie. "example#NA1"
     * @return The specified player. 
     * @throws HttpStatusException
     */
    public Player getPlayer(String name) throws HttpStatusException, NullPointerException {
        String playerData = connect.getProfilePage(name);
        parser.setJsonString(playerData);
        Player player = parser.getPlayer();
        if(storage != null)
            storage.store(player, playerData);
        return player;
    } 
    /**
     * Retrieves specified player and returns their stats for a specific gamemode. 
     * @param name The name of player to retrieve data for. 
     * Must include tagline ie. "example#NA1"
     * @param mode The gamemode to get stats from ie. "competitive"
     * @return The stats of player for specified gamemode. 
     * @throws HttpStatusException
     */
    public PlayerMode getPlayerMode(String name, String mode) throws HttpStatusException, NullPointerException {
        String playerData = connect.getProfilePage(name);
        parser.setJsonString(playerData);
        PlayerMode player = parser.getPlayerMode(mode);
        if(storage != null)
            storage.store(player, playerData);
        return player;
    }
    /**
     * Retrieves specified player and returns their stats for a specific map. 
     * @param name The name of player to retrieve data for. 
     * Must include tagline ie. "example#NA1"
     * @param mode The map to get stats from ie. "lotus"
     * @return The stats of player for specified map. 
     * @throws HttpStatusException
     */
    public PlayerMap getPlayerMap(String name, String map) throws HttpStatusException, NullPointerException {
        String playerData = connect.getProfilePage(name);
        parser.setJsonString(playerData);
        PlayerMap player = parser.getPlayerMap(map);
        if(storage != null)
            storage.store(player, playerData);
        return player;
    }
    /**
     * Retrieves specified player and returns their stats for a specific agent. 
     * @param name The name of player to retrieve data for. 
     * Must include tagline ie. "example#NA1"
     * @param mode The gamemode to get stats from ie. "omen"
     * @return The stats of player for specified agent. 
     * @throws HttpStatusException
     */
    public PlayerAgent getPlayerAgent(String name, String agent) throws HttpStatusException, NullPointerException {
        String playerData = connect.getProfilePage(name);
        parser.setJsonString(playerData);
        PlayerAgent player = parser.getPlayerAgent(agent);
        if(storage != null)
            storage.store(player, playerData);
        return player;
    }
    /**
     * Retrieves specified player and returns their stats for a specific weapon. 
     * @param name The name of player to retrieve data for. 
     * Must include tagline ie. "example#NA1"
     * @param mode The weapon to get stats from ie. "vandal"
     * @return The stats of player for specified weapon
     * @throws HttpStatusException
     */
    public PlayerWeapon getPlayerWeapon(String name, String weapon) throws HttpStatusException, NullPointerException {
        String playerData = connect.getProfilePage(name);
        parser.setJsonString(playerData);
        PlayerWeapon player = parser.getPlayerWeapon(weapon);
        if(storage != null)
            storage.store(player, playerData);
        return player;
    }
    /**
     * temp
     * @param name
     * @return
     */
    // public String[] getPlayersFromRecentMatch(String name, String gameMode) throws HttpStatusException {
    //     String[] players = new String[10];
    //     String recentMatch = connect.getRecentMatchesPage(name, gameMode);
    //     String matches[] = MatchParser.getRecentMatchs(recentMatch);
    //     String match = connect.getMatchPage(matchId);
    //     if(storage != null)
    //         storage.store(match);
    //     players = MatchParser.getPlayers(match);
    //     return players;
    // }

    public String getMatch(String matchId) throws HttpStatusException {
        String matchJson = connect.getMatchPage(matchId);
        if(storage != null)
            storage.store(matchJson);
        return matchJson;
    }
    
    public String[] getRecentMatches(String name, String gameMode) throws HttpStatusException {
        String recentMatch = connect.getRecentMatchesPage(name, gameMode);
        return MatchParser.getRecentMatchs(recentMatch);
    }

}
