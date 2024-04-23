package com.teriyake.stava.parsertest;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import com.teriyake.stava.StavaUtil;
import com.teriyake.stava.parser.MatchParser;

public class MatchTest {
    public static void main(String[] args) {
        System.out.println("Getting Match Data...");
        String file = "";
        // File filePath = new File(System.getProperty("user.dir") + "/src/test/java/com/teriyake/stava/parsertest/Match.json");
        file = StavaUtil.readFile(filePath);

        if(file.equals("")) {
            System.out.println("Nothing in File");
            System.exit(0);
        }
        System.out.println("Done Reading!");
        
        // MatchParser.getSegments(file);
        Map<String, ArrayList<String>> playerList = MatchParser.getTeams(file);
        String[] defender = new String[5];
        String[] attacker = new String[5];
        for(String team : playerList.keySet()) {
            for(int i = 0; i < 5; i++) {
                if(team.equals("defender"))
                    defender[i] = playerList.get(team).get(i);
                else if(team.equals("attacker"))
                    attacker[i] = playerList.get(team).get(i);
            }
        }

        System.out.println("Winning Team: " + MatchParser.getWinningTeam(file) + ", Map: " + MatchParser.getMap(file) + "\n");
        System.out.println("Defender Team: ");
        for(int i = 0; i < defender.length; i++) {
            System.out.println(defender[i] + " - " + MatchParser.getAgentOfPlayer(file, defender[i]));
        }
        System.out.println("Attacker Team: ");
        for(int i = 0; i < attacker.length; i++) {
            System.out.println(attacker[i] + " - " + MatchParser.getAgentOfPlayer(file, attacker[i]));
        }
    }
}
