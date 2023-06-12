package com.teriyake.stava.stats;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import com.teriyake.stava.stats.player.PlayerAgent;
import com.teriyake.stava.stats.player.PlayerAgentRole;
import com.teriyake.stava.stats.player.PlayerBase;
import com.teriyake.stava.stats.player.PlayerData;
import com.teriyake.stava.stats.player.PlayerMap;
import com.teriyake.stava.stats.player.PlayerMapTopAgent;
import com.teriyake.stava.stats.player.PlayerMode;
import com.teriyake.stava.stats.player.PlayerWeapon;

/**
 * Represents a player and their game statistics.
 */
public class Player {
    private PlayerData info;
    private Map<String, PlayerMode> modeStats;
    private Map<String, PlayerMap> mapStats;
    private Map<String, PlayerAgent> agentStats;
    private Map<String, PlayerWeapon> weaponStats;
    private Map<String, PlayerMapTopAgent> mapTopAgentStats;
    private Map<String, PlayerAgentRole> agentRoleStats;

    /**
     * Constructs a new Player object with the given statistics and metadata.
     * @param mode An Map of PlayerMode objects representing the player's statistics for different game modes.
     * @param map An Map of PlayerMap objects representing the player's statistics for different game maps.
     * @param agent An Map of PlayerAgent objects representing the player's statistics for different game agents.
     * @param weapon An Map of PlayerWeapon objects representing the player's statistics for different game weapons.
     * @param metadata A Metadata object representing the player's metadata.
     */
    public Player(Map<String, PlayerMode> mode, Map<String, PlayerMap> map, 
        Map<String, PlayerAgent> agent, Map<String, PlayerWeapon> weapon, 
        Map<String, PlayerMapTopAgent> mapTopAgent, Map<String, PlayerAgentRole> agentRole, 
        PlayerData metadata) {
            modeStats = mode;
            mapStats = map;
            agentStats = agent;
            weaponStats = weapon;
            mapTopAgentStats = mapTopAgent;
            agentRoleStats = agentRole;
            info = metadata;
    }

    /**
     * Returns the Metadata object containing basic player information
     * @return The Metadata object representing the player's metadata.
     */
    public PlayerData info() {
        return info;
    }


    private static <T extends PlayerBase> ArrayList<T> getAllType(Map<String, T> elements) {
        ArrayList<T> list = new ArrayList<T>();
        for(T i : elements.values()) {
            list.add(i);
        }
        return list;
    }

    public ArrayList<PlayerMode> getAllModes() {
        return getAllType(modeStats);
    }
    public ArrayList<PlayerMap> getAllMaps() {
        return getAllType(mapStats);
    }
    public ArrayList<PlayerAgent> getAllAgents() {
        return getAllType(agentStats);
    }
    public ArrayList<PlayerWeapon> getAllWeapons() {
        return getAllType(weaponStats);
    }
    public ArrayList<PlayerMapTopAgent> getAllMapTopAgents() {
        return getAllType(mapTopAgentStats);
    }
    public ArrayList<PlayerAgentRole> getAllAgentRoles() {
        return getAllType(agentRoleStats);
    }

    public ArrayList<PlayerMapTopAgent> getMapTopAgentsByMapOrAgent(String mapOrAgent) {
        ArrayList<PlayerMapTopAgent> list = new ArrayList<PlayerMapTopAgent>();
        for(String sub : mapTopAgentStats.keySet()) {
            if(sub.contains(mapOrAgent))
                list.add(mapTopAgentStats.get(sub));
        }
        return list;
    }


    public PlayerMode getMode(String mode) {
        return modeStats.get(mode);
    }
    public PlayerMap getMap(String map) {
        return mapStats.get(map);
    }
    public PlayerAgent getAgent(String agent) {
        return agentStats.get(agent);
    }
    public PlayerWeapon getWeapon(String weapon) {
        return weaponStats.get(weapon);
    }
    public PlayerMapTopAgent getMapTopAgent(String map, String agent) {
        return mapTopAgentStats.get(map + "-" + agent);
    }
    public PlayerAgentRole getAgentRole(String role) {
        return agentRoleStats.get(role);
    }

    public boolean containsMode(String mode) {
        return modeStats.containsKey(mode);
    }
    public boolean containsMap(String map) {
        return mapStats.containsKey(map);
    }
    public boolean containsAgent(String agent) {
        return agentStats.containsKey(agent);
    }
    public boolean containsWeapon(String weapon) {
        return weaponStats.containsKey(weapon);
    }
    public boolean containsMapTopAgent(String map, String agent) {
        return mapTopAgentStats.containsKey(map + "-" + agent);
    }
    public boolean containsAgentRole(String agentRole) {
        return agentRoleStats.containsKey(agentRole);
    }

    public boolean containsMapTopAgentByMapOrAgent(String mapOrAgent) {
        for(String sub : mapTopAgentStats.keySet()) {
            if(sub.contains(mapOrAgent))
                return true;
        }
        return false;
    }

    private static <T extends PlayerBase> ArrayList<T> getByHighestStat
    (Map<String, T> stats, String statMethod, boolean byLowest) {
        // add "get" to method name if not included
        if(!statMethod.substring(0, 3).equals("get")) {
            statMethod = "get" + 
                statMethod.substring(0, 1)
                .toUpperCase() + 
                statMethod.substring(1);
        }
        Method statGetter = null;
        ArrayList<T> elements = new ArrayList<T>();
        for(T i : stats.values()) {
            elements.add(i);
        } 
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
    public ArrayList<PlayerMapTopAgent> getMapTopAgentByHighest(String statMethod, boolean byLowest) {
        return getByHighestStat(mapTopAgentStats, statMethod, byLowest);
    }
    public ArrayList<PlayerAgentRole> getAgentRoleByHighest(String statMethod, boolean byLowest) {
        return getByHighestStat(agentRoleStats, statMethod, byLowest);
    }
}