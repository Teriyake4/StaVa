package com.teriyake.stava.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SearchParser {

    public static List<String> getSearchResults(String jsonString) {
        Gson gson = new Gson(); // error when it gets 403?
        JsonObject initJson = gson.fromJson(jsonString, JsonObject.class);
        JsonArray results = initJson.get("data").getAsJsonArray();
        List<String> finalData = new ArrayList<String>();
        for(int i = 0; i < results.size(); i++) {
            finalData.add(results.get(i).getAsJsonObject()
                .get("platformUserIdentifier").getAsString());
        }
        return finalData;
    }

    public static List<String> getNonPrivateSearchResults(String jsonString) {
        Gson gson = new Gson();
        JsonObject initJson = gson.fromJson(jsonString, JsonObject.class);
        JsonArray results = initJson.get("data").getAsJsonArray();
        List<String> finalData = new ArrayList<String>();
        for(int i = 0; i < results.size(); i++) {
            if(results.get(i).getAsJsonObject().get("status").isJsonNull())
                continue;
            finalData.add(results.get(i).getAsJsonObject()
                .get("platformUserIdentifier").getAsString());
        }
        return finalData;
    }

    public static String[] getOrderedSearchResults(String jsonString, String query) {
        /*
        * x minutes ago
        * an hour ago
        * x hours ago
        * yesterday
        * x days ago
        * one month ago
        * x months ago
        */
        // String[] times = {"minutes ago", "an hour ago", "hours ago", "yesterday", 
        //     "days ago", "one month ago", "months ago"};
        Map<String, Integer> times = new HashMap<String, Integer>(); // values are converting to minutes
        times.put(" minutes ago", 1);
        times.put("an hour ago", 60);
        times.put(" hours ago", 60);
        times.put("yesterday", 1440);
        times.put(" days ago", 1440);
        times.put("one month ago", 43800);
        times.put(" months ago", 43800);
        times.put("one year ago", 525600); // unsure exists
        times.put(" years ago", 525600); // unsure exists
        Gson gson = new Gson(); // error when it gets 403?
        JsonObject initJson = gson.fromJson(jsonString, JsonObject.class);
        JsonArray results = initJson.get("data").getAsJsonArray();
        Map<String, Integer> init = new HashMap<String, Integer>();
        for(int i = 0; i < results.size(); i++) {
            String name = results.get(i).getAsJsonObject()
                .get("platformUserIdentifier").getAsString();
            String time = results.get(i).getAsJsonObject()
                .get("status").getAsString();
            if(time == null) {
                init.put(name, -1);
                continue;
            }
            for(String unit : times.keySet()) {
                int index = time.indexOf(unit);
                int numTime = -1;
                if(index == -1) {
                    init.put(name, null);
                }
                switch(unit) {
                    case "an hour ago":
                        time = "1";
                        break;
                    case "yesterday":
                        time = "1";
                        break;
                    case "one month ago":
                        time = "1";
                        break;
                    case "one year ago":
                        time = "1";
                        break;
                    default:
                        time = time.substring(0, index);
                }
                try {
                    numTime = Integer.parseInt(time);
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid String: " + time);
                    e.printStackTrace();
                }
                numTime *= times.get(unit);
                init.put(name, numTime);
            }
        }
        // for(Map.Entry<String, Integer> result : init.entrySet()) {

        // }
        String[] sorted = new String[init.size()];
        return sorted;
    }
    
}
