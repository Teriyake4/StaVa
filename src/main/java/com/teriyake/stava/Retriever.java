package com.teriyake.stava;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.playwright.PlaywrightException;
import com.teriyake.stava.connection.ConnectPage;
import com.teriyake.stava.parser.BasicParser;
import com.teriyake.stava.parser.PlayerParser;
import com.teriyake.stava.stats.Metadata;
import com.teriyake.stava.stats.Player;
import com.teriyake.stava.stats.player.PlayerAgent;
import com.teriyake.stava.stats.player.PlayerBase;
import com.teriyake.stava.stats.player.PlayerMap;
import com.teriyake.stava.stats.player.PlayerMode;
import com.teriyake.stava.stats.player.PlayerWeapon;

/**
 * Main class to retrieve, automatically parse, and store data. 
 */
public class Retriever {
    private static ConnectPage connect;
    private File storePath; // C:/ProgramData/StaVa
    private boolean toStore;
    /**
     * Constructs a new Retriever object without filepath to store 
     * data and to not automatically store retrieved data. 
     * @throws PlaywrightException if connection is unsuccessful. 
     */
    public Retriever() throws PlaywrightException {
        toStore = false;
        connect = new ConnectPage();
    }
    /**
     * Constructs a new Retriever object without filepath to store 
     * data and storing data set to false; 
     * @param path The path of where to store retrieved data. 
     * @param store Whether to store retrieved data. 
     * @throws PlaywrightException if connection is unsuccessful. 
     */
    public Retriever(File path, boolean store) throws PlaywrightException {
        storePath = new File(path, "data");
        toStore = store;
        connect = new ConnectPage();
    }
    /**
     * Sets whether retrieved data should be stored. 
     * @param toLog Whether to store retrieved data. 
     */
    public void setToStore(boolean store) {
        toStore = store;
    }
    /**
     * Returns whether retrieved data is being stored. 
     * @return Whether retrieved data is being stored. 
     */
    public boolean toStore() {
        return toStore;
    }
    /**
     * Set the file path of where the retrieved data should be stored. 
     * @param filePath The path of where to store retrieved data. 
     */
    public void setStorePath(File filePath) {
        storePath = filePath;
    }
    /**
     * Returns the path where the retrieved data is being stored. 
     * @return The path where the retrieved data is being stored. 
     */
    public File getStorePath() {
        return storePath;
    }
    /**
     * Stores retrieved data for player, given is metadata and data itself. 
     * @param info The metadata of the data to store. 
     * @param toWrite The data to store. 
     */
    private void storePlayerData(Metadata info, String toWrite) {
        String child = info.getUserID();
        File storeTo = new File(storePath, "player/" + child);
        if(!storeTo.exists()) {
            storeTo.mkdirs();
            storeTo = new File(storeTo, info.getType() + ".json");
            try {
                storeTo.createNewFile();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            storeTo = new File(storeTo, info.getType() + ".json");
        }
        StavaUtil.writeFile(storeTo, toWrite, false);
        // set index
        File index = new File(storePath, "index.txt");
        toWrite = info.getName() + " = " + info.getUserID();
        if(!StavaUtil.readFile(index).contains(toWrite)) {
            StavaUtil.writeFile(index, toWrite + "\n", true);
        }
    }
    /**
     * Stores retrieved data of player sub classes extending PlayerBase. 
     * @param <T> The player sub classes extending PlayerBase. 
     * @param player The data to store. 
     */
    public <T extends PlayerBase> void store(T player) {
        Metadata info = player.info();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String toWrite = gson.toJson(player);
        storePlayerData(info, toWrite);
    }
    /**
     * Stores retrieved data of Player. 
     * @param player The data to store. 
     */
    public void store(Player player) {
        Metadata info = player.info();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String toWrite = gson.toJson(player);
        storePlayerData(info, toWrite);
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
        if(toStore) {
            store(player);
        }
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
        if(toStore) {
            store(player);
        }
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
        if(toStore) {
            store(player);
        }
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
        if(toStore) {
            store(player);
        }
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
        if(toStore) {
            store(player);
        }
        return player;
    }

}
