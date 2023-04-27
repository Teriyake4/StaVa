package com.teriyake.stava;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teriyake.stava.stats.Metadata;
import com.teriyake.stava.stats.Player;
import com.teriyake.stava.stats.player.PlayerBase;

public class Store {
    private File filePath; // C:/ProgramData/StaVa
    private String pattern; // ie A2, unit such as Acts and number
    private boolean idAsFileName;
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
     * Stores retrieved data for player, given is metadata and data itself. 
     * @param info The metadata of the data to store. 
     * @param toWrite The data to store. 
     */
    private void storePlayerData(Metadata info, String toWrite) {
        File storeTo = getFilePath(info);
        String fileName = "player.json";

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
    private File getFilePath(Metadata info) {
        String path = "player/";
        switch(pattern) {
            case "": // no patterm
                break;
            case "A": // by act
                path += info.getSeason();
                break;
            case "E": // by episode
                String child = info.getSeason();
                int i = child.indexOf(":");
                child = child.substring(i + 1);
                path += child;
                break;
        }
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
}
