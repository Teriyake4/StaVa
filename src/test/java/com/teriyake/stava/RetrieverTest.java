package com.teriyake.stava;

import java.util.ArrayList;

import com.teriyake.stava.stats.Player;
import com.teriyake.stava.stats.player.PlayerAgent;
import com.teriyake.stava.stats.player.PlayerMode;

public class RetrieverTest {
    public static void main(String[] args) {
        String name = "Onmakawa#KING";

        // String filePath = "C:/ProgramData";
        // File file = new File(filePath);
        Store store = new Store(System.getProperty("user.home") + "/OneDrive/Documents");
        store.setStorePatternByAct();
        store.setFileNameAsName();
        Retriever ret = null;
        Player p = null;
        // String[] results = null;
        try {
            System.out.println("Creating connection");
            ret = new Retriever(store);
            System.out.println("Connected");
            // results = ret.getSearch("el trucko");
            p = ret.getPlayer(name);
            ret.closeConnection();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        // System.out.println("Results: ");
        // for(String i : results){   
        //     System.out.println(i);
        // }
        System.out.println();

        PlayerMode pm = p.getMode("competitive");

        System.out.println("Name: " + p.info().getName());
        System.out.println("Rank: " + pm.getRank());
        System.out.println("Winrate: " + pm.getMatchesWinPct());
        System.out.println("KD Ratio: " + pm.getKDRatio());
        System.out.println("Image: " + pm.info().getItemImageURL());


        ArrayList<PlayerAgent> plist = p.getAgentByHighest("getMatchesWinPct", false);
        for(int i = 0; i < plist.size(); i++) {
            System.out.println("\nType: " + plist.get(i).info().getSubType());
            System.out.println("Winrate: " + plist.get(i).getMatchesWinPct());
        }
    }
    
}
