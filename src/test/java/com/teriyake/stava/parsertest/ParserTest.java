package com.teriyake.stava.parsertest;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import com.teriyake.stava.StavaUtil;
import com.teriyake.stava.parser.PlayerParser;
import com.teriyake.stava.stats.Player;
import com.teriyake.stava.stats.player.PlayerAgent;
import com.teriyake.stava.stats.player.PlayerAgentRole;
import com.teriyake.stava.stats.player.PlayerMap;
import com.teriyake.stava.stats.player.PlayerMapTopAgent;
import com.teriyake.stava.stats.player.PlayerMode;
import com.teriyake.stava.stats.player.PlayerWeapon;

public class ParserTest {
    public static void main(String[] args) {
        System.out.println("Getting Player Data...");
        String file = "";
        // File filePath = new File(System.getProperty("user.dir") + "/src/test/java/com/teriyake/stava/parsertest/Profile.json");
        File filePath = new File(System.getProperty("user.home") + "\\OneDrive\\Documents\\StaVa\\data\\player\\jollibee#1614.json");
        System.out.println(filePath);
        file = StavaUtil.readFile(filePath);

        if(file.equals("")) {
            System.out.println("Nothing in File");
            System.exit(0);
        }
        System.out.println("Done Reading!");
        // System.out.println(file);
        // String newfile = "C:/ProgramData/StaVa";
        // File newfilePath = new File(newfile);
        System.out.println("Parsing Player Data...");
        PlayerMode mode = null;
        PlayerMap map = null;
        PlayerAgent agent = null;
        PlayerWeapon weapon = null;
        PlayerMapTopAgent mapTopAgent = null;
        PlayerAgentRole agentRole = null;
        Player player = null;
            // PlayerParser parser = new PlayerParser(file);
            // mode = PlayerParser.getPlayerMode(file, "competitive");
            // map = PlayerParser.getPlayerMap(file, "lotus");
            // agent = parser.getPlayerAgent("omen");
            // weapon = PlayerParser.getPlayerWeapon(file, "vandal");
        long[] timeList = new long[1];
        for(int i = 0; i < timeList.length; i++) {
            PlayerParser parser = new PlayerParser(file);
            long startTime = System.nanoTime();
            player = parser.getPlayer();
            // System.out.println("Parse Time: " + (System.nanoTime() - startTime) / 1000000 + "ms");
            long endTime = System.nanoTime();
            timeList[i] = endTime - startTime;
        }
        long time = 0;
        for(int i = 0; i < timeList.length; i++)
            time += timeList[i];
        time /= timeList.length;
        System.out.println("Total Average Parsing Time: " + (time) / 1000000 + "ms");
        
        System.out.println("Done Parsing!");

        // mode = player.getMode("competitive");
        // map = player.getMap("lotus");
        // agent = player.getAgent("omen");
        // weapon = player.getWeapon("vandal");
        // mapTopAgent = player.getMapTopAgent("fracture", "sage");
        // agentRole = player.getAgentRole("controller");


        // System.out.println(PlayerParser.getTypes(file));

        // System.out.println(PlayerParser.getPlayer(file));


        // Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        // System.out.println(gson.toJson(player));

        // filePath = new File("C:/Users/Ian/OneDrive/Projects/Coding Projects/Java Projects/StaVa/stava/src/test/java/com/teriyake/stava/parsertest/ParserTest.json");
        // String toFile = gson.toJson(json);
        // StavaUtil.writeFile(filePath, toFile);



        
        // System.out.println("\nPlayer info of: " + player.info().getName());
        // System.out.println("Region: " + player.info().getRegion());
        // System.out.println("Episode, Act: " + player.info().getSeason());
        // System.out.println("Date: " + player.info().getDate());

        // System.out.println("\n" + mode.info().getType());
        // System.out.println("Type: " + mode.info().getGameMode());
        // System.out.println("Rank: " + mode.getRank());
        // System.out.println("Winrate: " + mode.getMatchesWinPct());
        // System.out.println("KD Ratio: " + mode.getKDRatio());

        // System.out.println("\n" + map.info().getType());
        // System.out.println("Type: " + map.info().getSubType());
        // System.out.println("Damage per Round: " + map.getDamagePerRound());
        // System.out.println("Winrate: " + map.getMatchesWinPct());
        // System.out.println("KD Ratio: " + map.getKDRatio());

        // System.out.println("\n" + agent.info().getType());
        // System.out.println("Type: " + agent.info().getSubType());
        // System.out.println("Damage per Round: " + agent.getDamagePerRound());
        // System.out.println("Winrate: " + agent.getMatchesWinPct());
        // System.out.println("KD Ratio: " + agent.getKDRatio());
        // System.out.println("Ability kills: " + agent.getGrenadeKills());

        // System.out.println("\n" + weapon.info().getType());
        // System.out.println("Type: " + weapon.info().getSubType());
        // System.out.println("Damage per Round: " + weapon.getDamagePerRound());
        // System.out.println("Winrate: " + weapon.getMatchesWinPct());
        // System.out.println("KD Ratio: " + weapon.getKDRatio());
        // System.out.println("Longest Kill Distance: " + weapon.getLongestKillDistance());

        // System.out.println("\n" + mapTopAgent.info().getType());
        // System.out.println("Type: " + mapTopAgent.info().getSubType());
        // System.out.println("Damage per Round: " + mapTopAgent.getDamagePerRound());
        // System.out.println("Winrate: " + mapTopAgent.getMatchesWinPct());
        // System.out.println("KD Ratio: " + mapTopAgent.getKDRatio());

        // System.out.println("\n" + agentRole.info().getType());
        // System.out.println("Type: " + agentRole.info().getSubType());
        // System.out.println("Damage per Round: " + agentRole.getDamagePerRound());
        // System.out.println("Winrate: " + agentRole.getMatchesWinPct());
        // System.out.println("KD Ratio: " + agentRole.getKDRatio());

        // ArrayList<PlayerAgent> agentList = player.getAgentByHighest("getMatchesWinPct", false);
        // System.out.println("\n--------Stats by highest winrate--------");
        // for(int i = 0; i < agentList.size(); i++) {
        //     System.out.println("\nType: " + agentList.get(i).info().getSubType());
        //     System.out.println("Winrate: " + agentList.get(i).getMatchesWinPct());
        // }

        // ArrayList<PlayerMapTopAgent> mapTopAgentList = player.getAllMapTopAgents();
        // System.out.println(mapTopAgentList.size());
        // for(int i = 0; i < mapTopAgentList.size(); i++) {
        //     System.out.println("\nType: " + mapTopAgentList.get(i).info().getSubType());
        //     System.out.println("Winrate: " + mapTopAgentList.get(i).getMatchesWinPct());
        // }




        // System.out.println();
        // Scanner input = new Scanner(System.in);
        // System.out.println("Search Results: ");
        // String jsonResults = input.nextLine();
        // input.close();
        // System.out.println(BasicParser.getSearchResults(jsonResults));
    }
}