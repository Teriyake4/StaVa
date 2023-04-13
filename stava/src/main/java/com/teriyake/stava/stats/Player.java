package com.teriyake.stava.stats;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.teriyake.stava.stats.player.PlayerAgent;
import com.teriyake.stava.stats.player.PlayerBase;
import com.teriyake.stava.stats.player.PlayerMap;
import com.teriyake.stava.stats.player.PlayerMode;
import com.teriyake.stava.stats.player.PlayerWeapon;

/**
 * Represents a player and their game statistics.
 */
public class Player {
    private Metadata info;
    private ArrayList<PlayerMode> modeStats;
    private ArrayList<PlayerMap> mapStats;
    private ArrayList<PlayerAgent> agentStats;
    private ArrayList<PlayerWeapon> weaponStats;

    /**
     * Constructs a new Player object with the given statistics and metadata.
     * @param mode An ArrayList of PlayerMode objects representing the player's statistics for different game modes.
     * @param map An ArrayList of PlayerMap objects representing the player's statistics for different game maps.
     * @param agent An ArrayList of PlayerAgent objects representing the player's statistics for different game agents.
     * @param weapon An ArrayList of PlayerWeapon objects representing the player's statistics for different game weapons.
     * @param metadata A Metadata object representing the player's metadata.
     */
    public Player(ArrayList<PlayerMode> mode, ArrayList<PlayerMap> map, 
        ArrayList<PlayerAgent> agent, ArrayList<PlayerWeapon> weapon, Metadata metadata) {
            modeStats = mode;
            mapStats = map;
            agentStats = agent;
            weaponStats = weapon;
            info = metadata;
    }

    /**
     * Returns the Metadata object containing basic player information
     * @return The Metadata object representing the player's metadata.
     */
    public Metadata info() {
        return info;
    }
    public ArrayList<PlayerMode> getAllModes() {
        return modeStats;
    }
    public ArrayList<PlayerMap> getAllMaps() {
        return mapStats;
    }
    public ArrayList<PlayerAgent> getAllAgents() {
        return agentStats;
    }
    public ArrayList<PlayerWeapon> getAllWeapons() {
        return weaponStats;
    }


    private static <T extends PlayerBase> T getType(ArrayList<T> elements, String sub) {
        for(T i : elements) {
            if(i.info().getSubType().equals(sub)) {
                return i;
            }
        }
        return null;
    }

    public PlayerMode getMode(String mode) {
        return getType(modeStats, mode);
    }
    public PlayerMap getMap(String map) {
        return getType(mapStats, map);
    }
    public PlayerAgent getAgent(String agent) {
        return getType(agentStats, agent);
    }
    public PlayerWeapon getWeapon(String weapon) {
        return getType(weaponStats, weapon);
    }

    public boolean containsMode(String mode) {
        return getType(modeStats, mode) != null;
    }
    public boolean containsMap(String map) {
        return getType(mapStats, map) != null;
    }
    public boolean containsAgent(String agent) {
        return getType(agentStats, agent) != null;
    }
    public boolean containsWeapon(String weapon) {
        return getType(weaponStats, weapon) != null;
    }


    private static <T extends PlayerBase> ArrayList<T> getByHighestStat
    (ArrayList<T> elements, String statMethod, boolean byLowest) {
        Method statGetter = null;
        try {
            statGetter = elements.get(0).getClass().getMethod(statMethod);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < elements.size(); i++) {
            double extreme = 0;
            int index = i;
            try {
                extreme = (double) statGetter.invoke(elements.get(i));
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            for(int j = i + 1; j < elements.size(); j++) {
                T element = elements.get(j);
                try {
                    double stat = (double) statGetter.invoke(element);
                    if(stat > extreme && !byLowest) {
                        extreme = stat;
                        index = j;
                    }
                    else if(stat < extreme && byLowest) {
                        extreme = stat;
                        index = j;
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
            T temp = elements.get(i);
            elements.set(i, elements.get(index));
            elements.set(index, temp);
        }
        return elements;
    }

    public ArrayList<PlayerMode> getModeByHighest(String statMethod, boolean byLowest) {
        return getByHighestStat(modeStats, statMethod, byLowest);
    }
    public ArrayList<PlayerMap> getMapByHighest(String statMethod, boolean byLowest) {
        return getByHighestStat(mapStats, statMethod, byLowest);
    }
    public ArrayList<PlayerAgent> getAgentByHighest(String statMethod, boolean byLowest) {
        return getByHighestStat(agentStats, statMethod, byLowest);
    }
    public ArrayList<PlayerWeapon> getWeaponByHighest(String statMethod, boolean byLowest) {
        return getByHighestStat(weaponStats, statMethod, byLowest);
    }
}
