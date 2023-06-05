package com.teriyake.stava.parsertest;

import java.io.File;

import com.teriyake.stava.StavaUtil;
import com.teriyake.stava.parser.MatchParser;

public class MatchTest {
    public static void main(String[] args) {
        System.out.println("Getting Match Data...");
        String file = "";
        File filePath = new File(System.getProperty("user.dir") + "/src/test/java/com/teriyake/stava/parsertest/Match.json");
        file = StavaUtil.readFile(filePath);

        if(file.equals("")) {
            System.out.println("Nothing in File");
            System.exit(0);
        }
        System.out.println("Done Reading!");
        
        MatchParser.getSegments(file);

        // for(String i : MatchParser.getPlayers(file)) {
        //     System.out.println(i);
        // }
    }
}
