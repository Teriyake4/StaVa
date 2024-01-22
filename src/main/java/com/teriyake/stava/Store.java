package com.teriyake.stava;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.teriyake.stava.stats.Player;
import com.teriyake.stava.stats.player.PlayerBase;
import com.teriyake.stava.stats.player.PlayerData;

public class Store {
    private File filePath; // C:/ProgramData/StaVa
    private String pattern; // ie A2, unit such as Acts and number
    private boolean idAsFileName;
    private File lastStored;
    /**
     * Constructs a new Store instance with a filepath of where to store the data. 
     * @param path of where to store data as a File object. 
     */
    public Store(File path) {
        filePath = new File(path, "/StaVa/data");
        pattern = "";
        idAsFileName = true;
    }
    /**
     * Constructs a new Store instance with a filepath of where to store the data. 
     * @param path of where to store data as a String. 
     */
    public Store(String path) {
        filePath = new File(path + "/StaVa/data");
        pattern = "";
        idAsFileName = true;
    }
    /**
     * Constructs a new Store instance with a default filepath. 
     */
    public Store() {
        String path = System.getProperty("user.home");
        filePath = new File(path + "/StaVa/data");
        pattern = "";
        idAsFileName = true;
    }
    /**
     * Set the file path of where the retrieved data should be stored. 
     * @param filePath The path of where to store retrieved data. 
     */
    public void setFilePath(File path) {
        filePath = path;
    }
    /**
     * Returns the path where the retrieved data is being stored. 
     * @return The path where the retrieved data is being stored. 
     */
    public File getFilePath() {
        return filePath;
    }
    /**
     * Set file name as player id when storing. 
     */
    public void setFileNameAsName() {
        idAsFileName = false;
    }
    /**
     * Set file name as player name when storing. 
     */
    public void setFileNameAsId() {
        idAsFileName = true;
    }
    /**
     * Seperates player data by act. 
     */
    public void setStorePatternByAct() {
        pattern = "A";
    }
    /**
     * Seperates player data by episode. 
     */
    public void setStorePatternByEpisode() {
        pattern = "E";
    }
    public String getStorePattern() {
        return pattern;
    }
    /**
     * Gives the file path of player last stored. 
     * @return file path of player last stored. 
     */
    public File getFileOfLastStored() {
        return lastStored;
    }
    /**
     * Stores retrieved data for player, given is metadata and data itself. 
     * @param info The metadata of the data to store. 
     * @param toWrite The data to store. 
     */
    private void storePlayerData(PlayerData info, String toWrite) throws NullPointerException {
        File storeTo = getFilePath(info);
        String fileName = "player.json";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        toWrite = gson.toJson(gson.fromJson(toWrite, Object.class));
        
        if(!storeTo.exists()) { // create folder
            storeTo.mkdirs();
            storeTo = new File(storeTo, fileName); // create file
            try {
                storeTo.createNewFile();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            storeTo = new File(storeTo, fileName);
        }
        StavaUtil.writeFile(storeTo, toWrite, false);
        lastStored = storeTo;
        // write to index
        // File index = new File(filePath, "index.txt");
        // toWrite = info.getName() + " = " + info.getUserID();
        // if(!StavaUtil.readFile(index).contains(toWrite))
        //     StavaUtil.writeFile(index, toWrite + "\n", true);
    }
    /**
     * Gives the filepath of where the data should be stored based on 
     * its metadata and the store pattern of this Store instance. 
     * @param info The metadata of the data to store. 
     * @return The path of where the data should be stored. 
     */
    private File getFilePath(PlayerData info) throws NullPointerException {
        if(info.getType() == null || info.getSeason() == null)
            throw new NullPointerException("Missing player data");
        String path = "/" + info.getType() + "/";
        String child = info.getSeason();
        switch(pattern) {
            case "": // no patterm
                break;
            case "A": // by act
                child = child.replaceAll(":", "-");
                path += child;
                break;
            case "E": // by episode
                int i = child.indexOf(":");
                child = child.substring(0, i);
                path += child;
                break;
        }
        if(info.getUserID() == null || info.getName() == null)
            throw new NullPointerException("Missing player data");
        path += "/";
        if(idAsFileName)
            path += info.getUserID();
        else
            path += info.getName();
        path += "/";
        File file = new File(filePath, path);
        return file;
    }
    /**
     * Stores retrieved data of player sub classes extending PlayerBase. 
     * @param <T> The player sub classes extending PlayerBase. 
     * @param player The data to store. 
     */
    public <T extends PlayerBase> void store(T player) throws NullPointerException {
        PlayerData info = player.info();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String toWrite = gson.toJson(player);
        storePlayerData(info, toWrite);
    }

    public <T extends PlayerBase> void store(T player, String toStore) throws NullPointerException {
        PlayerData info = player.info();
        storePlayerData(info, toStore);
    }

    /**
     * Stores retrieved data of Player. 
     * @param player The data to store. 
     */
    public void store(Player player) throws NullPointerException {
        PlayerData info = player.info();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String toWrite = gson.toJson(player);
        storePlayerData(info, toWrite);
    }

    public void store(Player player, String toStore) throws NullPointerException {
        PlayerData info = player.info();
        storePlayerData(info, toStore);
    }

    public void store(String match) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject info = gson.fromJson(match, JsonObject.class);
        String path = "/match/";
        path += info.get("data").getAsJsonObject()
            .get("metadata").getAsJsonObject()
            .get("seasonId").getAsString() + "/";
        path += info.get("data").getAsJsonObject()
            .get("attributes").getAsJsonObject()
            .get("id").getAsString() + "/";
        File storeTo = new File(filePath, path);

        String fileName = "match.json";

        if(!storeTo.exists()) { // create folder
            storeTo.mkdirs();
            storeTo = new File(storeTo, fileName); // create file
            try {
                storeTo.createNewFile();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        else {
            storeTo = new File(storeTo, fileName);
        }
        match = gson.toJson(gson.fromJson(match, Object.class));
        StavaUtil.writeFile(storeTo, match, false);
    }
}
