package com.teriyake.stava;

import com.microsoft.playwright.PlaywrightException;
import com.teriyake.stava.connection.ConnectPage;
import com.teriyake.stava.parser.BasicParser;
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
    /**
     * Constructs a new Retriever instance creating connection 
     * @throws PlaywrightException if connection is unsuccessful. 
     */
    public Retriever() {
        storage = null;
        connect();
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
     * @param name The player to seach for. 
     * @return The results of searched player. 
     * @throws HttpStatusException
     */
    public String[] getSearch(String name) throws HttpStatusException {
        String jsonResults = connect.getSearchPage(name);
        String[] results = BasicParser.getSearchResults(jsonResults);
        return results;
    }
    /**
     * Retrieves specified player and returns their stats. 
     * @param name The name of player to retrieve data for. 
     * Must include tagline ie. "example#NA1"
     * @return The specified player. 
     * @throws HttpStatusException
     */
    public Player getPlayer(String name) throws HttpStatusException {
        String playerData = connect.getProfilePage(name);
        Player player = PlayerParser.getPlayer(playerData);
        if(storage != null)
            storage.store(player);
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
    public PlayerMode getPlayerMode(String name, String mode) throws HttpStatusException {
        String playerData = connect.getProfilePage(name);
        PlayerMode player = PlayerParser.getPlayerMode(playerData, mode);
        if(storage != null)
            storage.store(player);
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
    public PlayerMap getPlayerMap(String name, String map) throws HttpStatusException {
        String playerData = connect.getProfilePage(name);
        PlayerMap player = PlayerParser.getPlayerMap(playerData, map);
        if(storage != null)
            storage.store(player);
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
    public PlayerAgent getPlayerAgent(String name, String agent) throws HttpStatusException {
        String playerData = connect.getProfilePage(name);
        PlayerAgent player = PlayerParser.getPlayerAgent(playerData, agent);
        if(storage != null)
            storage.store(player);
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
    public PlayerWeapon getPlayerWeapon(String name, String weapon) throws HttpStatusException {
        String playerData = connect.getProfilePage(name);
        PlayerWeapon player = PlayerParser.getPlayerWeapon(playerData, weapon);
        if(storage != null)
            storage.store(player);
        return player;
    }

}
