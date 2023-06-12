package com.teriyake.stava;

import java.io.File;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class SegmentStats {
    public static void main(String[] args) {
        String jsonString = "";
        File filePath = new File(System.getProperty("user.dir") + "/src/test/java/com/teriyake/stava/parsertest/Profile.json");
        System.out.println(filePath);
        jsonString = StavaUtil.readFile(filePath);

        if(jsonString.equals("")) {
            System.out.println("Nothing in File");
            System.exit(0);
        }


        Gson gson = new Gson(); // error when it gets 403?
        JsonArray segments = gson.fromJson(jsonString, JsonObject.class)
            .get("data").getAsJsonObject()
            .get("segments").getAsJsonArray();
        
        JsonObject stats = segments.get(37).getAsJsonObject()
            .get("stats").getAsJsonObject();

        Set<Entry<String, JsonElement>> entrySet = stats.entrySet();
        for(Entry<String, JsonElement> entry : entrySet) {
            System.out.println("private double " + entry.getKey() + ";");
        }
    }
}
