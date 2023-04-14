package com.teriyake.stava.parsertest;

import java.io.File;
import java.util.ArrayList;

import com.teriyake.stava.StavaUtil;
import com.teriyake.stava.parser.PlayerParser;
import com.teriyake.stava.stats.Player;
import com.teriyake.stava.stats.player.PlayerAgent;
import com.teriyake.stava.stats.player.PlayerMap;
import com.teriyake.stava.stats.player.PlayerMode;
import com.teriyake.stava.stats.player.PlayerWeapon;

public class ParserTest {
    public static void main(String[] args) {
        System.out.println("Getting Player Data...");
        String file = "";
        File filePath = new File(System.getProperty("user.dir") + "/stava/src/test/java/com/teriyake/stava/parsertest/Profile.json");
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
        Player player = null;
        try {
            // mode = PlayerParser.getPlayerMode(file, "competitive");
            // map = PlayerParser.getPlayerMap(file, "lotus");
            // agent = PlayerParser.getPlayerAgent(file, "viper");
            // weapon = PlayerParser.getPlayerWeapon(file, "vandal");
            player = PlayerParser.getPlayer(file);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done Parsing!");
        mode = player.getMode("competitive");
        map = player.getMap("lotus");
        agent = player.getAgent("viper");
        weapon = player.getWeapon("vandal");
        // System.out.println(PlayerParser.getTypes(file));

        // System.out.println(PlayerParser.getPlayer(file));


        // Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
        // System.out.println(json);

        // filePath = new File("C:/Users/Ian/OneDrive/Projects/Coding Projects/Java Projects/StaVa/stava/src/test/java/com/teriyake/stava/parsertest/ParserTest.json");
        // String toFile = gson.toJson(json);
        // StavaUtil.writeFile(filePath, toFile);
        System.out.println("\nPlayer info of: " + player.info().getName());
        System.out.println("Region: " + player.info().getRegion());
        System.out.println("Episode/Act: " + player.info().getSeason());
        System.out.println("Date: " + player.info().getDate());


        System.out.println("\n" + mode.info().getType());
        System.out.println("Type: " + mode.info().getGameMode());
        System.out.println("Rank: " + mode.getRank());
        System.out.println("Winrate: " + mode.getMatchesWinPct());
        System.out.println("KD Ratio: " + mode.getKDRatio());

        System.out.println("\n" + map.info().getType());
        System.out.println("Type: " + map.info().getSubType());
        System.out.println("Damage per Round: " + map.getDamagePerRound());
        System.out.println("Winrate: " + map.getMatchesWinPct());
        System.out.println("KD Ratio: " + map.getKDRatio());

        System.out.println("\n" + agent.info().getType());
        System.out.println("Type: " + agent.info().getSubType());
        System.out.println("Damage per Round: " + agent.getDamagePerRound());
        System.out.println("Winrate: " + agent.getMatchesWinPct());
        System.out.println("KD Ratio: " + agent.getKDRatio());
        System.out.println("Ability kills: " + agent.getGrenadeKills());

        System.out.println("\n" + weapon.info().getType());
        System.out.println("Type: " + weapon.info().getSubType());
        System.out.println("Damage per Round: " + weapon.getDamagePerRound());
        System.out.println("Winrate: " + weapon.getMatchesWinPct());
        System.out.println("KD Ratio: " + weapon.getKDRatio());
        System.out.println("Longest Kill Distance: " + weapon.getLongestKillDistance());

        ArrayList<PlayerAgent> agentList = player.getAgentByHighest("getMatchesWinPct", false);
        System.out.println("\n--------Stats by highest winrate--------");
        for(int i = 0; i < agentList.size(); i++) {
            System.out.println("\nType: " + agentList.get(i).info().getSubType());
            System.out.println("Winrate: " + agentList.get(i).getMatchesWinPct());
        }

        ArrayList<PlayerMap> mapList = player.getMapByHighest("getMatchesWinPct", false);
        for(int i = 0; i < mapList.size(); i++) {
            System.out.println("\nType: " + mapList.get(i).info().getSubType());
            System.out.println("Winrate: " + mapList.get(i).getMatchesWinPct());
        }

        ArrayList<PlayerWeapon> weaponList = player.getWeaponByHighest("getHeadshotsPercentage", false);
        for(int i = 0; i < weaponList.size(); i++) {
            System.out.println("\nType: " + weaponList.get(i).info().getSubType());
            System.out.println("Winrate: " + weaponList.get(i).getMatchesWinPct());
            System.out.println("Headshot: " + weaponList.get(i).getHeadshotsPercentage());
        }


        // System.out.println();
        // Scanner input = new Scanner(System.in);
        // System.out.println("Search Results: ");
        // String jsonResults = input.nextLine();
        // input.close();
        // System.out.println(BasicParser.getSearchResults(jsonResults));
    }
}